package Client.Model;

/**
 * This class has properties of spell that is not used yet.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class Spell {
    private int typeId;
    private int duration;
    private int priority;
    private int range;
    private int power;
    private boolean isDamaging;
    private SpellType type;
    private SpellTarget target;

    public boolean isAreaSpell() {
        return type != SpellType.TELE;
    }

    public boolean isUnitSpell() {
        return type == SpellType.TELE;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public SpellType getType() {
        return type;
    }

    public void setType(SpellType type) {
        this.type = type;
    }

    public SpellTarget getTarget() {
        return target;
    }

    public void setTarget(SpellTarget target) {
        this.target = target;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

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
