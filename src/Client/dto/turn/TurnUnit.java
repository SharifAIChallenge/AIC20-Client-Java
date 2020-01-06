package Client.dto.turn;


import Client.Model.Cell;
import Client.Model.Unit;
import Client.dto.ClientCell;

import java.util.List;

public class TurnUnit {
    private int unitId;
    private int playerId;
    private int typeId;
    private int pathId;     // valid for player and his friend
    private ClientCell cell;
    private int hp;
    private int damageLevel;
    private int rangeLevel;
    private boolean wasDamageUpgraded;
    private boolean wasRangeUpgraded;
    private boolean isDamaged;
    private boolean isHasted;
    private boolean isClone;
    private int range;
    private int attack;
    private boolean wasPlayedThisTurn;
    private List<Integer> affectedSpells;
    private int target;
    //todo target cell i darim ke nemidunam chie

    public Unit castToUnit(){
        return null;
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

    public boolean isHasted() {
        return isHasted;
    }

    public void setHasted(boolean hasted) {
        isHasted = hasted;
    }

    public boolean isClone() {
        return isClone;
    }

    public void setClone(boolean clone) {
        isClone = clone;
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

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
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
}
