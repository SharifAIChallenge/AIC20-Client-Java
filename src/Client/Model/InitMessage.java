package Client.Model;

import java.util.List;

/**
 * This class has initial information of the game.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class InitMessage {
    private static InitMessage initMessage = new InitMessage();
    private Map map;
    private List<Spell> spells;
    private List<BaseUnit> baseUnitList;

    public static InitMessage getInitMessage() {
        return initMessage;
    }

    private InitMessage() { }

    public Path getPathById(int pathId){
        for(Path path : Map.getMap().getPaths())
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
