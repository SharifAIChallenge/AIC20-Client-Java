package Client.Model;

public class Spell {
    private int turnEffect;
    private int typeId;
    private boolean isAreaSpell;
    private boolean isHaste;
    private int range;
    private int power;
    private boolean isDamaging;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTurnEffect() {
        return turnEffect;
    }

    public void setTurnEffect(int turnEffect) {
        this.turnEffect = turnEffect;
    }

    public boolean isAreaSpell() {
        return isAreaSpell;
    }

    public void setAreaSpell(boolean areaSpell) {
        isAreaSpell = areaSpell;
    }

    public boolean isHaste() {
        return isHaste;
    }

    public void setHaste(boolean haste) {
        isHaste = haste;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isDamaging() {
        return isDamaging;
    }

    public void setDamaging(boolean damaging) {
        isDamaging = damaging;
    }
}
