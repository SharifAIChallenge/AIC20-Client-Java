package Client.Model;

import java.util.List;

public class Unit {
    private int unitId, hp, playerId, typeId, pathId, damageLevel, rangeLevel, activePoisons, range, attack, target;
    private boolean isClone, isHasted, wasDamageUpgraded, wasRangeUpgraded, wasPlayedThisTurn;
    private List<Integer> affectedSpells;
    private Cell cell;
    private BaseUnit baseUnit;
    private Path path;
    private Cell targetCell;


    public Unit() {
    }

    public int getUnitID() {
        return unitId;
    }

    public void setUnitID(int unitID) {
        this.unitId = unitID;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isHasted() {
        return isHasted;
    }

    public void setHasted(boolean hasted) {
        isHasted = hasted;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public BaseUnit getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(BaseUnit baseUnit) {
        this.baseUnit = baseUnit;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public boolean isWasRangeUpgraded() {
        return wasRangeUpgraded;
    }

    public void setWasRangeUpgraded(boolean wasRangeUpgraded) {
        this.wasRangeUpgraded = wasRangeUpgraded;
    }

    public boolean isWasDamageUpgraded() {
        return wasDamageUpgraded;
    }

    public void setWasDamageUpgraded(boolean wasDamageUpgraded) {
        this.wasDamageUpgraded = wasDamageUpgraded;
    }

    public int getRangeLevel() {
        return rangeLevel;
    }

    public void setRangeLevel(int rangeLevel) {
        this.rangeLevel = rangeLevel;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public boolean isClone() {
        return isClone;
    }

    public void setClone(boolean clone) {
        isClone = clone;
    }

    public int getActivePoisons() {
        return activePoisons;
    }

    public void setActivePoisons(int activePoisons) {
        this.activePoisons = activePoisons;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
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

    public List<Integer> getAffectedSpells() {
        return affectedSpells;
    }

    public void setAffectedSpells(List<Integer> affectedSpells) {
        this.affectedSpells = affectedSpells;
    }

    public Cell getTargetCell() {
        return targetCell;
    }

    public void setTargetCell(Cell targetCell) {
        this.targetCell = targetCell;
    }
}
