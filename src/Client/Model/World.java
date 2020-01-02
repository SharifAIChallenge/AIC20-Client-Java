package Client.Model;

import java.util.HashMap;
import java.util.List;

public interface World {
    public void chooseDeck(List<Integer> typeIds);

    public int getMyId();

    public int getFriendId();

    public HashMap<Spell, Integer> getSpells();

    public int getActivePoisonsOnUnit(int unitId);

    public int getDamageUpgradeNumber();

    public void putUnit(int typeId, int pathId) ;

        public void castAreaSpell(int row, int col, int spellId) ;

        public void castAreaSpell(int row, int col, Spell spell) ;

        public List<Unit> getAreaSpellTargets(int row, int col, Spell spell);

        public List<Unit> getAreaSpellTargets(int row, int col, int spellId);

        public int getActivePoisonsOnUnit(Unit unit);

    public int getRangeUpgradeNumber();

    public Cell getPlayerPosition(int playerId);

    public List<Path> getPathsFromPlayer(int playerID);

    public Path getPathToFriend(int playerId);

    public int getMapHeight();

    public int getMapWidth();

    public List<Path> getPathsCrossingCell(Cell cell);

    public List<Unit> getPlayerUnits(int playerId);

    public List<Unit> getCellUnits(Cell cell);

    public Path getShortestPathToCell(int fromPlayerId, Cell cell);

    public int getMaxAP();

    public int getRemainingAP();

    public List<Unit> getHand();

    public List<Unit> getDeck();


    public int getCurrentTurn();

    public int getMaxTurns();

    public int getPickTimeout();

    public int getTurnTimeout();

    public int getRemainingTime();

    public int getPlayerHP(int playerId);

    public void castUnitSpell(int unitId, int pathId, int index, int spellId);

    public void castUnitSpell(int unitId, int pathId, int index, Spell spell);

    public List<Unit> getAreaSpellTargets(Cell center, Spell spell);

    public List<Unit> getAreaSpellTargets(Cell center, int SpellId);

    public int getRemainingTurnsToUpgrade();

    public int getRemainingTurnsToGetSpell();

    public CastAreaSpell getCastAreaSpell(int playerId);

    public CastUnitSpell getCastUnitSpell(int playerId);

    public Spell getReceivedSpell();

    public Spell getFriendReceivedSpell();

    public List<Spell> getSpellsList();


}