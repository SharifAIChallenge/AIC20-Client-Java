package Client.Model;

import Client.dto.ClientCell;
import Client.dto.init.ClientBaseKing;
import Client.dto.init.ClientInitMessage;
import Client.dto.init.InitMessage;
import Client.dto.turn.ClientTurnMessage;

import java.awt.image.AreaAveragingScaleFilter;
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
        for(King king : initMessage.getMap().getKings())
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
        List<Unit> units = new ArrayList<>();
        for(Unit unit : cell.getUnitList())
            units.add(unit);
        return units;
    }

    @Override
    public Path getShortestPathToCell(int fromPlayerId, Cell cell) {
        return null;
    }

    @Override
    public int getMaxAP() {
        return clientInitMessage.getGameConstants().getMaxAP();
    }

    @Override
    public int getRemainingAP() {
        // TODO
        return 0;
    }

    @Override
    public List<Unit> getHand() {
        return null;
    }

    @Override
    public List<Unit> getDeck() {
        return null;
    }

    @Override
    public void playUnit(int typeId, int pathId) {

    }

    @Override
    public int getCurrentTurn() {
        return 0;
    }

    @Override
    public int getMaxTurns() {
        return 0;
    }

    @Override
    public int getpickTimeout() {
        return 0;
    }

    @Override
    public int getTurnTimeout() {
        return 0;
    }

    @Override
    public int getRemainingTime() {
        return 0;
    }

    @Override
    public int getPlayerHP(int playerId) {
        return 0;
    }

    @Override
    public void castUnitSpell(int unitId, int pathId, int index, int spellId) {

    }

    @Override
    public void castUnitSpell(int unitId, int pathId, int index, Spell spell) {

    }

    @Override
    public void castAreaSpell(Cell center, int spellId) {

    }

    @Override
    public void castAreaSpell(Cell center, Spell spell) {

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
        return null;
    }

    @Override
    public int getUpgradeTokenNumber() {
        return 0;
    }

    @Override
    public List<Spell> getSpells() {
        return null;
    }

    @Override
    public Spell getReceivedSpell() {
        return null;
    }

    @Override
    public Spell getFriendReceivedSpell() {
        return null;
    }
    public ClientInitMessage getClientInitMessage() {
        return clientInitMessage;
    }

    public void setClientInitMessage(ClientInitMessage clientInitMessage) {
        this.clientInitMessage = clientInitMessage;
    }
}
