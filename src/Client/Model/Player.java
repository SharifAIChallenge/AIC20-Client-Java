package Client.Model;

import java.util.List;

public class Player {
    private int playerID, AP;
    private boolean upgradeTokens;
    private List<Spell> spells;
    private List<Unit> hand;
    private List<Unit> deck;

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


    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }

    public List<Unit> getHand() {
        return hand;
    }

    public void setHand(List<Unit> hand) {
        this.hand = hand;
    }

    public List<Unit> getDeck() {
        return deck;
    }

    public void setDeck(List<Unit> deck) {
        this.deck = deck;
    }
}
