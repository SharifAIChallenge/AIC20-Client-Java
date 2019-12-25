package Client.dto.turn;

import java.util.List;

public class ClientTurnMessage {
    private int currTurn;
    private List<Integer> deck;
    private List<Integer> hand;
    private List<TurnKing> kings;
    private List<TurnUnit> units;
    private List<TurnCastSpell> castSpells;
    private int aquiredSpell;
    private int friendAquiredSpell;
    private List<Integer> mySpells;
    private List<Integer> friendSpells;
    private boolean gotRangeUpgrade;
    private boolean gotDamageUpgrade;
    private int availableRangeUpgrades;
    private int availableDamageUpgrades;

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

    public int getAquiredSpell() {
        return aquiredSpell;
    }

    public void setAquiredSpell(int aquiredSpell) {
        this.aquiredSpell = aquiredSpell;
    }

    public int getFriendAquiredSpell() {
        return friendAquiredSpell;
    }

    public void setFriendAquiredSpell(int friendAquiredSpell) {
        this.friendAquiredSpell = friendAquiredSpell;
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
}
