package Client.Model;

import Client.dto.init.GameConstants;

import java.util.HashMap;
import java.util.List;

public interface World {
    public Player getMe();

    public Player getFriend();

    public Player getFirstEnemy();

    public Player getSecondEnemy();


    public void chooseDeckById(List<Integer> typeIds);

    public int getMyId();

    public int getFriendId();

    public int getFirstEnemyId();

    public int getSecondEnemyId();

    public List<BaseUnit> getAllBaseUnits();

    public List<Spell> getAllSpells();

    public King getKingById(int playerId);

    public Spell getSpellById(int spellId);

    public BaseUnit getBaseUnitById(int typeId);

    public List<Path> getPathsCrossingCell(Cell cell);

    public List<Path> getPathsCrossingCell(int row, int col);

    public List<Unit> getCellUnits(Cell cell);

    public List<Unit> getCellUnits(int row, int col);

    public Path getShortestPathToCell(int fromPlayerId, Cell cell);

    public Path getShortestPathToCell(int fromPlayerId, int row, int col);

    public GameConstants getGameConstants();

    public void putUnit(int typeId, int pathId);
    // todo, we have also 3 types else

    public int getCurrentTurn();

    public int getRemainingTime();

    public void castUnitSpell(int unitId, int pathId, int index, int spellId);

    public void castUnitSpell(int unitId, int pathId, int index, Spell spell);

    public void castAreaSpell(int row, int col, int spellId);

    public void castAreaSpell(int row, int col, Spell spell);

    public void castAreaSpell(Cell center, Spell spell);

    public void castAreaSpell(Cell center, int spellId);
    // todo we have also 2 types else

    public List<Unit> getAreaSpellTargets(int row, int col, Spell spell);

    public List<Unit> getAreaSpellTargets(int row, int col, int spellId);

    public List<Unit> getAreaSpellTargets(Cell center, Spell spell);

    public List<Unit> getAreaSpellTargets(Cell center, int SpellId);

    public int getRemainingTurnsToUpgrade();

    public int getRemainingTurnsToGetSpell();

    public int getDamageUpgradeNumber();

    public int getRangeUpgradeNumber();

    public List<Spell> getSpellsList();

    public HashMap<Spell, Integer> getSpells();

    public Spell getReceivedSpell();

    public Spell getFriendReceivedSpell();

    public void upgradeUnitRange(Unit unit);

    public void upgradeUnitRange(int unitId);

    public void upgradeUnitDamage(Unit unit);

    public void upgradeUnitDamage(int unitId);

    public void chooseDeck(List<BaseUnit> baseUnits);

}