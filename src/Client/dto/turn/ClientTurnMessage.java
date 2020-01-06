package Client.dto.turn;


import Client.Model.InitMessage;
import Client.Model.King;
import Client.Model.TurnMessage;
import Client.dto.init.ClientBaseUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientTurnMessage {
    private int currTurn;
    private List<Integer> deck;
    private List<Integer> hand;

    private List<TurnKing> kings;
    private List<TurnUnit> units;
    private List<TurnCastSpell> castSpells;

    private int receivedSpell;
    private int friendReceivedSpell;
    private List<Integer> mySpells;
    private List<Integer> friendSpells;

    //deck o hand class nistan?

    private boolean gotRangeUpgrade;
    private boolean gotDamageUpgrade;
    private int availableRangeUpgrades;
    private int availableDamageUpgrades;

    private int remainingAP;

    public TurnMessage castToTurnMessage(InitMessage initMessage){
        TurnMessage turnMessage = new TurnMessage();

        turnMessage.setKings(new ArrayList<>());
        for(int i = 0; i < kings.size(); i++){
            King king = initMessage.getMapp().getKings().get(i);
            TurnKing turnKing = kings.get(i);
            update(king, turnKing);
            turnMessage.getKings().add(king);
        }

        turnMessage.setCastSpells(
                castSpells.stream().map(TurnCastSpell::castToCastSpell).collect(Collectors.toList())
        );
        turnMessage.setUnits(
                units.stream().map(TurnUnit::castToUnit).collect(Collectors.toList())
        );
        return turnMessage;
    }

    private void update(King king, TurnKing turnKing) {
        king.setPlayerId(turnKing.getPlayerId());
        king.setAlive(turnKing.isAlive());
        king.setHp(turnKing.getHp());
        king.setTarget(turnKing.getTarget());
    }

    public ClientTurnMessage() {

    }

    public int getCurrTurn() {

        return currTurn;
    }

    public void setCurrTurn(int currTurn) {
        this.currTurn = currTurn;
    }

    public List<Integer> getDeck() {
        return deck;
    }

    public void setDeck(List<Integer> deck) {
        this.deck = deck;
    }

    public List<Integer> getHand() {
        return hand;
    }

    public void setHand(List<Integer> hand) {
        this.hand = hand;
    }

    public List<TurnKing> getKings() {
        return kings;
    }

    public void setKings(List<TurnKing> kings) {
        this.kings = kings;
    }

    public List<TurnUnit> getUnits() {
        return units;
    }

    public void setUnits(List<TurnUnit> units) {
        this.units = units;
    }

    public List<TurnCastSpell> getCastSpells() {
        return castSpells;
    }

    public void setCastSpells(List<TurnCastSpell> castSpells) {
        this.castSpells = castSpells;
    }

    public List<Integer> getMySpells() {
        return mySpells;
    }

    public void setMySpells(List<Integer> mySpells) {
        this.mySpells = mySpells;
    }

    public List<Integer> getFriendSpells() {
        return friendSpells;
    }

    public void setFriendSpells(List<Integer> friendSpells) {
        this.friendSpells = friendSpells;
    }

    public boolean isGotRangeUpgrade() {
        return gotRangeUpgrade;
    }

    public void setGotRangeUpgrade(boolean gotRangeUpgrade) {
        this.gotRangeUpgrade = gotRangeUpgrade;
    }

    public boolean isGotDamageUpgrade() {
        return gotDamageUpgrade;
    }

    public void setGotDamageUpgrade(boolean gotDamageUpgrade) {
        this.gotDamageUpgrade = gotDamageUpgrade;
    }

    public int getAvailableRangeUpgrades() {
        return availableRangeUpgrades;
    }

    public void setAvailableRangeUpgrades(int availableRangeUpgrades) {
        this.availableRangeUpgrades = availableRangeUpgrades;
    }

    public int getAvailableDamageUpgrades() {
        return availableDamageUpgrades;
    }

    public void setAvailableDamageUpgrades(int availableDamageUpgrades) {
        this.availableDamageUpgrades = availableDamageUpgrades;
    }

    public int getFriendReceivedSpell() {
        return friendReceivedSpell;
    }

    public void setFriendReceivedSpell(int friendReceivedSpell) {
        this.friendReceivedSpell = friendReceivedSpell;
    }

    public int getReceivedSpell() {
        return receivedSpell;
    }

    public void setReceivedSpell(int receivedSpell) {
        this.receivedSpell = receivedSpell;
    }

    public int getRemainingAP() {
        return remainingAP;
    }

    public void setRemainingAP(int remainingAP) {
        this.remainingAP = remainingAP;
    }
}
