package Client.Model;

public class Spell {
    private SpellType type;
    private int typeId;
    private int duration;
    private int range;
    private int power;
    private SpellTarget target;
    //todo priority?

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
}
