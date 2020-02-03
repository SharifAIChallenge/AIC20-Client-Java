package Client.Model;

import java.util.List;

public class CastSpell {
    private Spell spell;
    private int id;
    private int casterId;
    private Cell cell;
    private List<Unit> affectedUnits;

    public List<Unit> getAffectedUnits() {
        return affectedUnits;
    }

    public void setAffectedUnits(List<Unit> affectedUnits) {
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

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
