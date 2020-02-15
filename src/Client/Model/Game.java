package Client.Model;

import Client.dto.ClientCell;
import Client.dto.init.*;
import Client.dto.turn.*;

import java.util.*;
import java.util.function.Consumer;

import com.google.gson.JsonObject;
import common.network.Json;
import common.network.data.Message;

public class Game implements World {
    private long turnTime;
    private ClientInitMessage clientInitMessage;
    private ClientTurnMessage clientTurnMessage;
    private InitMessage initMessage;
    private TurnMessage turnMessage = new TurnMessage();
    private Consumer<Message> sender;
    private HashMap<Cell, List<Path>> pathsCrossingCells = new HashMap<>();
    private List<Player> players;
    private HashMap<Integer, Unit> unitsById = new HashMap<>();
    private HashMap<Integer, Spell> spellsByTypeId = new HashMap<>();
    private HashMap<Integer, BaseUnit> baseUnitsById = new HashMap<>();
    private HashMap<Integer, Path> pathsById = new HashMap<>();

    public Game(Consumer<Message> sender) {
        this.sender = sender;
    }

    public Game(Game game) {
        this.clientInitMessage = game.getClientInitMessage();
        this.initMessage = game.getInitMessage();
        this.sender = game.getSender();
        this.spellsByTypeId = game.spellsByTypeId;
        players = game.getPlayers();
    }

    public HashMap<Integer, BaseUnit> getBaseUnitsById() {
        return baseUnitsById;
    }

    public void setBaseUnitsById(HashMap<Integer, BaseUnit> baseUnitsById) {
        this.baseUnitsById = baseUnitsById;
    }

    public HashMap<Integer, Path> getPathsById() {
        return pathsById;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    private Consumer<Message> getSender() {
        return sender;
    }

    public InitMessage getInitMessage() {
        return initMessage;
    }

    public ClientTurnMessage getClientTurnMessage() {
        return clientTurnMessage;
    }

    @Override
    public void chooseDeckById(List<Integer> typeIds) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("units", Json.GSON.toJsonTree(typeIds));
        Message message = new Message("pick", jsonObject, this.getCurrentTurn());
        sender.accept(message);
    }

    @Override
    public void chooseDeck(List<BaseUnit> baseUnits) {
        List<Integer> typeIds = new ArrayList<>();
        for (BaseUnit baseUnit : baseUnits)
            typeIds.add(baseUnit.getTypeId());
        chooseDeckById(typeIds);
    }

    @Override
    public Player getMe() {
        return players.get(0);
    }

    @Override
    public Player getFriend() {
        return players.get(1);
    }

    @Override
    public Player getFirstEnemy() {
        return players.get(2);
    }

    @Override
    public Player getSecondEnemy() {
        return players.get(3);
    }

    @Override
    public Map getMap() {
        return Map.getMap();
    }

    @Override
    public List<Path> getPathsCrossingCell(Cell cell) {
        if (cell == null) return new ArrayList<>();
        if (pathsCrossingCells.get(cell) == null) {
            List<Path> paths = new ArrayList<>();
            for (Path path : initMessage.getMap().getPaths())
                if (path.getCells().contains(cell))
                    paths.add(path);
            pathsCrossingCells.put(cell, paths);
        }
        return new ArrayList<>(pathsCrossingCells.get(cell));
    }

    @Override
    public List<Path> getPathsCrossingCell(int row, int col) {
        Cell cell = getCellByCoordination(row, col);
        if (cell == null) return new ArrayList<>();
        return getPathsCrossingCell(cell);
    }

    @Override
    public List<Unit> getCellUnits(Cell cell) {
        if (cell == null) return new ArrayList<>();
        return new ArrayList<>(cell.getUnits());
    }

    @Override
    public List<Unit> getCellUnits(int row, int col) {
        Cell cell = getCellByCoordination(row, col);
        if (cell == null) return new ArrayList<>();
        return getCellUnits(cell);
    }

