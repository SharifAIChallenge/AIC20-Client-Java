package Client.Model;

public class AreaSpell extends Spell{
    private int range;
    private int power;
    private boolean isDamaging;

    public boolean isDamaging() {
        return isDamaging;
    }

    public void setDamaging(boolean damaging) {
        isDamaging = damaging;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
