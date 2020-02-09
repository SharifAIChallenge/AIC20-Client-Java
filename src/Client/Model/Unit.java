package Client.Model;

import java.util.List;

/**
 * This class has properties of the unit that is cast.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class Unit {
    private int unitId;
    private int hp;
    private int playerId;
    private int damageLevel;
    private int rangeLevel;
    private int range;
    private int attack;
    private boolean isDuplicate;
    private boolean isHasted;
    private Cell cell;
    private Path path;
    private Unit target;
    private Cell targetCell;
    private King targetIfKing;
    private BaseUnit baseUnit;
    private List<CastSpell> affectedSpells;


    public Unit() {
    }

    public int getUnitId() {
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

    public void setUnitId(int unitId) {
        this.unitId = unitId;
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

    public Cell getTargetCell() {
        return targetCell;
    }

    public void setTargetCell(Cell targetCell) {
        this.targetCell = targetCell;
    }

    public Unit getTarget() {
        return target;
    }

    public void setTarget(Unit target) {
        this.target = target;
    }

    public King getTargetIfKing() {
        return targetIfKing;
    }

    public void setTargetIfKing(King targetIfKing) {
        this.targetIfKing = targetIfKing;
    }

    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }

    public List<CastSpell> getAffectedSpells() {
        return affectedSpells;
    }

    public void setAffectedSpells(List<CastSpell> affectedSpells) {
        this.affectedSpells = affectedSpells;
    }
}
