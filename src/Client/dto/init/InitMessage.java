package Client.dto.init;

import Client.Model.BaseUnit;
import Client.Model.Map;
import Client.Model.Spell;

import java.util.List;

public class InitMessage {
    private Map map;
    private List<BaseUnit> baseUnitList;
    private List<Spell> spells;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public List<BaseUnit> getBaseUnitList() {
        return baseUnitList;
    }

    public void setBaseUnitList(List<BaseUnit> baseUnitList) {
        this.baseUnitList = baseUnitList;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }
}
