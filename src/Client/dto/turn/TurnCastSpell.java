package Client.dto.turn;

import Client.Model.*;
import Client.dto.ClientCell;
import java.util.HashMap;
import java.util.List;

/**
 * This class has information of the cast spell. The data is sent by the server each turn.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class TurnCastSpell {
    private int id;
    private int unitId;
    private int pathId;
    private int typeId;
    private int casterId;
    private int remainingTurns;
    private boolean wasCastThisTurn;
    private ClientCell cell;
    private List<Integer> affectedUnits;

    public CastSpell castToCastSpell(HashMap<Integer, Spell> spellsByTypeId){
        if(spellsByTypeId.get(this.getTypeId()).getType() == SpellType.TELE){
            CastUnitSpell castUnitSpell = new CastUnitSpell();
            castUnitSpell.setCasterId(casterId);
            castUnitSpell.setCell(cell.castToCell());
            castUnitSpell.setId(id);
            castUnitSpell.setWasCastThisTurn(wasCastThisTurn);
            return castUnitSpell;
        }
        else{
            CastAreaSpell castAreaSpell = new CastAreaSpell();
            castAreaSpell.setCasterId(casterId);
            castAreaSpell.setCell(cell.castToCell());
            castAreaSpell.setId(id);
            castAreaSpell.setWasCastThisTurn(wasCastThisTurn);
            return castAreaSpell;
        }
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
