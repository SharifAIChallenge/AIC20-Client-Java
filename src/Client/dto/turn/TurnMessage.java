package Client.dto.turn;

import Client.Model.CastSpell;
import Client.Model.King;
import Client.Model.Unit;

import java.util.List;

public class TurnMessage {
    private List<King> kings;
    private List<Unit> units;
    private List<CastSpell> castSpells;


    public List<King> getKings() {
        return kings;
    }

    public void setKings(List<King> kings) {
        this.kings = kings;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<CastSpell> getCastSpells() {
        return castSpells;
    }

    public void setCastSpells(List<CastSpell> castSpells) {
        this.castSpells = castSpells;
    }
}
