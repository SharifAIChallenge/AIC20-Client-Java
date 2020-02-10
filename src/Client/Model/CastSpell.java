package Client.Model;

import java.util.List;

/**
 * This class has properties of the spell that is cast.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class CastSpell {
    private int id;
    private int casterId;
    private Cell cell;
    private Spell spell;
    private List<Unit> affectedUnits;
    private boolean wasCastThisTurn;

    public List<Unit>getAffectedUnits() {
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

    boolean isWasCastThisTurn() {
        return wasCastThisTurn;
    }

    public void setWasCastThisTurn(boolean wasCastThisTurn) {
        this.wasCastThisTurn = wasCastThisTurn;
    }
}
