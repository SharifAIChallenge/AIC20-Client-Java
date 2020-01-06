package Client.Model;

import Client.dto.init.GameConstants;

import java.util.List;

public class InitMessage {
    private Mapp mapp;
    private List<BaseUnit> baseUnitList;
    private List<Spell> spells;


    public Mapp getMapp() {
        return mapp;
    }

    public void setMapp(Mapp mapp) {
        this.mapp = mapp;
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
