package Client.Model;

import Client.dto.init.ClientBaseKing;
import Client.dto.init.ClientInitMessage;
import Client.dto.init.InitMessage;
import Client.dto.turn.ClientTurnMessage;

import java.util.*;
import java.util.function.Consumer;

import Client.dto.turn.TurnMessage;
import com.google.gson.Gson;
import common.network.Json;
import common.network.data.Message;

public class Game implements World {
    private ClientInitMessage clientInitMessage;
    private ClientTurnMessage clientTurnMessage;
    private InitMessage initMessage;
    private TurnMessage turnMessage;
    private Consumer sender;

    public Game(Consumer sender){
        this.sender = sender;
    }

    public Game(Game game){
        this.clientInitMessage = game.getClientInitMessage();
        this.initMessage = game.getInitMessage();
        this.sender = game.getSender();
    }


    public Consumer getSender() {
        return sender;
    }

    public void setSender(Consumer sender) {
        this.sender = sender;
    }

    public InitMessage getInitMessage() {
        return initMessage;
    }

    public ClientTurnMessage getClientTurnMessage() {
        return clientTurnMessage;
    }

    @Override
    public void chooseDeck(List<Integer> typeIds) {
        //tOdO
    }

    @Override
    public int getMyId() {
        return clientInitMessage.getMap().getKings().get(0).getPlayerId();
    }

    @Override
    public int getFriendId() {
        return clientInitMessage.getMap().getKings().get(1).getPlayerId();
    }

    public int getFirstEnemyId() {
        return clientInitMessage.getMap().getKings().get(2).getPlayerId();
    }

    public int getSecondEnemyId() {
        return clientInitMessage.getMap().getKings().get(3).getPlayerId();
    }

    private List<ClientBaseKing> getEnemyKings() {
        List<ClientBaseKing> enemyKings = new ArrayList<>();
        for (ClientBaseKing clientBaseKing : clientInitMessage.getMap().getKings()) {
            if (clientBaseKing.isYourFriend() || clientBaseKing.isYou())
                continue;
            enemyKings.add(clientBaseKing);
        }
        return enemyKings;
    }

    @Override
    public Cell getPlayerPosition(int playerId) {
        return getPlayerKing(playerId).getCenter();
    }

    private King getPlayerKing(int playerId) {
        for (King king : initMessage.getMapp().getKings())
            if (king.getPlayerId() == playerId) {
                return king;
            }
        return null;
    }

    private int getFriendIdOfPlayer(int playerId) {
        if (playerId == getFriendId()) return getFriendId();
        if (playerId == getMyId()) return getMyId();
        if (playerId == getFirstEnemyId()) return getSecondEnemyId();
        if (playerId == getSecondEnemyId()) return getFirstEnemyId();
        return -1;
    }


    @Override
    public List<Path> getPathsFromPlayer(int playerId) {
        //todo
        King playerKing = getPlayerKing(playerId);
        King friendKing = getPlayerKing(getFriendIdOfPlayer(playerId));

        List<Path> paths = new ArrayList<>();
        for (Path path : initMessage.getMapp().getPaths())
            if (path.getCells().indexOf(playerKing) == 0 || path.getCells().lastIndexOf(playerKing) == path.getCells().size() - 1)
                if (!path.getCells().contains(friendKing))
                    paths.add(path);
        return paths;
    }

    @Override
    public Path getPathToFriend(int playerId) {
        King playerKing = getPlayerKing(playerId);
        King friendKing = getPlayerKing(getFriendIdOfPlayer(playerId));

        for (Path path : initMessage.getMapp().getPaths()) {
            List<Cell> pathCells = path.getCells();
            if (pathCells.indexOf(playerKing) == 0 || pathCells.lastIndexOf(playerKing) == pathCells.size() - 1)
                if (pathCells.indexOf(friendKing) == 0 || pathCells.lastIndexOf(friendKing) == pathCells.size() - 1)
                    return path;
        }
        return null;
    }


    @Override
    public int getMapRowNum() {
        return clientInitMessage.getMap().getRows();
    }

    @Override
    public int getMapColNum() {
        return clientInitMessage.getMap().getCols();
    }

    @Override
    public List<Path> getPathsCrossingCell(Cell cell) {
        List<Path> paths = new ArrayList<>();
        for (Path path : initMessage.getMapp().getPaths())
            if (path.getCells().contains(cell))
                paths.add(path);
        return paths;
    }

