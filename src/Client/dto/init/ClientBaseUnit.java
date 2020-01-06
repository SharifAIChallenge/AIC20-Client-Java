package Client.dto.init;

import Client.Model.BaseUnit;
import Client.Model.UnitTarget;

public class ClientBaseUnit {
    private int typeId;
    private int maxHP;
    private int baseAttack;
    private int baseRange;
    private String target;      //can be enum, values: GROUND, AIR, BOTH
    private boolean isFlying;
    private boolean isMultiple;
    private int ap;

    public BaseUnit castToBaseUnit() {
        BaseUnit baseUnit = new BaseUnit();
        baseUnit.setTypeId(typeId);
        baseUnit.setMaxHP(maxHP);
        baseUnit.setBaseAttack(baseAttack);
        baseUnit.setBaseRange(baseRange);
        baseUnit.setTargetType(UnitTarget.valueOf(target));
        baseUnit.setFlying(isFlying);
        baseUnit.setMultiple(isMultiple);
        baseUnit.setAp(ap);
        return baseUnit;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
    }

    public int getBaseRange() {
        return baseRange;
    }

    public void setBaseRange(int baseRange) {
        this.baseRange = baseRange;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }
}
