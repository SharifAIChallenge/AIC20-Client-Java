package Client.Model;

import java.util.List;

public class Player {
    private int playerID, AP;
    private boolean upgradeTokens;
    private Deck deck;
    private Hand hand;
    private List<Spell> spells;


    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getAP() {
        return AP;
    }

    public void setAP(int AP) {
        this.AP = AP;
    }

    public boolean isUpgradeTokens() {
        return upgradeTokens;
    }

    public void setUpgradeTokens(boolean upgradeTokens) {
        this.upgradeTokens = upgradeTokens;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }
}