    @Override
    public List<Unit> getPlayerUnits(int playerId) {
        List<Unit> units = new ArrayList<>();
        for (Unit unit : turnMessage.getUnits())
            if (unit.getPlayerId() == playerId)
                units.add(unit);
        return units;
    }

    //path ha ro tavajoh nakardim hatman vase khode player bashe
    @Override
    public List<Unit> getCellUnits(Cell cell) {
        List<Unit> units = new ArrayList<>(cell.getUnitList());
        return units;
    }

    @Override
    public Path getShortestPathToCell(int fromPlayerId, Cell cell) {
        //todo tartibe khune haye masira che sheklie
        int minDis = 100 * 1000;
        Cell playerCell = getPlayerPosition(fromPlayerId);
        Path bestPath = null;
        for (Path path : initMessage.getMapp().getPaths()) {
            if (path.getCells().indexOf(playerCell) != 0)
                Collections.reverse(path.getCells());
            if (path.getCells().indexOf(playerCell) == 0) {
                int index = path.getCells().indexOf(cell);
                if (index < minDis) {
                    minDis = index;
                    bestPath = path;
                }
            }
        }
        return bestPath;
    }

    @Override
    public int getMaxAP() {
        return clientInitMessage.getGameConstants().getMaxAP();
    }

    @Override
    public int getRemainingAP() {
        return clientTurnMessage.getRemainingAP();
    }

    @Override
    public List<Unit> getHand() {
        List<Unit> hand = new ArrayList<>();
        for (int unitId : clientTurnMessage.getHand())
            for (Unit unit : turnMessage.getUnits())
                if (unit.getUnitID() == unitId) {
                    hand.add(unit);
                    break;
                }
        return hand;
    }

    @Override
    public List<Unit> getDeck() {
        List<Unit> deck = new ArrayList<>();
        for (int unitId : clientTurnMessage.getDeck())
            for (Unit unit : turnMessage.getUnits())
                if (unit.getUnitID() == unitId) {
                    deck.add(unit);
                    break;
                }
        return deck;
    }

    @Override
    public void putUnit(int typeId, int pathId) {
        // todo
        return;
    }

    @Override
    public int getCurrentTurn() {
        return clientTurnMessage.getCurrTurn();
    }

    @Override
    public int getMaxTurns() {
        return clientInitMessage.getGameConstants().getMaxTurns();
    }

    @Override
    public int getPickTimeout() {
        return clientInitMessage.getGameConstants().getPickTimeout();
    }

    @Override
    public int getTurnTimeout() {
        return clientInitMessage.getGameConstants().getTurnTimeout();
    }

    @Override
    public int getRemainingTime() {

        //TODO mamadoo mizane
        return 0;
    }

    @Override
    public int getPlayerHP(int playerId) {
        for (King king : turnMessage.getKings())
            if (king.getPlayerId() == playerId)
                return king.getHp();//getHp chera p sh kuchike ??
        return -1; // impossible
    }

    @Override
    public void castUnitSpell(int unitId, int pathId, int index, int spellId) {
        //todo
    }

    @Override
    public void castUnitSpell(int unitId, int pathId, int index, Spell spell) {
        //todo
    }

    @Override
    public void castAreaSpell(int row, int col, int spellId) {
        //todo
    }

    @Override
    public void castAreaSpell(int row, int col, Spell spell) {
        //todo
    }

    private boolean inRange(Cell cell, Cell center, int range) {
        if (Math.abs(cell.getCol() - center.getCol()) > range)
            return false;
        if (Math.abs(cell.getRow() - center.getRow()) > range)
            return false;
        return true;
    }

    private ArrayList<Unit> unique(List<Unit> listOfUnits) {
        ArrayList<Unit> uniqueList = new ArrayList<>();
        for (Unit unit : listOfUnits) {
            if (uniqueList.contains(unit))
                continue;
            uniqueList.add(unit);
        }
        return uniqueList;
    }

