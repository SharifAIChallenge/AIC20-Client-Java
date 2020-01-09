package Client.dto.turn;

import Client.Model.CastSpell;
import Client.dto.ClientCell;

import java.util.List;

public class TurnCastSpell {
    private int typeId;
    private int id;
    private int casterId;
    private ClientCell cell;
    private int unitId;         // for unit spell
    private int pathId;         // for unit spell and caster == player,friend
    private int remainingTurns;
    private boolean wasCastThisTurn;
    private List<Integer> affectedUnits;

    public CastSpell castToCastSpell(){
        CastSpell castSpell = new CastSpell();
        castSpell.setCasterId(casterId);
        castSpell.setUnitId(unitId);
        castSpell.setPathId(pathId);
        castSpell.setCell(cell.castToCell());
        castSpell.setAffectedUnits(affectedUnits);
        return castSpell;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getCasterId() {
        return casterId;
    }

    public void setCasterId(int casterId) {
        this.casterId = casterId;
    }

    public ClientCell getCell() {
        return cell;
    }

    public void setCell(ClientCell cell) {
        this.cell = cell;
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

    public List<Integer> getAffectedUnits() {
        return affectedUnits;
    }

    public void setAffectedUnits(List<Integer> affectedUnits) {
        this.affectedUnits = affectedUnits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = remainingTurns;
    }

    public boolean isWasCastThisTurn() {
        return wasCastThisTurn;
    }

    public void setWasCastThisTurn(boolean wasCastThisTurn) {
        this.wasCastThisTurn = wasCastThisTurn;
    }
}
