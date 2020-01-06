package Client.dto.init;

import Client.Model.BaseUnit;
import Client.Model.Mapp;
import Client.Model.Spell;

import java.util.List;

public class InitMessage {
    private GameConstants gameConstants;
    private Mapp mapp;
    private List<BaseUnit> baseUnitList;
    private List<Spell> spells;

    public GameConstants getGameConstants() {
        return gameConstants;
    }

    public void setGameConstants(GameConstants gameConstants) {
        this.gameConstants = gameConstants;
    }

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
