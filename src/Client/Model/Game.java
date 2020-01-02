package Client.Model;

import Client.dto.init.ClientBaseKing;
import Client.dto.init.ClientInitMessage;
import Client.dto.init.InitMessage;
import Client.dto.turn.ClientTurnMessage;

import java.util.*;
import java.util.function.Consumer;

import Client.dto.turn.TurnMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import common.network.Json;
import common.network.data.Message;

import javax.swing.*;

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

    private List<ClientBaseKing> getEnemyKings(){
        List<ClientBaseKing> enemyKings = new ArrayList<>();
        for(ClientBaseKing clientBaseKing : clientInitMessage.getMap().getKings()){
            if(clientBaseKing.isYourFriend() || clientBaseKing.isYou())
                continue;
            enemyKings.add(clientBaseKing);
        }
        return enemyKings;
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

    private int getFriendIdOfPlayer(int playerId){
        if(playerId == getFriendId()) return getFriendId();
        if(playerId == getMyId()) return getMyId();
        if(playerId == getFirstEnemyId()) return getSecondEnemyId();
        if(playerId == getSecondEnemyId()) return getFirstEnemyId();
        return -1;
    }


    @Override
    public List<Path> getPathsFromPlayer(int playerId) {
        //todo
        King playerKing = getPlayerKing(playerId);
        King friendKing = getPlayerKing(getFriendIdOfPlayer(playerId));

        List<Path> paths = new ArrayList<>();
        for(Path path : initMessage.getMapp().getPaths())
            if(path.getCells().indexOf(playerKing) == 0 || path.getCells().lastIndexOf(playerKing) == path.getCells().size() -1)
                if(!path.getCells().contains(friendKing))
                    paths.add(path);
        return paths;
    }

    @Override
    public Path getPathToFriend(int playerId) {
        King playerKing = getPlayerKing(playerId);
        King friendKing = getPlayerKing(getFriendIdOfPlayer(playerId));

        for(Path path : initMessage.getMapp().getPaths()) {
            List<Cell> pathCells = path.getCells();
            if(pathCells.indexOf(playerKing) == 0 || pathCells.lastIndexOf(playerKing) == pathCells.size() - 1)
                if(pathCells.indexOf(friendKing) == 0 || pathCells.lastIndexOf(friendKing) == pathCells.size() - 1)
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
            if(path.getCells().indexOf(playerCell) != 0)
                Collections.reverse(path.getCells());
            if(path.getCells().indexOf(playerCell) == 0){
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
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("typeId", Json.GSON.toJsonTree(typeId));
        jsonObject.add("pathId", Json.GSON.toJsonTree(pathId));
        Message message = new Message("putUnit", this.getCurrentTurn(), jsonObject);
        sender.accept(message);
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

    private Path getPathById(int pathId){
        for(Path path : this.initMessage.getMapp().getPaths())
            if(path.getId() == pathId)
                return path;
        return null;
    }

    @Override
    public void castUnitSpell(int unitId, int pathId, int index, int spellId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("typeId", Json.GSON.toJsonTree(spellId));
        jsonObject.add("unitId", Json.GSON.toJsonTree(unitId));
        jsonObject.add("pathId", Json.GSON.toJsonTree(pathId));
        Path path = getPathById(pathId);
        Cell cell = null;
        if(path != null && index < path.getCells().size())
            cell = path.getCells().get(index);
        jsonObject.add("cell", Json.GSON.toJsonTree(cell));
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
        jsonObject.add("cell", Json.GSON.toJsonTree(cell));
        Message message = new Message("castSpell", this.getCurrentTurn(), jsonObject);
        sender.accept(message);
    }

    @Override
    public void castAreaSpell(int row, int col, Spell spell) {
        int spellId = spell.getTypeId();
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
        Spell spell = getSpellById(spellId);
        return getAreaSpellTargets(center, spell);
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

    private Cell getCellByCoordination(int row, int col){
        for(Path path : initMessage.getMapp().getPaths())
            for(Cell cell : path.getCells())
                if(cell.getRow() == row && cell.getCol() == col)
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
                return unit;;
        return null;
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

    @Override
    public void upgareUnitRange(Unit unit) {

    }

    @Override
    public void upgradeUnitRange(int unitId) {

    }

    @Override
    public void upgradeUnitDamage(Unit unit) {

    }

    @Override
    public void upgradeUnitDamage(int unitId) {

    }

    @Override
    public List<Unit> getPlayerDuplicateUnits(int playerId) {
        return null;
    }

    @Override
    public List<Unit> getPlayerHastedUnits(int playerId) {
        return null;
    }

    @Override
    public List<Unit> getPlayerPlayedUnits(int playerId) {
        return null;
    }

    @Override
    public Unit getUnitTarget(Unit unit) {
        return null;
    }

    @Override
    public Unit getUnitTarge(int unitId) {
        return null;
    }

    @Override
    public Cell getUnitTargetCell(Unit unit) {
        return null;
    }

    @Override
    public Cell getUnitTargetCell(int unitId) {
        return null;
    }

    @Override
    public Unit getKingTarget(int playerId) {
        return null;
    }

    @Override
    public Cell getKingTargetCell(int playerId) {
        return null;
    }

    @Override
    public int getKingUnitIsAttackingTo(Unit unit) {
        return 0;
    }

    @Override
    public int getKingUnitIsAttackingTo(int unitId) {
        return 0;
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
