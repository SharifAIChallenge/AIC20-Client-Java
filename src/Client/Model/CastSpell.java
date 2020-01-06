package Client.Model;

import java.util.List;

public class CastSpell {
    private Spell spell;
    private int casterId, unitId, pathId;
    private Cell cell;
    private List<Integer> affectedUnits;

    public List<Integer> getAffectedUnits() {
        return affectedUnits;
    }

    public void setAffectedUnits(List<Integer> affectedUnits) {
        this.affectedUnits = affectedUnits;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getCasterId() {
        return casterId;
    }

    public void setCasterId(int casterId) {
        this.casterId = casterId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }
}
