package Client.dto.init;

/**
 * This class has constants of the game.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class GameConstants {
    private int maxAP;
    private int maxTurns;
    private int turnTimeout;
    private int pickTimeout;
    private int turnsToUpgrade;
    private int turnsToSpell;
    private int damageUpgradeAddition;
    private int rangeUpgradeAddition;
    private int deckSize;
    private int handSize;

    public int getMaxAP() {
        return maxAP;
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public int getTurnTimeout() {
        return turnTimeout;
    }

    public int getPickTimeout() {
        return pickTimeout;
    }

    public int getTurnsToUpgrade() {
        return turnsToUpgrade;
    }

    public int getTurnsToSpell() {
        return turnsToSpell;
    }

    public int getDamageUpgradeAddition() {
        return damageUpgradeAddition;
    }

    public int getRangeUpgradeAddition() {
        return rangeUpgradeAddition;
    }

    public int getDeckSize() {
        return deckSize;
    }

    public int getHandSize() {
        return handSize;
    }
}
