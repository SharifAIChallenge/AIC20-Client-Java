package Client.Model;

import Client.dto.init.GameConstants;

import java.util.HashMap;
import java.util.List;

public interface World {
    /**
     * returns your player
     * @return
     */
    public Player getMe();

    /**
     * returns your friend's player
     * @return
     */
    public Player getFriend();

    /**
     * returns your first enemy's player
     * @return
     */
    public Player getFirstEnemy();

    /**
     * returns your second enemy's player
     * @return
     */
    public Player getSecondEnemy();

    /**
     * choose your deck by base units in the pick turn
     * @param baseUnits
     */

    public void chooseDeck(List<BaseUnit> baseUnits);

    /**
     * choose your deck by ids of Base Units in the pick turn
     *
     * @param typeIds ids of BaseUnits that put into your deck
     */

    public void chooseDeckById(List<Integer> typeIds);

    /**
     * returns your id
     * @return
     */
    public int getMyId();

    /**
     * returns your friend's id
     * @return
     */
    public int getFriendId();

    /**
     * returns first enemy's id
     * @return
     */
    public int getFirstEnemyId();

    /**
     * returns second enemy's id
     * @return
     */
    public int getSecondEnemyId();

    /**
     * returns all of base units that exists in the game
     * @return
     */
    public List<BaseUnit> getAllBaseUnits();

    /**
     * returns all of spells that exists in the game
     * @return
     */
    public List<Spell> getAllSpells();

    /**
     * returns king of given id
     * returns null if player doesn't exit
     * @param playerId id of player
     * @return
     */
    public King getKingById(int playerId);

    /**
     * returns spell by its id
     * returns null if id doesn't exist
     * @param spellId id of spell
     * @return
     */
    public Spell getSpellById(int spellId);

    /**
     * returns null if id doesn't exist
     * @param typeId id of base unit
     * @return
     */

    public BaseUnit getBaseUnitById(int typeId);

    /**
     * returns paths that cross the given cell
     * @param cell given cell
     * @return
     */

    public List<Path> getPathsCrossingCell(Cell cell);

    /**
     * @param row row of cell
     * @param col col of cell
     * @return
     */

    public List<Path> getPathsCrossingCell(int row, int col);

    /**
     * returns units that placed in given cell
     * @param cell given cell
     * @return
     */

    public List<Unit> getCellUnits(Cell cell);

    /**
     * @param row row of cell
     * @param col col of cell
     * @return
     */

    public List<Unit> getCellUnits(int row, int col);

    /**
     * returns shortest path to the given cell from the given player's king
     * @param fromPlayerId id of player
     * @param cell given cell
     * @return
     */

    public Path getShortestPathToCell(int fromPlayerId, Cell cell);

    /**
     * @param fromPlayerId player id
     * @param row row of given cell
     * @param col col of given cell
     * @return
     */
    public Path getShortestPathToCell(int fromPlayerId, int row, int col);

    /**
     * returns game constants
     * @return
     */
    public GameConstants getGameConstants();

    /**
     * returns
     * @param typeId id of unit that want to put
     * @param pathId id of path that put unit
     */

    public void putUnit(int typeId, int pathId);
    // todo, we have also 3 types else

    public int getCurrentTurn();

    public int getRemainingTime();

    public void castUnitSpell(int unitId, int pathId, Cell cell, int spellId);

    public void castUnitSpell(int unitId, int pathId, Cell cell, Spell spell);

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



}