package Client.dto.init;


public class GameConstants {
    private int maxAP;
    private int maxTurns;
    private int turnTimeout;
    private int pickTimeout;
    private int turnsToUpgrade;
    private int turnsToSpell;
    private int damageUpgradeAddition;
    private int rangeUpgradeAddition;
    private int deckSize;   //todo getter
    private int handSize;

    public int getMaxAP() {
        return maxAP;
    }

    public void setMaxAP(int maxAP) {
        this.maxAP = maxAP;
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    public int getTurnTimeout() {
        return turnTimeout;
    }

    public void setTurnTimeout(int turnTimeout) {
        this.turnTimeout = turnTimeout;
    }

    public int getPickTimeout() {
        return pickTimeout;
    }

    public void setPickTimeout(int pickTimeout) {
        this.pickTimeout = pickTimeout;
    }

    public int getTurnsToUpgrade() {
        return turnsToUpgrade;
    }

    public void setTurnsToUpgrade(int turnsToUpgrade) {
        this.turnsToUpgrade = turnsToUpgrade;
    }

    public int getTurnsToSpell() {
        return turnsToSpell;
    }

    public void setTurnsToSpell(int turnsToSpell) {
        this.turnsToSpell = turnsToSpell;
    }

    public int getDamageUpgradeAddition() {
        return damageUpgradeAddition;
    }

    public void setDamageUpgradeAddition(int damageUpgradeAddition) {
        this.damageUpgradeAddition = damageUpgradeAddition;
    }

    public int getRangeUpgradeAddition() {
        return rangeUpgradeAddition;
    }

    public void setRangeUpgradeAddition(int rangeUpgradeAddition) {
        this.rangeUpgradeAddition = rangeUpgradeAddition;
    }
}
