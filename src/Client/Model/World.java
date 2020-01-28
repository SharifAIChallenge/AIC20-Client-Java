package Client.Model;

import Client.dto.init.GameConstants;

import java.util.HashMap;
import java.util.List;

public interface World {
    public void chooseDeck(List<Integer> typeIds);

    public int getMyId();

    public int getFriendId();

    public int getFirstEnemyId();

    public int getSecondEnemyId();

    public Cell getPlayerPosition(int playerId);

    public List<Path> getPathsFromPlayer(int playerID);

    public Path getPathToFriend(int playerId);

    public int getMapRowNum();

    public int getMapColNum();

    public List<Path> getPathsCrossingCell(Cell cell);

    public List<Unit> getPlayerUnits(int playerId);

    public List<Unit> getCellUnits(Cell cell);

    public List<Unit> getCellUnits(int row, int col);

    public Path getShortestPathToCell(int fromPlayerId, Cell cell);

    public int getMaxAP();

    public int getRemainingAP();

    public List<BaseUnit> getHand();

    public GameConstants getGameConstants();

    public List<BaseUnit> getDeck();

    public void putUnit(int typeId, int pathId);
    // todo, we have also 3 types else

    public int getCurrentTurn();

    public int getMaxTurns();

    public int getPickTimeout();

    public int getTurnTimeout();

    public int getRemainingTime();

    public int getPlayerHP(int playerId);

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

    public CastAreaSpell getCastAreaSpell(int playerId);

    public CastUnitSpell getCastUnitSpell(int playerId);

    public List<CastSpell> getCastSpellOnUnit(Unit unit);

    public List<CastSpell> getCastSpellOnUnit(int unitId);

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

    public List<Unit> getPlayerDuplicateUnits(int playerId);

    public List<Unit> getPlayerHastedUnits(int playerId);

    public List<Unit> getPlayerPlayedUnits(int playerId);

    public Unit getUnitTarget(Unit unit);

    Unit getUnitTarget(int unitId);

    public Cell getUnitTargetCell(Unit unit);

    public Cell getUnitTargetCell(int unitId);

    public Unit getKingTarget(int playerId);

    public Cell getKingTargetCell(int playerId);

    public int getKingUnitIsAttackingTo(Unit unit);

    public int getKingUnitIsAttackingTo(int unitId);


}