package Client.Model;

import Client.dto.init.ClientBaseKing;
import Client.dto.init.ClientInitMessage;
import Client.dto.init.InitMessage;
import Client.dto.turn.ClientTurnMessage;

import java.util.ArrayList;
import java.util.List;

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
        for (ClientBaseKing clientBaseKing : clientInitMessage.getMap().getKings()) {
            if (clientBaseKing.isYou())
                return clientBaseKing.getPlayerId();
        }
        return -1; // impossible
    }

    @Override
    public int getFriendId() {
        for (ClientBaseKing clientBaseKing : clientInitMessage.getMap().getKings())
            if (clientBaseKing.isYourFriend())
                return clientBaseKing.getPlayerId();
        return -1; // impossible
    }

    @Override
    public Cell getPLayerPosition(int playerId) {
        for (King king : initMessage.getMapp().getKings())
            if (king.getPlayerId() == playerId)
                return king.getCenter();
        return null; // impossible
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
        List<Path> paths = new ArrayList<>();
        for (Path path : initMessage.getMapp().getPaths()) {
            boolean hasCell = false;
            for (Cell pathCell : path.getCells())
                if(cell.getRow() == pathCell.getRow() && cell.getCol() == pathCell.getCol())
                    hasCell = true;
            if(hasCell)paths.add(path);
        }
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
        King playerKing = null;
        for(King king : initMessage.getMapp().getKings())
            if(king.getPlayerId() == fromPlayerId){
                playerKing = king;
                break;
            }
        int minDis = 100 * 1000;
        Path bestPath = null;
        for(Path path : initMessage.getMapp().getPaths()){
            if(path.getCells().contains(cell)){
                int index = path.getCells().indexOf(cell);
                if(index < minDis){
                    minDis = index;
                    bestPath = path;
                }
            }
        }

        //todo
        return bestPath;
    }

    @Override
    public int getMaxAP() {
        return clientInitMessage.getGameConstants().getMaxAP();
    }

    @Override
    public int getRemainingAP() {
        return clientTurnMessage.getRemainigAP();
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
        for (King king : turnMessage.getKings())
            if (king.getPlayerId() == playerId)
                return king.getHp();
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
    public int getRemainingTurnsToUpgrade() {
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
    public List<CastAreaSpell> getActiveSpellsOnUnit(Unit unit) {
        //todo
      /*  List<CastAreaSpell>
        for(CastSpell castSpell : turnMessage.getCastSpells()){
            if(castSpell.getAffectedUnits().contains(unit.getUnitId())){

            }
        }*/
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
    public List<Spell> getMySpells() {
        //spellId hamun type e ?
        return getSpellsByIds(clientTurnMessage.getMySpells());
    }

    @Override
    public Spell getReceivedSpell() {
        return getReceivedSpell();
    }

    private List<Spell> getFriendSpells() {
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

    private Spell getSpellById(int spellId) {
        for (Spell spell : initMessage.getSpells())
            if (spell.getTypeId() == spellId)
                return spell;

        return null;
    }
}
