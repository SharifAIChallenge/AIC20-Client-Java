package Client.dto.turn;


import Client.Model.*;
import Client.dto.ClientCell;

import java.util.List;

public class TurnUnit {
    private boolean wasDamageUpgraded;
    private boolean wasRangeUpgraded;
    private int unitId;
    private int playerId;
    private int typeId;
    private int pathId;     // valid for player and his friend
    private ClientCell cell;
    private int hp;
    private int damageLevel;
    private int rangeLevel;
    private boolean isHasted;
    private boolean isDuplicate;
    private int range;
    private int attack;
    private boolean wasPlayedThisTurn;
    private List<Integer> affectedSpells;
    private int target;
    private ClientCell targetCell;
    //todo target cell i darim ke nemidunam chie

    public Unit castToUnit(){
        Unit unit = new Unit();
        unit.setUnitId(unitId);
        unit.setRange(range);
        unit.setPlayerId(playerId);
        unit.setHp(hp);
        unit.setHasted(isHasted);
        unit.setDamageLevel(damageLevel);
        unit.setRangeLevel(rangeLevel);
        unit.setAttack(attack);
        unit.setDuplicate(isDuplicate);
        unit.setHasted(isHasted);
        for(BaseUnit gameBaseUnit: InitMessage.getInitMessage().getBaseUnitList())
            if(gameBaseUnit.getTypeId() == typeId)
                unit.setBaseUnit(gameBaseUnit);

        unit.setPath(InitMessage.getInitMessage().getPathById(pathId));

        if(targetCell != null)
            unit.setTargetCell(Mapp.getMapp().getCells()[targetCell.getRow()][targetCell.getCol()]);
        unit.setCell(Mapp.getMapp().getCells()[cell.getRow()][cell.getCol()]);
        unit.setUnitId(unitId);
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
