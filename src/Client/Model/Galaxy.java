package Client.Model;

import Client.dto.ClientCell;
import Client.dto.init.ClientBaseKing;
import Client.dto.init.ClientInitMessage;
import Client.dto.init.InitMessage;
import Client.dto.turn.ClientTurnMessage;

import java.util.ArrayList;
import java.util.List;

import Client.dto.init.ClientInitMessage;
import Client.dto.turn.TurnMessage;

public class Galaxy implements World {
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

    @Override
    public List<Unit> getAreaSpellTargets(Cell center, Spell spell) {
        return null;
    }

    @Override
    public List<Unit> getAreaSpellTargets(Cell center, int SpellId) {
        return null;
    }

    @Override
    public int getRemainingTurnsToUpgrade() {
        return 0;
    }

    @Override
    public int getRemainingTurnsToGetSpell() {
        return 0;
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
    public List<CastAreaSpell> getActiveSpellsOnCell(Cell cell) {
        List<CastAreaSpell> activeSpells = new ArrayList<CastAreaSpell>();
        for(CastSpell castSpell : turnMessage.getCastSpells()){
            if(castSpell instanceof CastAreaSpell){
                CastAreaSpell castAreaSpell = (CastAreaSpell) castSpell;
                Cell spellCell = castAreaSpell.getCenter();
                if(spellCell.getCol() == cell.getCol() && spellCell.getRow() == cell.getRow())
                    activeSpells.add(castAreaSpell);
            }
        }
        return activeSpells;
    }

    @Override
    public int getUpgradeTokenNumber() {
        // todo nadarim ke ino jaei
        return 0;
    }

    @Override
    public List<Spell> getSpells() {
        // todo in chio daghighan bar migardune ?
        return initMessage.getSpells();
    }

    @Override
    public Spell getReceivedSpell() {
        for (Spell spell : initMessage.getSpells()){
            if(spell.getType() == clientTurnMessage.getAcquiredSpell())
                return spell;
        }
        return null;
    }

    @Override
    public Spell getFriendReceivedSpell() {
        for(Spell spell : initMessage.getSpells()){
            if(spell.getType() == clientTurnMessage.getFriendAcquiredSpell())
                return spell;
        }
        return null;
    }
    public ClientInitMessage getClientInitMessage() {
        return clientInitMessage;
    }

    public void setClientInitMessage(ClientInitMessage clientInitMessage) {
        this.clientInitMessage = clientInitMessage;
    }
}
