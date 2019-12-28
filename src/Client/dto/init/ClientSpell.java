package Client.dto.init;


import Client.Model.Spell;

public class ClientSpell {
    private int typeId;
    private int turnEffect;
    private boolean isAreaSpell;
    private int range;          //invalid for unit spell
    private int power;          //invalid for unit spell
    private boolean isDamaging; //invalid for unit spell

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
