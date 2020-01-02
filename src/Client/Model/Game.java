package Client.Model;

import Client.dto.init.ClientBaseKing;
import Client.dto.init.ClientInitMessage;
import Client.dto.init.InitMessage;
import Client.dto.turn.ClientTurnMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private Consumer<Message> sender;

    public Game(Consumer sender){
        this.sender = sender;
    }

    public Game(Game game){
        this.clientInitMessage = game.getClientInitMessage();
        this.initMessage = game.getInitMessage();
    }

    public InitMessage getInitMessage() {
        return initMessage;
    }

    public void setInitMessage(InitMessage initMessage){
        this.initMessage = initMessage;
    }

    public TurnMessage getTurnMessage() {
        return turnMessage;
    }

    public void setTurnMessage(TurnMessage turnMessage){
        this.turnMessage = turnMessage;
    }

    public ClientTurnMessage getClientTurnMessage() {
        return clientTurnMessage;
    }

    public void setClientTurnMessage(ClientTurnMessage clientTurnMessage){
        this.clientTurnMessage = clientTurnMessage;
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

    private List<ClientBaseKing> getEnemyKings(){
        List<ClientBaseKing> enemyKings = new ArrayList<>();
        for(ClientBaseKing clientBaseKing : clientInitMessage.getMap().getKings()){
            if(clientBaseKing.isYourFriend() || clientBaseKing.isYou())
                continue;
            enemyKings.add(clientBaseKing);
        }
        return enemyKings;
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

    private King getPlayerKing(int playerId){
        for(King king : initMessage.getMapp().getKings())
            if(king.getPlayerId() == playerId){
                return king;
            }
        return null;
    }

    @Override
    public List<Path> getPathsFromPlayer(int playerID) {
        //todo
        King playerKing = getPlayerKing(playerID);
        List<Path> paths = new ArrayList<>();
        for(Path path : initMessage.getMapp().getPaths())
            if(path.getCells().contains(playerKing.getCenter()))
                paths.add(path);
        return paths;
    }

    @Override
    public Path getPathToFriend(int playerId) {
        //todo
        Cell myCell = getPlayerPosition(playerId);
        Cell friendCell = getPlayerPosition(getFriendId());
        Cell firstEnemyCell = getPlayerPosition(getFirstEnemyId());
        Cell secondEnemyCell = getPlayerPosition(getSecondEnemyId());

        for(Path path : initMessage.getMapp().getPaths()) {
            List<Cell> pathCells = path.getCells();
            if (pathCells.contains(myCell) && pathCells.contains(friendCell))
                if (!pathCells.contains(firstEnemyCell) && !pathCells.contains(secondEnemyCell))
                    return path;
        }
        return null;
    }


    @Override
    public int getMapHeight() {
        return clientInitMessage.getMap().getRows();
    }

    @Override
    public int getMapWidth() {
        return clientInitMessage.getMap().getCols();
    }


    @Override
    public List<Path> getPathsCrossingCell(Cell cell) {
        List<Path> paths = new ArrayList<>();
        for (Path path : initMessage.getMapp().getPaths())
            if(path.getCells().contains(cell))
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
        for(Path path : initMessage.getMapp().getPaths()){
            if(path.getCells().contains(cell) && path.getCells().contains(playerCell)){
                int index = path.getCells().indexOf(cell);
                if(index < minDis){
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

    private Cell getCellByCoordination(int row, int col){
        for(Path path : initMessage.getMapp().getPaths())
            for(Cell cell : path.getCells())
                if(cell.getRow() == row && cell.getCol() == col)
                    return cell;
        return null;
    }

    @Override
    public List<Unit> getAreaSpellTargets(int row, int col, Spell spell){
        Cell cell = getCellByCoordination(row, col);
        return getAreaSpellTargets(cell, spell);
    }

    @Override
    public List<Unit> getAreaSpellTargets(int row, int col, int spellId){
        Cell cell = getCellByCoordination(row, col);
        return getAreaSpellTargets(cell, spellId);
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
    public int getActivePoisonsOnUnit(Unit unit) {
        return unit.getActivePoisons();
    }

    private Unit getUnitById(int unitId){
        for(Unit unit : turnMessage.getUnits())
            if(unit.getUnitID() == unitId)
                return unit;;
        return null;
    }

    @Override
    public int getActivePoisonsOnUnit(int unitId) {
        Unit unit = getUnitById(unitId);
        return getActivePoisonsOnUnit(unit);
    }

    @Override
    public int getDamageUpgradeNumber() {
        //avaz kardimesh
        return clientTurnMessage.getAvailableDamageUpgrades();
    }

    @Override
    public int getRangeUpgradeNumber() {
        //inam khodemnu ezade kardim
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
    public HashMap<Spell, Integer> getSpells(){
        List<Spell> spells = getSpellsByIds(clientTurnMessage.getMySpells());
        HashMap<Spell, Integer> hashMap = new HashMap<Spell, Integer>();
        for (Spell spell : spells){
            int currentCounter = 0;
            if(hashMap.containsKey(spell)){
                currentCounter = hashMap.get(spell);
                hashMap.remove(spell);
            }
            currentCounter ++;
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

    public void handleInitMessage(Message msg) {
        this.clientInitMessage = Json.GSON.fromJson(msg.getInfo(), clientInitMessage.getClass());
        castToInitMessage(this.clientInitMessage);
    }

    public void castToInitMessage(ClientInitMessage clientInitMessage){
        //todo
    }

    public void handleTurnMessage(Message msg) {
        this.clientTurnMessage = Json.GSON.fromJson(msg.getInfo(), clientTurnMessage.getClass());
    }
}