    @Override
    public List<Unit> getAreaSpellTargets(Cell center, Spell spell) {
        //AreaSpell dade she hatman
        List<Unit> units = new ArrayList<>();
        if (!(spell instanceof AreaSpell))
            return null;
        AreaSpell areaSpell = (AreaSpell) spell;
        for (Path path : initMessage.getMapp().getPaths()) {
            for (Cell cell : path.getCells())
                if (inRange(cell, center, areaSpell.getRange()))
                    units.addAll(cell.getUnitList());
        }
        return unique(units);
    }

    @Override
    public List<Unit> getAreaSpellTargets(Cell center, int spellId) {
        //spellId hamun type ?
        Spell spell = getSpellById(spellId);
        return getAreaSpellTargets(center, spell);
    }

    @Override
    public List<Unit> getAreaSpellTargets(int row, int col, Spell spell) {
        Cell cell = getCellByCoordination(row, col);
        return getAreaSpellTargets(cell, spell);
    }

    @Override
    public List<Unit> getAreaSpellTargets(int row, int col, int spellId) {
        Cell cell = getCellByCoordination(row, col);
        return getAreaSpellTargets(cell, spellId);
    }

    private Cell getCellByCoordination(int row, int col) {
        for (Path path : initMessage.getMapp().getPaths())
            for (Cell cell : path.getCells())
                if (cell.getRow() == row && cell.getCol() == col)
                    return cell;
        return null;
    }

    @Override
    public int getRemainingTurnsToUpgrade() {
        //+=1 esho havasemun bashe
        return clientTurnMessage.getCurrTurn() % clientInitMessage.getGameConstants().getTurnsToUpgrade();
    }

    @Override
    public int getRemainingTurnsToGetSpell() {
        return clientTurnMessage.getCurrTurn() % clientInitMessage.getGameConstants().getTurnsToSpell();
    }

    @Override
    public CastAreaSpell getCastAreaSpell(int playerId) {
        for (CastSpell castSpell : turnMessage.getCastSpells()) {
            if (castSpell instanceof CastAreaSpell) {
                if (castSpell.getCasterId() == playerId)
                    return (CastAreaSpell) castSpell;
            }
        }
        return null;
    }

    @Override
    public CastUnitSpell getCastUnitSpell(int playerId) {
        for (CastSpell castSpell : turnMessage.getCastSpells()) {
            if (castSpell instanceof CastUnitSpell) {
                if (castSpell.getCasterId() == playerId)
                    return (CastUnitSpell) castSpell;
            }
        }
        return null;
    }

    @Override
    public List<CastSpell> getCastSpellOnUnit(Unit unit) {
        return null;
    }

    @Override
    public List<CastSpell> getCastSpellOnUnit(int unitId) {
        return null;
    }

    private Unit getUnitById(int unitId){
        for(Unit unit : turnMessage.getUnits())
            if(unit.getUnitID() == unitId)
                return unit;
        return null;
    }

    @Override
    public int getDamageUpgradeNumber() {
        //avaz kardimesh
        return clientTurnMessage.getAvailableDamageUpgrades();
    }

    @Override
    public int getRangeUpgradeNumber() {
        //inam khodemun ezade kardim
        return clientTurnMessage.getAvailableRangeUpgrades();
    }

    private List<Spell> getSpellsByIds(List<Integer> list) {
        List<Spell> spells = new ArrayList<>();
        for (int spellId : list) {
            for (Spell spell : initMessage.getSpells()) {
                if (spell.getTypeId() == spellId)
                    spells.add(spell);
            }
        }
        return spells;

    }

    @Override
    public List<Spell> getSpellsList() {
        //spellId hamun type e ?
        return getSpellsByIds(clientTurnMessage.getMySpells());
    }

    @Override
    public HashMap<Spell, Integer> getSpells() {
        List<Spell> spells = getSpellsByIds(clientTurnMessage.getMySpells());
        HashMap<Spell, Integer> hashMap = new HashMap<Spell, Integer>();
        for (Spell spell : spells) {
            int currentCounter = 0;
            if (hashMap.containsKey(spell)) {
                currentCounter = hashMap.get(spell);
                hashMap.remove(spell);
            }
            currentCounter++;
            hashMap.put(spell, currentCounter + 1);
        }
        return hashMap;
    }

    @Override
    public Spell getReceivedSpell() {
        return getSpellById(clientTurnMessage.getReceivedSpell());
    }

    @Override
    public Spell getFriendReceivedSpell() {
        return getSpellById(clientTurnMessage.getFriendReceivedSpell());
    }

