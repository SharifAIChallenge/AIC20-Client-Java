package Client.dto.init;

import Client.Model.Spell;
import Client.Model.SpellTarget;
import Client.Model.SpellType;

public class ClientSpell {
    private String type;
    private int typeId;
    private int duration;
    private int priority; // todo is it needed ?
    private int range;          //invalid for unit spell
    private int power;          //invalid for unit spell
    private String target;

    public Spell castToSpell() {
        Spell spell = new Spell();
        spell.setType(SpellType.valueOf(type));
        spell.setTypeId(typeId);
        spell.setDuration(duration);
        spell.setRange(range);
        spell.setPower(power);
        spell.setPriority(priority);
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
