package Client.Model;

public class Spell {
    private SpellType type;
    private int typeId;
    private int duration;
    private int priority;
    private SpellTarget target;

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
}