    @Override
    public void upgradeUnitRange(Unit unit) {
        //todo
    }

    @Override
    public void upgradeUnitRange(int unitId) {
        Unit unit = getUnitById(unitId);
        upgradeUnitRange(unit);
        return;
    }

    @Override
    public void upgradeUnitDamage(Unit unit) {
        //todo
    }

    @Override
    public void upgradeUnitDamage(int unitId) {
        Unit unit = getUnitById(unitId);
        upgradeUnitDamage(unit);
        return;
    }

    @Override
    public List<Unit> getPlayerDuplicateUnits(int playerId) {
        return getUnitsWithSpellOfPlayer("Duplicate", playerId);
    }

    private String getSpellType(Spell spell){
        for(Spell spell1 : initMessage.getSpells())
            if(spell1.getTypeId() == spell.getTypeId())
                return spell1.getType();
        return null;
    }

    private List<Unit> getUnitsWithSpellOfPlayer(String spellType, int playerId){
        List<Unit> units = new ArrayList<>();
        for (Unit unit : turnMessage.getUnits()) {
            if (unit.getPlayerId() != playerId) continue;
            for (int spellId : unit.getAffectedSpells()) {
                Spell spell = getSpellById(spellId);
                if(getSpellType(spell).equals(spellType)){
                    units.add(unit);
                    break;
                }

            }
        }
        return units;
    }

    @Override
    public List<Unit> getPlayerHastedUnits(int playerId) {
        return getUnitsWithSpellOfPlayer("Hasted", playerId);
    }

    @Override
    public List<Unit> getPlayerPlayedUnits(int playerId) {
        List<Unit> playedUnits = new ArrayList<>();
        for (Unit unit : turnMessage.getUnits())
            if (unit.getPlayerId() == playerId && unit.isWasPlayedThisTurn())
                playedUnits.add(unit);
        return playedUnits;
    }

    @Override
    public Unit getUnitTarget(Unit unit) {
        return getUnitById(unit.getTarget());
    }

    @Override
    public Unit getUnitTarget(int unitId) {
        return getUnitTarget(getUnitById(unitId));
    }

    @Override
    public Cell getUnitTargetCell(Unit unit) {
        Unit target = getUnitTarget(unit);
        if (target == null) return null;
        return target.getCell();
    }

    @Override
    public Cell getUnitTargetCell(int unitId) {
        Unit unit = getUnitById(unitId);
        return getUnitTargetCell(unit);
    }

    @Override
    public Unit getKingTarget(int playerId) {
        King king = getPlayerKing(playerId);
        int unitId = king.getTarget();
        if (unitId == -1) return null;
        Unit unit = getUnitById(unitId);
        return unit;
    }

    @Override
    public Cell getKingTargetCell(int playerId) {
        Unit unit = getKingTarget(playerId);
        if (unit == null) return null;
        return unit.getCell();
    }

    @Override
    public int getKingUnitIsAttackingTo(Unit unit) {
        if (unit.getTarget() == -1) return -1;
        if (unit.getTarget() >= 4) return -1;
        return unit.getTarget();
    }

    @Override
    public int getKingUnitIsAttackingTo(int unitId) {
        Unit unit = getUnitById(unitId);
        if (unit == null) return -1;
        return getKingUnitIsAttackingTo(unit);
    }

    public ClientInitMessage getClientInitMessage() {
        return clientInitMessage;
    }

    public void setClientInitMessage(ClientInitMessage clientInitMessage) {
        this.clientInitMessage = clientInitMessage;
    }

    private Spell getSpellById(int spellId) {
        for (Spell spell : initMessage.getSpells())
            if (spell.getTypeId() == spellId)
                return spell;

        return null;
    }

    public void handleTurnMessage(Message msg) {
        this.clientTurnMessage = Json.GSON.fromJson(msg.getInfo(), ClientTurnMessage.class);
        castToTurnMessage(this.getClientTurnMessage());
    }

    public void handleInitMessage(Message msg) {
        this.clientInitMessage = Json.GSON.fromJson(msg.getInfo(), ClientInitMessage.class);
        castToInitMessage(this.getClientInitMessage());
    }


    public void castToTurnMessage(ClientTurnMessage clientTurnMessage){

    }

    public void castToInitMessage(ClientInitMessage clientInitMessage){

    }
}
