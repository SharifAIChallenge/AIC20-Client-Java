package Client.dto.init;

import Client.Model.BaseUnit;
import Client.Model.UnitTarget;

/**
 * This class has information of the unit which is not used. Data is sent by the server in the initial message.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class ClientBaseUnit {
    private int ap;
    private int maxHP;
    private int typeId;
    private int baseAttack;
    private int baseRange;
    private String target;      // values: GROUND, AIR, BOTH
    private boolean isFlying;
    private boolean isMultiple;

    public BaseUnit castToBaseUnit() {
        BaseUnit baseUnit = new BaseUnit();
        baseUnit.setTypeId(typeId);
        baseUnit.setMaxHp(maxHP);
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
