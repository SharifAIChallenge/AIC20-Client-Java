package Client.Model;

import Client.dto.ClientCell;
import Client.dto.init.ClientBaseKing;
import Client.dto.init.ClientInitMessage;
import Client.dto.init.InitMessage;
import Client.dto.turn.ClientTurnMessage;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Client.dto.init.ClientInitMessage;
import Client.dto.turn.TurnMessage;

public class Game implements World {
    private ClientInitMessage clientInitMessage;
    private ClientTurnMessage clientTurnMessage;
    private InitMessage initMessage;
    private TurnMessage turnMessage;


    @Override
    public void chooseDeck(List<Integer> typeIds) {
        //tOdO
    }

    @Override
    public int getMyId() {
        for(ClientBaseKing clientBaseKing : clientInitMessage.getMap().getKings()){
            if(clientBaseKing.isYou())
                return clientBaseKing.getPlayerId();
        }
        return -1; // impossible
    }

    @Override
    public int getFriendId() {
        for(ClientBaseKing clientBaseKing : clientInitMessage.getMap().getKings())
            if(clientBaseKing.isYourFriend())
                return clientBaseKing.getPlayerId();
        return -1; // impossible
    }

    @Override
    public Cell getPLayerPosition(int playerId) {
        for(King king : initMessage.getMapp().getKings())
            if(king.getPlayerID() == playerId)
                return king.getCenter();
        return null; // impossible
    }

    @Override
    public List<Path> getPathsFromPlayer(int playerID) {
        //todo
        return null;
    }

    @Override
    public Path getPathToFriend(int playerId) {
        //todo
        return null;
    }


    // is it right ?
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
        return null;
    }

    @Override
    public List<Unit> getPlayerUnits(int playerId) {
        List<Unit> units = new ArrayList<>();
        for(Unit unit : turnMessage.getUnits())
            if(unit.getPlayerID() == playerId)
                units.add(unit);
        return units;
    }

    @Override
    public List<Unit> getCellUnits(Cell cell) {
        List<Unit> units = new ArrayList<>(cell.getUnitList());
        return units;
    }

    @Override
    public Path getShortestPathToCell(int fromPlayerId, Cell cell) {
        //todo
        return null;
    }

    @Override
    public int getMaxAP() {
        return clientInitMessage.getGameConstants().getMaxAP();
    }

    @Override
    public int getRemainingAP() {
        return 0;
    }

    @Override
    public List<Unit> getHand() {
        List<Unit> hand = new ArrayList<>();
        for(int unitId : clientTurnMessage.getHand())
            for(Unit unit : turnMessage.getUnits())
                if(unit.getUnitID() == unitId) {
                    hand.add(unit);
                    break;
                }
        return hand;
    }

    @Override
    public List<Unit> getDeck() {
        List<Unit> deck = new ArrayList<>();
        for(int unitId:clientTurnMessage.getDeck())
            for(Unit unit : turnMessage.getUnits())
                if(unit.getUnitID() == unitId) {
                    deck.add(unit);
                    break;
                }
        return deck;
    }

    @Override
    public void playUnit(int typeId, int pathId) {
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
        //TODO
        return 0;
    }

    @Override
    public int getPlayerHP(int playerId) {
        for(King king : turnMessage.getKings())
            if(king.getPlayerID() == playerId)
                return king.getHP();
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
    public void castAreaSpell(Cell center, int spellId) {
        //todo
    }

    @Override
    public void castAreaSpell(Cell center, Spell spell) {
        //todo
    }

    private boolean inRange(Cell cell, Cell center, int range){
        if(Math.abs(cell.getCol() - center.getCol()) > range)
            return false;
        if(Math.abs(cell.getRow() - center.getRow()) > range)
            return false;
        return true;
    }

    private ArrayList<Unit> unique(List<Unit> listOfUnits) {
        ArrayList<Unit> uniqueList = new ArrayList<>();
        for(Unit unit : listOfUnits) {
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
        AreaSpell areaSpell = (AreaSpell) spell;
        for(Path path: initMessage.getMapp().getPaths()){
            for (Cell cell : path.getCellList())
                if(inRange(cell, center, areaSpell.getRange()))
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
    public int getRemainingTurnsToUpgrade() {
        return clientTurnMessage.getCurrTurn() % clientInitMessage.getGameConstants().getTurnsToUpgrade();
    }

    @Override
    public int getRemainingTurnsToGetSpell() {
        return clientTurnMessage.getCurrTurn() % clientInitMessage.getGameConstants().getTurnsToSpell();
    }

    @Override
    public CastAreaSpell getCastAreaSpell(int playerId) {
        return null;
    }

    @Override
    public CastUnitSpell getCastUnitSpell(int playerId) {
        return null;
    }

    @Override
    public List<CastAreaSpell> getActiveSpellsOnUnit(Unit unit) {
        //todo
        return null;
    }

    @Override
    public int getDamageUpgradeTokenNumbers() {
        //avaz kardimesh
        return clientTurnMessage.getAvailableDamageUpgrades();
    }

    public int getRangeUpgradeTokenNumbers() {
        //inam khodemnu ezade kardim
        return clientTurnMessage.getAvailableRangeUpgrades();
    }

    private List<Spell> getSpellsByIds(List<Integer> list){
        List<Spell> spells = new ArrayList<>();
        for(int spellId : list){
            for(Spell spell : initMessage.getSpells()){
                if(spell.getType() == spellId)
                    spells.add(spell);
            }
        }
        return spells;

    }

    @Override
    public List<Spell> getMySpells() {
        //spellId hamun type e ?
        return getSpellsByIds(clientTurnMessage.getMySpells());
    }

    @Override
    public Spell getReceivedSpell() {
        return getReceivedSpell();
    }

    private List<Spell> getFriendSpells(){
        return getSpellsByIds(clientTurnMessage.getFriendSpells());
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

    private Spell getSpellById(int spellId){
        for(Spell spell : initMessage.getSpells())
            if(spell.getType() == spellId)
                return spell;

        return null;
    }
}
