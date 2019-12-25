package Client.dto.turn;

import Client.dto.ClientCell;

import java.util.List;

public class TurnCastSpell {
    private int typeId;
    private int casterId;
    private ClientCell cell;
    private int unitId;         // for unit spell
    private int pathId;         // for unit spell and caster == player,friend
    private List<Integer> affectedUnits;

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
}