    @Override
    public Path getShortestPathToCell(int fromPlayerId, Cell cell) {
        Player player = getPlayerById(fromPlayerId);
        if (player == null || cell == null) return null;
        return player.getShortestPathsToCells()[cell.getRow()][cell.getCol()];
    }

    @Override
    public Path getShortestPathToCell(int fromPlayerId, int row, int col) {
        Cell cell = getCellByCoordination(row, col);
        if (cell == null) return null;
        return getShortestPathToCell(fromPlayerId, cell);
    }

    @Override
    public Path getShortestPathToCell(Player fromPlayer, Cell cell) {
        if (fromPlayer == null || cell == null) return null;
        return getShortestPathToCell(fromPlayer.getPlayerId(), cell);
    }

    @Override
    public Path getShortestPathToCell(Player fromPlayer, int row, int col) {
        Cell cell = getCellByCoordination(row, col);
        if (fromPlayer == null || cell == null) return null;
        return getShortestPathToCell(fromPlayer.getPlayerId(), cell);
    }

    @Override
    public void putUnit(int typeId, int pathId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("typeId", Json.GSON.toJsonTree(typeId));
        jsonObject.add("pathId", Json.GSON.toJsonTree(pathId));
        Message message = new Message("putUnit", jsonObject, this.getCurrentTurn());
        sender.accept(message);
    }

    @Override
    public void putUnit(BaseUnit baseUnit, int pathId) {
        if (baseUnit == null) return;
        putUnit(baseUnit.getTypeId(), pathId);
    }

    @Override
    public void putUnit(int typeId, Path path) {
        if (path == null) return;
        putUnit(typeId, path.getId());
    }

    @Override
    public void putUnit(BaseUnit baseUnit, Path path) {
        if (baseUnit == null || path == null) return;
        putUnit(baseUnit.getTypeId(), path.getId());
    }

    @Override
    public int getCurrentTurn() {
        if (clientTurnMessage == null)
            return 0;
        return clientTurnMessage.getCurrTurn();
    }

    @Override
    public int getRemainingTime() {
        long result = (this.turnTime + clientInitMessage.getGameConstants().getTurnTimeout() -
                System.currentTimeMillis());
        return (int) result;
    }

    @Override
    public void castUnitSpell(Unit unit, Path path, Cell cell, Spell spell) {
        if (unit == null || path == null || cell == null || spell == null) return;
        int unitId = unit.getUnitId(), pathId = path.getId(), spellId = spell.getTypeId();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("typeId", Json.GSON.toJsonTree(spellId));
        jsonObject.add("unitId", Json.GSON.toJsonTree(unitId));
        jsonObject.add("pathId", Json.GSON.toJsonTree(pathId));
        jsonObject.add("cell", Json.GSON.toJsonTree(cell.castToClientCell()));
        Message message = new Message("castSpell", jsonObject, this.getCurrentTurn());
        sender.accept(message);
    }


    @Override
    public void castUnitSpell(Unit unit, Path path, int row, int col, Spell spell) {
        Cell cell = getCellByCoordination(row, col);
        castUnitSpell(unit, path, cell, spell);
    }

    @Override
    public void castUnitSpell(Unit unit, int pathId, Cell cell, int spellId) {
        Spell spell = initMessage.getSpellById(spellId);
        Path path = initMessage.getPathById(pathId);
        castUnitSpell(unit, path, cell, spell);
    }

    @Override
    public void castUnitSpell(Unit unit, int pathId, Cell cell, Spell spell) {
        Path path = initMessage.getPathById(pathId);
        castUnitSpell(unit, path, cell, spell);
    }

    @Override
    public void castUnitSpell(Unit unit, int pathId, int row, int col, int spellId) {
        Path path = initMessage.getPathById(pathId);
        Spell spell = initMessage.getSpellById(spellId);
        castUnitSpell(unit, path, row, col, spell);
    }

