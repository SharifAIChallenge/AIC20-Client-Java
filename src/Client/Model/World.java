package Client.Model;

import Client.dto.init.GameConstants;

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
     * choose your hand by base units in the pick turn
     * @param baseUnits
     */

    public void chooseHand(List<BaseUnit> baseUnits);

    /**
     * choose your hand by ids of Base Units in the pick turn
     * @param typeIds ids of BaseUnits that put into your hand
     */

    public void chooseHandById(List<Integer> typeIds);

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
     * returns null if cell doesn't exist
     * @param cell given cell
     * @return
     */

    public List<Path> getPathsCrossingCell(Cell cell);

    /**
     * @param row row of cell
     * @param col column of cell
     * @return
     */

    public List<Path> getPathsCrossingCell(int row, int col);

    /**
     * returns units that placed in given cell
     * returns null array list if cell doesn't exist
     * @param cell given cell
     * @return
     */

    public List<Unit> getCellUnits(Cell cell);

    /**
     * returns
     * @param row row of cell
     * @param col column of cell
     * @return
     */

    public List<Unit> getCellUnits(int row, int col);

    /**
     * returns shortest path to the given cell from the given player's king
     * returns null if cell or player doesn't exist
     * @param fromPlayerId id of player
     * @param cell given cell
     * @return
     */

    public Path getShortestPathToCell(int fromPlayerId, Cell cell);

    /**
     * @param fromPlayerId player id
     * @param row row of given cell
     * @param col column of given cell
     * @return
     */
    public Path getShortestPathToCell(int fromPlayerId, int row, int col);

    public Path getShortestPathToCell(Player fromPlayer, Cell cell);

    public Path getShortestPathToCell(Player fromPlayer, int row, int col);
    /**
     * returns game constants
     * @return
     */
    public GameConstants getGameConstants();

    /**
     * put unit with type id
     * @param typeId id of unit that want to put
     * @param typeId id of unit that want to put
     * @param pathId id of path that put unit on that
     */

    public void putUnit(int typeId, int pathId);
    public void putUnit(BaseUnit baseUnit, int pathId);
    public void putUnit(int typeId, Path path);
    public void putUnit(BaseUnit baseUnit, Path path);

    /**
     * returns current turn number
     * @return
     */
    public int getCurrentTurn();

    /**
     * returns remaining time to end of current turn
     * @return
     */
    public int getRemainingTime();

    /**
     * cast unit spell
     * @param unit the unit that want to cast unit spell on it
     * @param path the path that cast unit spell on it
     * @param cell cell that cast unit spell on it
     * @param spell unitSpell that want to cast
     */
    public void castUnitSpell(Unit unit, Path path, Cell cell, Spell spell);

    public void castUnitSpell(Unit unit, Path path, int row, int col, Spell spell);


    public void castUnitSpell(Unit unit, int pathId, Cell cell, int spellId);

    public void castUnitSpell(Unit unit, int pathId, Cell cell, Spell spell);

    public void castUnitSpell(Unit unit, int pathId, int row, int col, int spellId);

    public void castUnitSpell(Unit unit, int pathId, int row, int col, Spell spell);
    /**
     * cast area spell
     * @param row row of cell that cast spell on it
     * @param col column of cell that cast spell on it
     * @param spellId id of spell that cast
     */
    public void castAreaSpell(int row, int col, int spellId);

    public void castAreaSpell(int row, int col, Spell spell);

    public void castAreaSpell(Cell center, Spell spell);

    public void castAreaSpell(Cell center, int spellId);

    /**
     * returns map of the game
     * @return
     */

    public Map getMap();

    /**
     * returns targets of given area spell if cast spell in given cell
     * @param row row of cell
     * @param col column of cell
     * @param spell given area spell
     * @return
     */
    public List<Unit> getAreaSpellTargets(int row, int col, Spell spell);

    public List<Unit> getAreaSpellTargets(int row, int col, int spellId);

    public List<Unit> getAreaSpellTargets(Cell center, Spell spell);

    public List<Unit> getAreaSpellTargets(Cell center, int SpellId);

    /**
     * returns number of remaining turns to get token for upgrade
     * @return
     */
    public int getRemainingTurnsToUpgrade();

    /**
     * returns number of remaining turns to get spell
     * @return
     */
    public int getRemainingTurnsToGetSpell();

    /**
     * returns number of damage upgrade tokens
     * @return
     */
    public int getDamageUpgradeNumber();

    /**
     * returns number of range upgrade tokens
     * @return
     */
    public int getRangeUpgradeNumber();

    /**
     * returns unit by given id
     * returns null if unitId doesn't exist
     * @param unitId id of unit
     * @return
     */

    public Unit getUnitById(int unitId);

    /**
     * returns player by given id
     * @param playerId id of player
     * @return
     */

    public Player getPlayerById(int playerId);

    /**
     * returns the spell you received at the current turn
     * @return
     */

    public Spell getReceivedSpell();

    /**
     * returns the spell your friend received at the current turn
     * @return
     */

    public Spell getFriendReceivedSpell();

    /**
     * upgrade range of given unit
     * @param unit unit that you want upgrade its range
     */

    public void upgradeUnitRange(Unit unit);

    public void upgradeUnitRange(int unitId);

    /**
     * upgrade damage of given unit
     * @param unit unit that you want upgrade its damage
     */

    public void upgradeUnitDamage(Unit unit);

    public void upgradeUnitDamage(int unitId);

}