package Client.Model;

import Client.dto.ClientCell;
import Client.dto.init.*;
import Client.dto.turn.*;

import java.util.*;
import java.util.function.Consumer;

import com.google.gson.JsonObject;
import common.network.Json;
import common.network.data.Message;

public class Game implements World {
    private ClientInitMessage clientInitMessage;
    private ClientTurnMessage clientTurnMessage;
    private InitMessage initMessage;
    private TurnMessage turnMessage = new TurnMessage();
    private Consumer<Message> sender;

    public Game(Consumer<Message> sender){
        this.sender = sender;
    }

    public Game(Game game){
        this.clientInitMessage = game.getClientInitMessage();
        this.initMessage = game.getInitMessage();
        this.sender = game.getSender();
    }

    private Consumer<Message> getSender() {
        return sender;
    }

    public InitMessage getInitMessage() {
        return initMessage;
    }

    public ClientTurnMessage getClientTurnMessage() {
        return clientTurnMessage;
    }

    @Override
    public GameConstants getGameConstants(){
        if(clientInitMessage == null)return null;
        return clientInitMessage.getGameConstants();
    }

    @Override
    public void chooseDeck(List<Integer> typeIds) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("units", Json.GSON.toJsonTree(typeIds));
        Message message = new Message("pick", jsonObject, this.getCurrentTurn());
        sender.accept(message);
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
        if (playerId == getFriendId()) return getMyId();
        if (playerId == getMyId()) return getFriendId();
        if (playerId == getFirstEnemyId()) return getSecondEnemyId();
        if (playerId == getSecondEnemyId()) return getFirstEnemyId();
        return -1;
    }

    @Override
    public List<Path> getPathsFromPlayer(int playerId) {
        Cell playerKingCell = getPlayerKing(playerId).getCenter();
        Cell friendKingCell = getPlayerKing(getFriendIdOfPlayer(playerId)).getCenter();

        List<Path> paths = new ArrayList<>();
        for (Path path : initMessage.getMapp().getPaths())
            if (path.getCells().indexOf(playerKingCell) == 0 || path.getCells().lastIndexOf(playerKingCell) == path.getCells().size() - 1)
                if (!path.getCells().contains(friendKingCell))
                    paths.add(path);
        return paths;
    }

    @Override
    public Path getPathToFriend(int playerId) {
        if(getPlayerKing(playerId) == null) return null;
        Cell playerKingCell = getPlayerKing(playerId).getCenter();
        Cell friendKingCell = getPlayerKing(getFriendIdOfPlayer(playerId)).getCenter();

        for (Path path : initMessage.getMapp().getPaths()) {
            List<Cell> pathCells = path.getCells();
            if (pathCells.indexOf(playerKingCell) == 0 || pathCells.lastIndexOf(playerKingCell) == pathCells.size() - 1)
                if (pathCells.indexOf(friendKingCell) == 0 || pathCells.lastIndexOf(friendKingCell) == pathCells.size() - 1)
                    return path;
        }
        return null; // impossible
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
        if(turnMessage.getUnits() == null) return units;
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
    public List<Unit> getCellUnits(int row, int col) {
        Cell cell = getCellByCoordination(row, col);
        return getCellUnits(cell);
    }

    public Path getShortestPathToCellFromPlayer(int fromPlayerId, Cell cell){
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
    public Path getShortestPathToCell(int fromPlayerId, Cell cell) {
        Path path1 = getShortestPathToCellFromPlayer(fromPlayerId, cell);
        Path path2 = getShortestPathToCellFromPlayer(getFriendIdOfPlayer(fromPlayerId), cell);
        Path path3 = getPathToFriend(fromPlayerId);
        int length1 = 100 * 1000, length2 = 100 * 1000;
        if(path1 != null) length1 = path1.getCells().indexOf(cell);
        if(path2 != null) length2 = path3.getCells().size() + path2.getCells().indexOf(cell);
        if(length1 <= length2 && length1 < 100 * 1000) return path1;
        if(length1 > length2) return path2;
        return null;
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
    public List<BaseUnit> getHand() {
        return turnMessage.getHand();
    }

    @Override
    public List<BaseUnit> getDeck() {
        return turnMessage.getDeck();
    }

    @Override
    public void putUnit(int typeId, int pathId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("typeId", Json.GSON.toJsonTree(typeId));
        jsonObject.add("pathId", Json.GSON.toJsonTree(pathId));
        Message message = new Message("putUnit", this.getCurrentTurn(), jsonObject);
        sender.accept(message);
    }

    public void putUnit(BaseUnit baseUnit, int pathId){
        putUnit(baseUnit.getTypeId(), pathId);
    }

    public void putUnit(int typeId, Path path){
        putUnit(typeId, path.getId());
    }

    public void putUnit(BaseUnit baseUnit, Path path){
        putUnit(baseUnit.getTypeId(), path.getId());
    }

    @Override
    public int getCurrentTurn() {
        if (clientTurnMessage == null)
            return 0;
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
        long result = (clientTurnMessage.getTurnTime() + clientInitMessage.getGameConstants().getTurnTimeout() -
                System.currentTimeMillis());
        return (int)result;
    }

    @Override
    public int getPlayerHP(int playerId) {
        for (King king : turnMessage.getKings())
            if (king.getPlayerId() == playerId)
                return king.getHp();//getHp chera p sh kuchike ??
        return -1; // impossible
    }
//
//    private Path getPathById(int pathId){
//        for(Path path : this.initMessage.getMapp().getPaths())
//            if(path.getId() == pathId)
//                return path;
//        return null;
//    }

    @Override
    public void castUnitSpell(int unitId, int pathId, int index, int spellId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("typeId", Json.GSON.toJsonTree(spellId));
        jsonObject.add("unitId", Json.GSON.toJsonTree(unitId));
        jsonObject.add("pathId", Json.GSON.toJsonTree(pathId));
        Path path = InitMessage.getInitMessage().getPathById(pathId);
        Cell cell = null;
        if(path != null && index < path.getCells().size())
            cell = path.getCells().get(index);
        jsonObject.add("cell", Json.GSON.toJsonTree(cell.castToClientCell()));
        Message message = new Message("castSpell", this.getCurrentTurn(), jsonObject);
        sender.accept(message);
    }

    @Override
    public void castUnitSpell(int unitId, int pathId, int index, Spell spell) {
        int spellId = spell.getTypeId();
        castUnitSpell(unitId, pathId, index, spellId);
    }

    @Override
    public void castAreaSpell(int row, int col, int spellId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("typeId", Json.GSON.toJsonTree(spellId));
        Cell cell = getCellByCoordination(row, col);
        jsonObject.add("cell", Json.GSON.toJsonTree(cell.castToClientCell()));
        Message message = new Message("castSpell", this.getCurrentTurn(), jsonObject);
        sender.accept(message);
    }

    @Override
    public void castAreaSpell(int row, int col, Spell spell) {
        int spellId = spell.getTypeId();
        castAreaSpell(row, col, spellId);
    }

    @Override
    public void castAreaSpell(Cell center, Spell spell) {
        int spellId = spell.getTypeId();
        int row = center.getRow();
        int col = center.getCol();
        castAreaSpell(row, col, spellId);
    }

    @Override
    public void castAreaSpell(Cell center, int spellId) {
        int row = center.getRow();
        int col = center.getCol();
        castAreaSpell(row, col, spellId);
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
        Spell spell = initMessage.getSpellById(spellId);
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
        // todo what does this do if row and col be invalid
        return initMessage.getMapp().getCells()[row][col];
    }

    @Override
    public int getRemainingTurnsToUpgrade() {
        //+=1 esho havasemun bashe
        return clientInitMessage.getGameConstants().getTurnsToUpgrade() -
                clientTurnMessage.getCurrTurn() % clientInitMessage.getGameConstants().getTurnsToUpgrade();
    }

    @Override
    public int getRemainingTurnsToGetSpell() {
        return clientInitMessage.getGameConstants().getTurnsToSpell() -
                clientTurnMessage.getCurrTurn() % clientInitMessage.getGameConstants().getTurnsToSpell();
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
            if(unit.getUnitId() == unitId)
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
        return initMessage.getSpellById(clientTurnMessage.getReceivedSpell());
    }

    @Override
    public Spell getFriendReceivedSpell() {
        return initMessage.getSpellById(clientTurnMessage.getFriendReceivedSpell());
    }

    @Override
    public void upgradeUnitRange(Unit unit) {
        upgradeUnitRange(unit.getUnitId());
    }

    @Override
    public void upgradeUnitRange(int unitId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("unitId", Json.GSON.toJsonTree(unitId));
        Message message = new Message("rangeUpgrade", this.getCurrentTurn(), jsonObject);
        sender.accept(message);
    }

    @Override
    public void upgradeUnitDamage(Unit unit) {
        upgradeUnitDamage(unit.getUnitId());
    }

    @Override
    public void upgradeUnitDamage(int unitId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("unitId", Json.GSON.toJsonTree(unitId));
        Message message = new Message("damageUpgrade", this.getCurrentTurn(), jsonObject);
        sender.accept(message);
    }

    @Override
    public List<Unit> getPlayerDuplicateUnits(int playerId) {
        return getUnitsWithSpellOfPlayer(SpellType.DUPLICATE, playerId);
    }

/*    private String getSpellType(Spell spell){   //todo why this method exists? -> spell.getType()
        for(Spell spell1 : initMessage.getSpells())
            if(spell1.getTypeId() == spell.getTypeId())
                return spell1.getType();
        return null;
    }*/

    private List<Unit> getUnitsWithSpellOfPlayer(SpellType spellType, int playerId){
        List<Unit> units = new ArrayList<>();
        for (Unit unit : turnMessage.getUnits()) {
            if (unit.getPlayerId() != playerId)
                continue;
            for (int spellId : unit.getAffectedSpells()) {
                Spell spell = initMessage.getSpellById(spellId);    //todo use a map for getting spell from spellId
                if (spell == null)
                    continue;
                if(spell.getType() == spellType){
                    units.add(unit);
                    break;
                }
            }
        }
        return units;
    }

    @Override
    public List<Unit> getPlayerHastedUnits(int playerId) {
        return getUnitsWithSpellOfPlayer(SpellType.HASTE, playerId);
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
        return getUnitById(unit.getTargetId());
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
        if (unit.getTargetId() == -1) return -1;
        if (unit.getTargetId() >= 4) return -1;
        return unit.getTargetId();
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


    public void handleTurnMessage(Message msg) {
        System.out.println(Json.GSON.toJson(msg));
        this.clientTurnMessage = Json.GSON.fromJson(msg.getInfo(), ClientTurnMessage.class);
        this.clientTurnMessage.setTurnTime(System.currentTimeMillis());
        turnMessage = clientTurnMessage.castToTurnMessage(initMessage);

//        castToTurnMessage(this.getClientTurnMessage());
    }

    public void handleInitMessage(Message msg) {
        this.clientInitMessage = Json.GSON.fromJson(msg.getInfo(), ClientInitMessage.class);
        this.initMessage = clientInitMessage.castToInitMessage();
    }


    public void castToTurnMessage(ClientTurnMessage clientTurnMessage){
        turnMessage.setCastSpells(castToCastSpells(clientTurnMessage.getCastSpells()));
        turnMessage.setKings(castToKings(clientTurnMessage.getKings()));
        turnMessage.setUnits(castToUnits(clientTurnMessage.getUnits()));
    }

    private List<CastSpell> castToCastSpells(List<TurnCastSpell> turnCastSpells){
        return null;
    }

    private List<King> castToKings(List<TurnKing> turnKings){
        return null;
    }

    private List<Unit> castToUnits(List<TurnUnit> turnUnits){
        List<Unit> units = new ArrayList<>();
        for(TurnUnit turnUnit : turnUnits)
            units.add(castToUnit(turnUnit));
        return units;
    }

    private Unit castToUnit(TurnUnit turnUnit){
        Unit unit = new Unit();
        unit.setAffectedSpells(turnUnit.getAffectedSpells());
        unit.setAttack(turnUnit.getAttack());
//        unit.setBaseUnit();
//        unit.setCell(turnUnit.getCell());
        unit.setClone(turnUnit.isClone());
        unit.setDamageLevel(turnUnit.getDamageLevel());
        unit.setHasted(turnUnit.isHasted());
        unit.setHp(turnUnit.getHp());
//        unit.setPath();
        unit.setPlayerId(turnUnit.getPlayerId());
        unit.setRange(turnUnit.getRange());

        return unit;
    }

    /*private Spell castToCastSpell(TurnCastSpell turnCastSpell){
        Spell spell = new Spell();
        spell.setAreaSpell();
        spell.setDamaging();
        spell.getType();
        spell.setPower();
        spell.setTypeId();
        spell.setHaste();
        spell.setDuration();
        spell.setRange();
        return spell;
    }

    private King castToKing(TurnKing turnKing){
        King king = new King();
        king.setAlive(turnKing.isAlive());
        king.setAttack();
        king.setCenter();
        king.setHp(turnKing.getHp());
        king.setLevel();
        king.setPlayerId(turnKing.getPlayerId());
        king.setRange();
        king.setTargetType(turnKing.getTargetType());
        return king;
    }*/
}