    @Override
    public void castUnitSpell(Unit unit, int pathId, int row, int col, Spell spell) {
        Path path = initMessage.getPathById(pathId);
        castUnitSpell(unit, path, row, col, spell);
    }


    @Override
    public void castAreaSpell(Cell center, int spellId) {
        if (center == null) return;
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("typeId", Json.GSON.toJsonTree(spellId));
        if (center == null) return;
        jsonObject.add("cell", Json.GSON.toJsonTree(center.castToClientCell()));
        Message message = new Message("castSpell", jsonObject, this.getCurrentTurn());
        sender.accept(message);
    }

    @Override
    public void castAreaSpell(int row, int col, int spellId) {
        Cell cell = getCellByCoordination(row, col);
        castAreaSpell(cell, spellId);
    }

    @Override
    public void castAreaSpell(int row, int col, Spell spell) {
        Cell cell = getCellByCoordination(row, col);
        if (spell == null || cell == null) return;
        int spellId = spell.getTypeId();
        castAreaSpell(cell, spellId);
    }

    @Override
    public void castAreaSpell(Cell center, Spell spell) {
        if (center == null || spell == null) return;
        int spellId = spell.getTypeId();
        castAreaSpell(center, spellId);
    }

    @Override
    public List<Unit> getAreaSpellTargets(Cell center, Spell spell) {
        if (center == null || spell == null) return new ArrayList<>();
        List<Unit> units = new ArrayList<>();
        int minRow = Math.max(0, center.getRow() - spell.getRange());
        int maxRow = Math.min(initMessage.getMap().getRowNum() - 1, center.getRow() + spell.getRange());
        int minCol = Math.max(0, center.getCol() - spell.getRange());
        int maxCol = Math.min(initMessage.getMap().getColNum() - 1, center.getCol() + spell.getRange());
        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                units.addAll(initMessage.getMap().getCells()[i][j].getUnits());
            }
        }
        return units;
    }

    @Override
    public List<Unit> getAreaSpellTargets(Cell center, int spellId) {
        if (center == null) return new ArrayList<>();
        Spell spell = spellsByTypeId.get(spellId);
        if (spell == null) return new ArrayList<>();
        return new ArrayList<>(getAreaSpellTargets(center, spell));
    }

    @Override
    public List<Unit> getAreaSpellTargets(int row, int col, Spell spell) {
        if (spell == null) return new ArrayList<>();
        Cell cell = getCellByCoordination(row, col);
        if (cell == null) return new ArrayList<>();
        return new ArrayList<>(getAreaSpellTargets(cell, spell));
    }

    @Override
    public List<Unit> getAreaSpellTargets(int row, int col, int spellId) {
        Cell cell = getCellByCoordination(row, col);
        if (cell == null) return new ArrayList<>();
        Spell spell = spellsByTypeId.get(spellId);
        return new ArrayList<>(getAreaSpellTargets(cell, spell));
    }

    @Override
    public int getRemainingTurnsToUpgrade() {
        return clientInitMessage.getGameConstants().getTurnsToUpgrade() -
                clientTurnMessage.getCurrTurn() % clientInitMessage.getGameConstants().getTurnsToUpgrade();
    }

    @Override
    public int getRemainingTurnsToGetSpell() {
        return clientInitMessage.getGameConstants().getTurnsToSpell() -
                clientTurnMessage.getCurrTurn() % clientInitMessage.getGameConstants().getTurnsToSpell();
    }

    @Override
    public int getRangeUpgradeNumber() {
        return clientTurnMessage.getAvailableRangeUpgrades();
    }

    @Override
    public int getDamageUpgradeNumber() {
        return clientTurnMessage.getAvailableDamageUpgrades();
    }

    @Override
    public Spell getReceivedSpell() {
        return spellsByTypeId.get(clientTurnMessage.getReceivedSpell());
    }

    @Override
    public Spell getFriendReceivedSpell() {
        return spellsByTypeId.get(clientTurnMessage.getFriendReceivedSpell());
    }

    @Override
    public void upgradeUnitRange(Unit unit) {
        if (unit == null) return;
        upgradeUnitRange(unit.getUnitId());
    }

    @Override
    public void upgradeUnitRange(int unitId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("unitId", Json.GSON.toJsonTree(unitId));
        Message message = new Message("rangeUpgrade", jsonObject, this.getCurrentTurn());
        sender.accept(message);
    }

    @Override
    public void upgradeUnitDamage(Unit unit) {
        if (unit == null) return;
        upgradeUnitDamage(unit.getUnitId());
    }

    @Override
    public void upgradeUnitDamage(int unitId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("unitId", Json.GSON.toJsonTree(unitId));
        Message message = new Message("damageUpgrade", jsonObject, this.getCurrentTurn());
        sender.accept(message);
    }

    @Override
    public List<BaseUnit> getAllBaseUnits() {
        return new ArrayList<>(initMessage.getBaseUnitList());
    }

    @Override
    public List<Spell> getAllSpells() {
        return new ArrayList<>(initMessage.getSpells());
    }

    @Override
    public King getKingById(int playerId) {
        for (Player player : players)
            if (player.getPlayerId() == playerId)
                return player.getKing();
        return null;
    }

    @Override
    public Spell getSpellById(int spellId) {
        return spellsByTypeId.get(spellId);
    }

    @Override
    public BaseUnit getBaseUnitById(int typeId) {
        return baseUnitsById.get(typeId);
    }

    @Override
    public Player getPlayerById(int playerId) {
        for (Player player : players)
            if (player.getPlayerId() == playerId)
                return player;
        return null;
    }

    @Override
    public Unit getUnitById(int unitId) {
        return unitsById.get(unitId);
    }

    @Override
    public GameConstants getGameConstants() {
        if (clientInitMessage == null) return null;
        return clientInitMessage.getGameConstants();
    }

    private int getMyId() {
        return clientInitMessage.getMap().getKings().get(0).getPlayerId();
    }

    private int getFriendId() {
        return clientInitMessage.getMap().getKings().get(1).getPlayerId();
    }

    private int getFirstEnemyId() {
        return clientInitMessage.getMap().getKings().get(2).getPlayerId();
    }

    private int getSecondEnemyId() {
        return clientInitMessage.getMap().getKings().get(3).getPlayerId();
    }


    private King getPlayerKing(int playerId) {
        for (King king : initMessage.getMap().getKings())
            if (king.getPlayerId() == playerId) {
                return king;
            }
        return null;
    }

    private int getFriendIdOfPlayer(int playerId) {
        if (playerId == getFriendId()) return getMyId();
        if (playerId == getMyId()) return getFriendId();
        if (playerId == getFirstEnemyId()) return getSecondEnemyId();
        if (playerId == getSecondEnemyId()) return getFirstEnemyId();
        return -1;
    }


    private List<Unit> calcPlayerUnits(int playerId) {
        List<Unit> units = new ArrayList<>();
        if (turnMessage.getUnits() == null) return units;
        for (Unit unit : turnMessage.getUnits())
            if (unit.getPlayerId() == playerId)
                units.add(unit);
        return units;
    }

    private Path getShortestPathToCellFromPlayer(int fromPlayerId, Cell cell) {
        Player player = getPlayerById(fromPlayerId);
        if (player == null || cell == null) return null;
        return player.getShortestPathsToCellsCrossMyself()[cell.getRow()][cell.getCol()];
    }

    private Path calcShortestPathToCell(int fromPlayerId, Cell cell) {
        Player player = getPlayerById(fromPlayerId);
        if (player == null || cell == null) return null;
        Path path1 = getShortestPathToCellFromPlayer(fromPlayerId, cell);
        Path path2 = getShortestPathToCellFromPlayer(getFriendIdOfPlayer(fromPlayerId), cell);
        Path path3 = player.getPathToFriend();
        int length1 = 100 * 1000, length2 = 100 * 1000;
        if (path1 != null) length1 = path1.getCells().indexOf(cell);
        if (path2 != null) length2 = path3.getCells().size() + path2.getCells().indexOf(cell);
        if (length1 <= length2 && length1 < 100 * 1000) return path1;
        if (length1 > length2) return path2;
        return null;
    }

    private Cell getCellByCoordination(int row, int col) {
        if (row >= initMessage.getMap().getRowNum() || row < 0) return null;
        if (col >= initMessage.getMap().getColNum() || col < 0) return null;
        return initMessage.getMap().getCells()[row][col];
    }

    private List<Spell> getSpellsByIds(List<Integer> list) {
        List<Spell> spells = new ArrayList<>();
        for (int spellId : list) {
            Spell spell = spellsByTypeId.get(spellId);
            if (spell != null) spells.add(spell);
        }
        return spells;
    }

    public ClientInitMessage getClientInitMessage() {
        return clientInitMessage;
    }

    private void setPLayersUnits() {
        for (Player player : players) {
            player.setUnits(calcPlayerUnits(player.getPlayerId()));
        }
    }

    private void calcUnitsById() {
        unitsById = new HashMap<>();
        for (Unit unit : turnMessage.getUnits()) {
            if (!unitsById.containsKey(unit.getUnitId()))
                unitsById.put(unit.getUnitId(), unit);
        }
    }

    private void calcMyTurnSpells() {
        players.get(0).setSpells(getSpellsByIds(clientTurnMessage.getMySpells()));
        players.get(0).calcTurnSpells();
    }

    private void calcMyFriendTurnSpells() {
        players.get(1).setSpells(getSpellsByIds(clientTurnMessage.getFriendSpells()));
        players.get(1).calcTurnSpells();
    }

    private void calcPlayerplayedUnits(Player player) {
        List<Unit> playedUnits = new ArrayList<>();
        for (TurnUnit turnUnit : clientTurnMessage.getUnits())
            if (turnUnit.getPlayerId() == player.getPlayerId() && turnUnit.isWasPlayedThisTurn())
                playedUnits.add(unitsById.get(turnUnit.getUnitId()));
        player.setPlayedUnits(playedUnits);
    }

    private void calcPlayersPlayedUnits() {
        for (Player player : players)
            calcPlayerplayedUnits(player);
    }

    private void calcCastAreaSpells() {
        for (Player player : players) {
            for (CastSpell castSpell : turnMessage.getCastSpells()) {
                if (castSpell instanceof CastAreaSpell && castSpell.getCasterId() == player.getPlayerId()
                        && castSpell.isWasCastThisTurn())
                    player.setCastAreaSpell((CastAreaSpell) castSpell);
            }
        }
    }

    private void calcCastUnitSpells() {
        for (Player player : players) {
            for (CastSpell castSpell : turnMessage.getCastSpells()) {
                if (castSpell instanceof CastUnitSpell && castSpell.getCasterId() == player.getPlayerId()
                        && castSpell.isWasCastThisTurn())
                    player.setCastUnitSpell((CastUnitSpell) castSpell);
            }
        }
    }

    private void calcPlayersDuplicateUnits() {
        for (Player player : players) {
            List<Unit> duplicateUnits = new ArrayList<>();
            for (Unit unit : player.getUnits())
                if (unit.isDuplicate())
                    duplicateUnits.add(unit);
            player.setDuplicateUnits(duplicateUnits);
        }
    }

    private void calcPlayersHastedUnits() {
        for (Player player : players) {
            List<Unit> hastedUnits = new ArrayList<>();
            for (Unit unit : player.getUnits())
                if (unit.isHasted())
                    hastedUnits.add(unit);
            player.setHastedUnits(hastedUnits);
        }
    }

    private void calcAffectedSpells() {
        for (TurnUnit turnUnit : clientTurnMessage.getUnits()) {
            List<CastSpell> affectedSpells = new ArrayList<>();
            for (int affectedSpellId : turnUnit.getAffectedSpells()) {

                for (CastSpell castSpell : turnMessage.getCastSpells()) {
                    if (castSpell.getId() == affectedSpellId)
                        affectedSpells.add(castSpell);
                }
            }
            unitsById.get(turnUnit.getUnitId()).setAffectedSpells(affectedSpells);
        }
    }

    private void calcKingsTargets() {
        for (Player player : players) {
            for (TurnKing turnKing : clientTurnMessage.getKings())
                if (turnKing.getPlayerId() == player.getPlayerId()) {
                    Unit targetUnit = unitsById.get(turnKing.getTarget());
                    player.getKing().setTarget(targetUnit);
                    Cell targetCell = null;
                    if (targetUnit != null) targetCell = targetUnit.getCell();
                    player.getKing().setTargetCell(targetCell);
                }
        }
    }

    private void calcUnitsTargets() {
        for (TurnUnit turnUnit : clientTurnMessage.getUnits()) {
            Unit unit = unitsById.get(turnUnit.getUnitId());
            boolean find = false;
            for (King king : turnMessage.getKings()) {
                if (king.getPlayerId() == turnUnit.getTarget()) {
                    unit.setTargetIfKing(king);
                    find = true;
                }
            }

            if (!find && turnUnit.getTarget() != -1)
                unit.setTarget(unitsById.get(turnUnit.getTarget()));
            else {
                unit.setTarget(null);
                unit.setTargetCell(null);
                continue;
            }

            ClientCell clientCell = turnUnit.getTargetCell();
            Cell targetCell = getCellByCoordination(clientCell.getRow(), clientCell.getCol());
            unit.setTargetCell(targetCell);
        }
    }

    private void calcDiedUnits() {
        for (Player player : players) {
            List<Unit> diedUnits = new ArrayList<>();
            for (TurnUnit diedUnit : clientTurnMessage.getDiedUnits()) {
                if (diedUnit.getPlayerId() == player.getPlayerId()) diedUnits.add(diedUnit.castToUnit());
            }
            player.setDiedUnits(diedUnits);
        }
    }

    private void updateCastSpells() {
        for (CastSpell castSpell : turnMessage.getCastSpells()) {
            for (TurnCastSpell turnCastSpell : clientTurnMessage.getCastSpells()) {
                if (turnCastSpell.getId() == castSpell.getId()) {
                    if (castSpell instanceof CastUnitSpell) {
                        ((CastUnitSpell) castSpell).setPath(pathsById.get(turnCastSpell.getPathId()));
                        ((CastUnitSpell) castSpell).setUnit(unitsById.get(turnCastSpell.getUnitId()));
                    } else {
                        ((CastAreaSpell) castSpell).setRemainingTurns(turnCastSpell.getRemainingTurns());
                    }
                    List<Unit> affectedUnits = new ArrayList<>();
                    for (int affectedUnitId : turnCastSpell.getAffectedUnits())
                        affectedUnits.add(unitsById.get(affectedUnitId));
                    castSpell.setAffectedUnits(affectedUnits);
                    castSpell.setSpell(spellsByTypeId.get(turnCastSpell.getTypeId()));
                }
            }
        }
    }

    private void calcDamageUpgradedUnits() {
        for (Player player : players) {
            for (Unit unit : player.getUnits()) {
                for (TurnUnit turnUnit : clientTurnMessage.getUnits()) {
                    if (turnUnit.getUnitId() == unit.getUnitId() && turnUnit.isWasDamageUpgraded())
                        player.setDamageUpgradedUnit(unit);
                }
            }
        }
    }

    private void calcRangeUpgradedUnits() {
        for (Player player : players) {
            for (Unit unit : player.getUnits()) {
                for (TurnUnit turnUnit : clientTurnMessage.getUnits()) {
                    if (turnUnit.getUnitId() == unit.getUnitId() && turnUnit.isWasRangeUpgraded())
                        player.setRangeUpgradedUnit(unit);
                }
            }
        }
    }

    private void calcBaseUnits() {
        for (Unit unit : turnMessage.getUnits()) {
            for (TurnUnit turnUnit : clientTurnMessage.getUnits()) {
                if (turnUnit.getUnitId() == unit.getUnitId())
                    unit.setBaseUnit(baseUnitsById.get(turnUnit.getTypeId()));
            }
        }
    }

    private void updateDeck() {
        players.get(0).setDeck(turnMessage.getDeck());
        players.get(0).setHand(turnMessage.getHand());
    }

    private void updateAp() {
        players.get(0).setAp(clientTurnMessage.getRemainingAP());
    }

    private void setCellsUnits() {
        for (int i = 0; i < getMap().getRowNum(); i++)
            for (int j = 0; j < getMap().getColNum(); j++)
                getMap().getCells()[i][j].setUnits(new ArrayList<>());
        for (Unit unit : turnMessage.getUnits())
            unit.getCell().getUnits().add(unit);
    }

    private void createPLayers() {
        players = new ArrayList<>();
        Player myPlayer = new Player(getMyId());
        Player friendPlayer = new Player(getFriendId());
        Player firstEnemyPlayer = new Player(getFirstEnemyId());
        Player secondEnemyPlayer = new Player(getSecondEnemyId());
        players.add(myPlayer);
        players.add(friendPlayer);
        players.add(firstEnemyPlayer);
        players.add(secondEnemyPlayer);
    }

    private void updateKings() {
        for (Player player : players) {
            player.setKing(getPlayerKing(player.getPlayerId()));
        }

    }

    private Path calcShortestPathToCellFromPlayer(int playerId, Cell cell) {
        int minDis = 100 * 1000;
        Player player = getPlayerById(playerId);
        if (player == null) return null;
        Cell playerCell = player.getKing().getCenter();
        if (cell == null) return null;
        Path bestPath = null;
        for (Path path : player.getPathsFromPlayer()) {
            if (path.getCells().indexOf(playerCell) != 0) Collections.reverse(path.getCells());
            int index = path.getCells().indexOf(cell);
            if (index < minDis) {
                minDis = index;
                bestPath = path;
            }

        }
        return bestPath;
    }

    private void setShortestPathsOfPlayerCrossMyself(Player player) {
        Path[][] shortestPathsFromPlayer = new Path[initMessage.getMap().getRowNum()][initMessage.getMap().getColNum()];
        for (int i = 0; i < initMessage.getMap().getRowNum(); i++) {
            for (int j = 0; j < initMessage.getMap().getColNum(); j++) {
                Cell cell = initMessage.getMap().getCells()[i][j];
                shortestPathsFromPlayer[i][j] = calcShortestPathToCellFromPlayer(player.getPlayerId(), cell);
            }
        }
        player.setShortestPathsToCellsCrossMyself(shortestPathsFromPlayer);
    }

    private void setShortestPathsOfPlayer(Player player) {
        Path[][] shortestPaths = new Path[initMessage.getMap().getRowNum()][initMessage.getMap().getColNum()];
        for (int i = 0; i < initMessage.getMap().getRowNum(); i++) {
            for (int j = 0; j < initMessage.getMap().getColNum(); j++) {
                Cell cell = initMessage.getMap().getCells()[i][j];
                shortestPaths[i][j] = calcShortestPathToCell(player.getPlayerId(), cell);
            }
        }
        player.setShortestPathsToCells(shortestPaths);
    }

    private void setShortestPathsOfPlayers() {
        for (Player player : players) {
            setShortestPathsOfPlayerCrossMyself(player);
        }
        for (Player player : players) {
            setShortestPathsOfPlayer(player);
        }
    }

    private void setSpellsById() {
        for (Spell spell : initMessage.getSpells()) {
            spellsByTypeId.putIfAbsent(spell.getTypeId(), spell);
        }
    }

    private void calcPathsFromPlayer(Player player) {
        if (player == null) return;
        Cell playerKingCell = getPlayerKing(player.getPlayerId()).getCenter();
        Cell friendKingCell = getPlayerKing(getFriendIdOfPlayer(player.getPlayerId())).getCenter();

        List<Path> paths = new ArrayList<>();
        for (Path path : initMessage.getMap().getPaths()) {
            if (path.getCells().indexOf(playerKingCell) == 0 || path.getCells().indexOf(playerKingCell) == path.getCells().size() - 1) {
                if (!path.getCells().contains(friendKingCell)){
                    Path newPath = path.copy();
                    if(newPath.getCells().indexOf(playerKingCell) != 0)
                        Collections.reverse(newPath.getCells());
                    paths.add(newPath);
                }
            }
        }
        player.setPathsFromPlayer(paths);
    }

    private void calcPathsFromPlayers() {
        for (Player player : players)
            calcPathsFromPlayer(player);
    }

    private void calcPathToFriend(Player player) {
        Cell playerKingCell = getPlayerKing(player.getPlayerId()).getCenter();
        Cell friendKingCell = getPlayerKing(getFriendIdOfPlayer(player.getPlayerId())).getCenter();

        for (Path path : initMessage.getMap().getPaths()) {
            List<Cell> pathCells = path.getCells();
            if (pathCells.indexOf(playerKingCell) == 0 || pathCells.lastIndexOf(playerKingCell) == pathCells.size() - 1)
                if (pathCells.indexOf(friendKingCell) == 0 || pathCells.lastIndexOf(friendKingCell) == pathCells.size() - 1) {
                    Path newPath = path.copy();
                    if(newPath.getCells().indexOf(playerKingCell) != 0)
                        Collections.reverse(newPath.getCells());
                    player.setPathToFriend(newPath);
                }
        }
    }

    private void calcPathsToFriends() {
        for (Player player : players)
            calcPathToFriend(player);
    }

    void calcBaseUnitsById() {
        for (BaseUnit baseUnit : initMessage.getBaseUnitList())
            if (baseUnitsById.get(baseUnit.getTypeId()) == null)
                baseUnitsById.put(baseUnit.getTypeId(), baseUnit);
    }

    private void calcPathsById() {
        for (Path path : initMessage.getMap().getPaths())
            if (pathsById.get(path.getId()) == null)
                pathsById.put(path.getId(), path);
    }

    private void updateMessage(ClientInitMessage msg) {
        this.clientInitMessage = msg;
        setSpellsById();
        calcBaseUnitsById();
        calcPathsById();
        updateKings();
        calcPathsToFriends();
        calcPathsFromPlayers();
    }

    public void handleInitMessage(ClientInitMessage msg) {
        this.clientInitMessage = msg;
        this.initMessage = clientInitMessage.castToInitMessage();
        this.turnTime = System.currentTimeMillis();
        createPLayers();
        updateMessage(msg);
        setShortestPathsOfPlayers();
    }

    public void handleTurnMessage(ClientTurnMessage msg) {
        this.clientTurnMessage = msg;
        this.turnTime = System.currentTimeMillis();
        turnMessage = clientTurnMessage.castToTurnMessage(initMessage, spellsByTypeId);
        updateMessage(clientInitMessage);
        setPLayersUnits();
        calcUnitsById();
        calcBaseUnits();
        updateCastSpells();
        updateDeck();
        updateAp();
        calcMyTurnSpells();
        calcMyFriendTurnSpells();
        calcPlayersPlayedUnits();
        calcCastAreaSpells();
        calcCastUnitSpells();
        calcPlayersDuplicateUnits();
        calcPlayersHastedUnits();
        calcAffectedSpells();
        calcKingsTargets();
        calcUnitsTargets();
        calcDiedUnits();
        calcDamageUpgradedUnits();
        calcRangeUpgradedUnits();
        setCellsUnits();
    }
}
