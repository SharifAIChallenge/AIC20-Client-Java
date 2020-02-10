package Client.dto.turn;


import Client.Model.*;
import Client.dto.ClientCell;

import java.util.List;

/**
 * This class has properties of unit which is used. The data is sent by server each turn.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class TurnUnit {
    private int unitId;
    private int playerId;
    private int hp;
    private int typeId;
    private int pathId;     // valid for player and his friend
    private int damageLevel;
    private int rangeLevel;
    private int range;
    private int attack;
    private int target;
    private boolean wasDamageUpgraded;
    private boolean wasRangeUpgraded;
    private boolean isHasted;
    private boolean isDuplicate;
    private boolean wasPlayedThisTurn;
    private ClientCell cell;
    private ClientCell targetCell;
    private List<Integer> affectedSpells;

    public Unit castToUnit(){
        Unit unit = new Unit();

        unit.setUnitId(this.unitId);
        unit.setRange(this.range);
        unit.setPlayerId(this.playerId);
        unit.setHp(this.hp);
        unit.setHasted(this.isHasted);
        unit.setDamageLevel(this.damageLevel);
        unit.setRangeLevel(this.rangeLevel);
        unit.setAttack(this.attack);
        unit.setDuplicate(this.isDuplicate);
        unit.setHasted(this.isHasted);

        for(BaseUnit baseUnit: InitMessage.getInitMessage().getBaseUnitList())
            if(baseUnit.getTypeId() == this.typeId)
                unit.setBaseUnit(baseUnit);

        unit.setPath(InitMessage.getInitMessage().getPathById(this.pathId));

        if(this.targetCell != null)
            unit.setTargetCell(Map.getMap().getCells()[this.targetCell.getRow()][this.targetCell.getCol()]);
        unit.setCell(Map.getMap().getCells()[this.cell.getRow()][this.cell.getCol()]);
        unit.setUnitId(this.unitId);
        return unit;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }

    public int getRangeLevel() {
        return rangeLevel;
    }

    public void setRangeLevel(int rangeLevel) {
        this.rangeLevel = rangeLevel;
    }

    public boolean isHasted() {
        return isHasted;
    }

    public void setHasted(boolean hasted) {
        isHasted = hasted;
    }

    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public boolean isWasPlayedThisTurn() {
        return wasPlayedThisTurn;
    }

    public void setWasPlayedThisTurn(boolean wasPlayedThisTurn) {
        this.wasPlayedThisTurn = wasPlayedThisTurn;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public ClientCell getCell() {
        return cell;
    }

    public void setCell(ClientCell cell) {
        this.cell = cell;
    }

    public List<Integer> getAffectedSpells() {
        return affectedSpells;
    }

    public void setAffectedSpells(List<Integer> affectedSpells) {
        this.affectedSpells = affectedSpells;
    }

    public ClientCell getTargetCell() {
        return targetCell;
    }

    public void setTargetCell(ClientCell targetCell) {
        this.targetCell = targetCell;
    }

    public boolean isWasDamageUpgraded() {
        return wasDamageUpgraded;
    }

    public void setWasDamageUpgraded(boolean wasDamageUpgraded) {
        this.wasDamageUpgraded = wasDamageUpgraded;
    }

    public boolean isWasRangeUpgraded() {
        return wasRangeUpgraded;
    }

    public void setWasRangeUpgraded(boolean wasRangeUpgraded) {
        this.wasRangeUpgraded = wasRangeUpgraded;
    }
}
