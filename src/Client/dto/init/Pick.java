package Client.dto.init;

import Client.Model.Unit;

import java.util.List;

public class Pick {
    private List<Unit> units;

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }
}
