package Client.Model;

import Client.dto.init.GameConstants;

import java.net.PortUnreachableException;
import java.util.List;

public class InitMessage {
    private static InitMessage initMessage = new InitMessage();
    private Mapp mapp;
    private List<BaseUnit> baseUnitList;
    private List<Spell> spells;

    public static InitMessage getInitMessage() {
        return initMessage;
    }

    private InitMessage() {

    }

    public Path getPathById(int pathId){
        for(Path path : Mapp.getMapp().getPaths())
            if(path.getId() == pathId)
                return path;
        return null;
    }

    public Spell getSpellById(int spellId) {
        for (Spell spell : initMessage.getSpells())
            if (spell.getTypeId() == spellId)
                return spell;

        return null;
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
