package Client.dto.init;

import Client.Model.Spell;
import Client.Model.SpellTarget;
import Client.Model.SpellType;

/**
 * This class has initial information of the spell. Data is received from the server in the initial message.
 * Note that an unit spell hasn't range and power.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class ClientSpell {
    private int typeId;
    private int duration;
    private int priority;
    private int range;
    private int power;
    private String type;
    private String target;

    public Spell castToSpell() {
        Spell spell = new Spell();
        spell.setTypeId(typeId);
        spell.setDuration(duration);
        spell.setPriority(priority);
        spell.setRange(range);
        spell.setPower(power);
        spell.setType(SpellType.valueOf(type));
        spell.setTarget(SpellTarget.valueOf(target));
        return spell;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
