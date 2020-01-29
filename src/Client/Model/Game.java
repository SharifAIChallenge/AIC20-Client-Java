package Client.Model;

import Client.dto.init.*;
import Client.dto.turn.*;

import java.util.*;
import java.util.function.Consumer;

import com.google.gson.JsonObject;
import common.network.Json;
import common.network.data.Message;

public class Game implements World {
    private ClientInitMessage clientInitMessage;
    private ClientTurnMessage clientTurnMessage;
    private InitMessage initMessage;
    private TurnMessage turnMessage = new TurnMessage();
    private Consumer<Message> sender;
    private HashMap<Cell, List<Path>> pathsCrossingCells = new HashMap<>();
    private HashMap<Spell, Integer> myTurnSpells = new HashMap<>();

    private List<Player> players = new ArrayList<>();
    private HashMap<Integer, Unit> unitsById = new HashMap<>();
    private HashMap<Integer, Spell> spellsByTypeId = new HashMap<>();

    public Game(Consumer<Message> sender) {
        this.sender = sender;
    }

    public Game(Game game) {
        this.clientInitMessage = game.getClientInitMessage();
        this.initMessage = game.getInitMessage();
        this.sender = game.getSender();
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
    public GameConstants getGameConstants() {
        if (clientInitMessage == null) return null;
        return clientInitMessage.getGameConstants();
    }

    @Override
    public void chooseDeck(List<Integer> typeIds) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("units", Json.GSON.toJsonTree(typeIds));
        Message message = new Message("pick", jsonObject, this.getCurrentTurn());
        sender.accept(message);
    }

    @Override
    public int getMyId() {
        return clientInitMessage.getMap().getKings().get(0).getPlayerId();
    }

    @Override
    public int getFriendId() {
        return clientInitMessage.getMap().getKings().get(1).getPlayerId();
    }

    public int getFirstEnemyId() {
        return clientInitMessage.getMap().getKings().get(2).getPlayerId();
    }

    public int getSecondEnemyId() {
        return clientInitMessage.getMap().getKings().get(3).getPlayerId();
    }

    @Override
    public Cell getPlayerPosition(int playerId) {
        King king = getPlayerKing(playerId);
        if (king == null) return null;
        return king.getCenter();
    }

    private King getPlayerKing(int playerId) {
        for (King king : initMessage.getMapp().getKings())
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

    @Override
    public List<Path> getPathsFromPlayer(int playerId) {
        Player player = getPlayerById(playerId);
        if (player == null) return new ArrayList<>();
        return player.getPathsFromPlayer();
    }

    @Override
    public Path getPathToFriend(int playerId) {
        Player player = getPlayerById(playerId);
        if (player == null) return null;
        return player.getPathToFriend();
    }


    @Override
    public int getMapRowNum() {
        return clientInitMessage.getMap().getRows();
    }

    @Override
    public int getMapColNum() {
        return clientInitMessage.getMap().getCols();
    }

    @Override
    public List<Path> getPathsCrossingCell(Cell cell) {
        if (cell == null) return new ArrayList<>();
        if (pathsCrossingCells.get(cell) == null) {
            List<Path> paths = new ArrayList<>();
            for (Path path : initMessage.getMapp().getPaths())
                if (path.getCells().contains(cell))
                    paths.add(path);
            pathsCrossingCells.put(cell, paths);
        }
        return new ArrayList<>(pathsCrossingCells.get(cell));
    }

    @Override
    public List<Unit> getPlayerUnits(int playerId) {
        Player player = getPlayerById(playerId);
        if (player == null) return new ArrayList<>();
        return new ArrayList<>(player.getPlayerUnits());
    }

    private List<Unit> calcPlayerUnits(int playerId) {
        List<Unit> units = new ArrayList<>();
        if (turnMessage.getUnits() == null) return units;
        for (Unit unit : turnMessage.getUnits())
            if (unit.getPlayerId() == playerId)
                units.add(unit);
        return units;
    }

    //path ha ro tavajoh nakardim hatman vase khode player bashe
    @Override
    public List<Unit> getCellUnits(Cell cell) {
        if (cell == null) return new ArrayList<>();
        return new ArrayList<>(cell.getUnitList());
    }

    @Override
    public List<Unit> getCellUnits(int row, int col) {
        Cell cell = getCellByCoordination(row, col);
        if (cell == null) return new ArrayList<>();
        return new ArrayList<>(getCellUnits(cell));
    }

    private Path getShortestPathToCellFromPlayer(int fromPlayerId, Cell cell) {
        Player player = getPlayerById(fromPlayerId);
        if(player == null || cell == null)return null;
        return player.getShortestPathsToCells()[cell.getRow()][cell.getCol()];
    }

    private Path calcShortestPathToCell(int fromPlayerId, Cell cell) {
        Player player = getPlayerById(fromPlayerId);
        if (player == null || cell == null) return null;
        Path path1 = getShortestPathToCellFromPlayer(fromPlayerId, cell);
        Path path2 = getShortestPathToCellFromPlayer(getFriendIdOfPlayer(fromPlayerId), cell);
        Path path3 = getPathToFriend(fromPlayerId);
        int length1 = 100 * 1000, length2 = 100 * 1000;
        if(path1 != null)length1 = path1.getCells().indexOf(cell);
        if(path2 != null)length2 = path3.getCells().size() + path2.getCells().indexOf(cell);
        if(length1 <= length2 && length1 < 100 * 1000)return path1;
        if (length1 > length2)return path2;
        return null;
    }

    @Override
    public Path getShortestPathToCell(int fromPlayerId, Cell cell){
        Player player = getPlayerById(fromPlayerId);
        if(player == null || cell == null)return null;
        return player.getShortestPathsToCells()[cell.getRow()][cell.getCol()];
    }

    @Override
    public int getMaxAP() {
        return clientInitMessage.getGameConstants().getMaxAP();
    }

    @Override
    public int getRemainingAP() {
        return clientTurnMessage.getRemainingAP();
    }

    @Override
    public List<BaseUnit> getHand() {
        return turnMessage.getHand();
    }

    @Override
    public List<BaseUnit> getDeck() {
        return turnMessage.getDeck();
    }

    @Override
    public void putUnit(int typeId, int pathId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("typeId", Json.GSON.toJsonTree(typeId));
        jsonObject.add("pathId", Json.GSON.toJsonTree(pathId));
        Message message = new Message("putUnit", this.getCurrentTurn(), jsonObject);
        sender.accept(message);
    }

    public void putUnit(BaseUnit baseUnit, int pathId) {
        if (baseUnit == null) return;
        putUnit(baseUnit.getTypeId(), pathId);
    }

    public void putUnit(int typeId, Path path) {
        if (path == null) return;
        putUnit(typeId, path.getId());
    }

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
    public int getMaxTurns() {
        return clientInitMessage.getGameConstants().getMaxTurns();
    }

    @Override
    public int getPickTimeout() {
        return clientInitMessage.getGameConstants().getPickTimeout();
    }

    @Override
    public int getTurnTimeout() {
        return clientInitMessage.getGameConstants().getTurnTimeout();
    }

    @Override
    public int getRemainingTime() {
        long result = (clientTurnMessage.getTurnTime() + clientInitMessage.getGameConstants().getTurnTimeout() -
                System.currentTimeMillis());
        return (int) result;
    }

    @Override
    public int getPlayerHP(int playerId) {
        King king = getPlayerKing(playerId);
        if (king == null) return -1;
        return king.getHp();
    }

    @Override
    public void castUnitSpell(int unitId, int pathId, int index, int spellId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("typeId", Json.GSON.toJsonTree(spellId));
        jsonObject.add("unitId", Json.GSON.toJsonTree(unitId));
        jsonObject.add("pathId", Json.GSON.toJsonTree(pathId));
        Path path = InitMessage.getInitMessage().getPathById(pathId);
        if (path == null) return;
        if (index >= path.getCells().size()) return;

        Cell cell = path.getCells().get(index);
        jsonObject.add("cell", Json.GSON.toJsonTree(cell.castToClientCell()));
        Message message = new Message("castSpell", this.getCurrentTurn(), jsonObject);
        sender.accept(message);
    }

    @Override
    public void castUnitSpell(int unitId, int pathId, int index, Spell spell) {
        if (spell == null) return;
        int spellId = spell.getTypeId();
        castUnitSpell(unitId, pathId, index, spellId);
    }

    @Override
    public void castAreaSpell(int row, int col, int spellId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("typeId", Json.GSON.toJsonTree(spellId));
        Cell cell = getCellByCoordination(row, col);
        if (cell == null) return;
        jsonObject.add("cell", Json.GSON.toJsonTree(cell.castToClientCell()));
        Message message = new Message("castSpell", this.getCurrentTurn(), jsonObject);
        sender.accept(message);
    }

    @Override
    public void castAreaSpell(int row, int col, Spell spell) {
        if (spell == null) return;
        int spellId = spell.getTypeId();
        castAreaSpell(row, col, spellId);
    }

    @Override
    public void castAreaSpell(Cell center, Spell spell) {
        if (center == null || spell == null) return;
        int spellId = spell.getTypeId();
        int row = center.getRow();
        int col = center.getCol();
        castAreaSpell(row, col, spellId);
    }

    @Override
    public void castAreaSpell(Cell center, int spellId) {
        if (center == null) return;
        int row = center.getRow();
        int col = center.getCol();
        castAreaSpell(row, col, spellId);
    }

    @Override
    public List<Unit> getAreaSpellTargets(Cell center, Spell spell) {
        if (center == null || spell == null) return new ArrayList<>();
        if (!(spell instanceof AreaSpell))
            return new ArrayList<>();
        List<Unit> units = new ArrayList<>();
        AreaSpell areaSpell = (AreaSpell) spell;
        int minRow = Math.max(0, center.getRow() - areaSpell.getRange());
        int maxRow = Math.min(initMessage.getMapp().getRows() - 1, center.getRow() + areaSpell.getRange());
        int minCol = Math.max(0, center.getCol() - areaSpell.getRange());
        int maxCol = Math.min(initMessage.getMapp().getCols() - 1, center.getCol() + areaSpell.getRange());
        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                units.addAll(initMessage.getMapp().getCells()[i][j].getUnitList());
            }
        }
        return units;
    }

    @Override
    public List<Unit> getAreaSpellTargets(Cell center, int spellId) {
        //spellId hamun type ?
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
        if(cell == null)return new ArrayList<>();
        return new ArrayList<>(getAreaSpellTargets(cell, spellId));
    }

    private Cell getCellByCoordination(int row, int col) {
        // todo what does this do if row and col be invalid
        if(row >= initMessage.getMapp().getRows() || row < 0)return null;
        if(col >= initMessage.getMapp().getCols() || col < 0)return null;
        return initMessage.getMapp().getCells()[row][col];
    }

    @Override
    public int getRemainingTurnsToUpgrade() {
        //+=1 esho havasemun bashe
        return clientInitMessage.getGameConstants().getTurnsToUpgrade() -
                clientTurnMessage.getCurrTurn() % clientInitMessage.getGameConstants().getTurnsToUpgrade();
    }

    @Override
    public int getRemainingTurnsToGetSpell() {
        return clientInitMessage.getGameConstants().getTurnsToSpell() -
                clientTurnMessage.getCurrTurn() % clientInitMessage.getGameConstants().getTurnsToSpell();
    }

    @Override
    public CastAreaSpell getCastAreaSpell(int playerId) {
        Player player = getPlayerById(playerId);
        if (player == null) return null;
        if (player.isCalcCastAreaSpell() == false) {
            for (CastSpell castSpell : turnMessage.getCastSpells()) {
                if (castSpell instanceof CastAreaSpell) {
                    if (castSpell.getCasterId() == playerId)
                        player.setCastAreaSpell((CastAreaSpell) castSpell);
                }
            }
            player.setCalcCastAreaSpell(true);
        }
        return player.getCastAreaSpell();
    }

    @Override
    public CastUnitSpell getCastUnitSpell(int playerId) {
        Player player = getPlayerById(playerId);
        if (player == null) return null;
        if (player.isCalcCastUnitSpell() == false) {
            for (CastSpell castSpell : turnMessage.getCastSpells()) {
                if (castSpell instanceof CastUnitSpell) {
                    if (castSpell.getCasterId() == playerId)
                        player.setCastUnitSpell((CastUnitSpell) castSpell);
                }
            }
            player.setCalcCastUnitSpell(true);
        }
        return player.getCastUnitSpell();
    }

    @Override
    public List<CastSpell> getCastSpellOnUnit(Unit unit) {
        if (unit == null) return new ArrayList<>();
        return getCastSpellOnUnit(unit.getUnitId());
    }

    @Override
    public List<CastSpell> getCastSpellOnUnit(int unitId) {
        //todo ino chera nazadim ?
        return null;
    }

    @Override
    public int getDamageUpgradeNumber() {
        //avaz kardimesh
        return clientTurnMessage.getAvailableDamageUpgrades();
    }

    @Override
    public int getRangeUpgradeNumber() {
        //inam khodemun ezade kardim
        return clientTurnMessage.getAvailableRangeUpgrades();
    }

    private List<Spell> getSpellsByIds(List<Integer> list) {
        List<Spell> spells = new ArrayList<>();
        for (int spellId : list) {
            Spell spell = spellsByTypeId.get(spellId);
            if (spell != null) spells.add(spell);
        }
        return spells;
    }

    @Override
    public List<Spell> getSpellsList() {
        //spellId hamun type e ?
        return getSpellsByIds(clientTurnMessage.getMySpells());
    }

    @Override
    public HashMap<Spell, Integer> getSpells() {
        return new HashMap<>(myTurnSpells);
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
        if(unit == null)return;
        upgradeUnitRange(unit.getUnitId());
    }

    @Override
    public void upgradeUnitRange(int unitId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("unitId", Json.GSON.toJsonTree(unitId));
        Message message = new Message("rangeUpgrade", this.getCurrentTurn(), jsonObject);
        sender.accept(message);
    }

    @Override
    public void upgradeUnitDamage(Unit unit) {
        if(unit == null)return;
        upgradeUnitDamage(unit.getUnitId());
    }

    @Override
    public void upgradeUnitDamage(int unitId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("unitId", Json.GSON.toJsonTree(unitId));
        Message message = new Message("damageUpgrade", this.getCurrentTurn(), jsonObject);
        sender.accept(message);
    }

    @Override
    public List<Unit> getPlayerDuplicateUnits(int playerId) {
        return getUnitsWithSpellOfPlayer(SpellType.DUPLICATE, playerId);
    }

    //////////////////////////////////////////////////// ino badan behtar konim
    private List<Unit> getUnitsWithSpellOfPlayer(SpellType spellType, int playerId) {
        //todo kamel optimize nashode
        List<Unit> units = new ArrayList<>();
        Player player = getPlayerById(playerId);
        if (player == null) return new ArrayList<>();
        for (Unit unit : player.getPlayerUnits()) {
            for (int spellId : unit.getAffectedSpells()) {
                Spell spell = spellsByTypeId.get(spellId);    //todo use a map for getting spell from spellId
                if (spell == null)
                    continue;
                if (spell.getType() == spellType) {
                    units.add(unit);
                    break;
                }
            }
        }
        return units;
    }

    //////////////////////////////////////////////////////////
    @Override
    public List<Unit> getPlayerHastedUnits(int playerId) {
        return getUnitsWithSpellOfPlayer(SpellType.HASTE, playerId);
    }

    @Override
    public List<Unit> getPlayerPlayedUnits(int playerId) {
        Player player = getPlayerById(playerId);
        if (player == null) return new ArrayList<>();
        return player.getTurnPlayedUnits();
    }

    @Override
    public Unit getUnitTarget(Unit unit) {
        if(unit == null)return null;
        return unitsById.get(unit.getTargetId());
    }

    @Override
    public Unit getUnitTarget(int unitId) {
        return getUnitTarget(unitsById.get(unitId));
    }

    @Override
    public Cell getUnitTargetCell(Unit unit) {
        if (unit == null) return null;
        Unit target = getUnitTarget(unit);
        if (target == null) return null;
        return target.getCell();
    }

    @Override
    public Cell getUnitTargetCell(int unitId) {
        Unit unit = unitsById.get(unitId);
        if(unit == null)return null;
        return getUnitTargetCell(unit);
    }

    @Override
    public Unit getKingTarget(int playerId) {
        King king = getPlayerKing(playerId);
        if (king == null) return null;
        int unitId = king.getTarget();
        if (unitId == -1) return null;
        Unit unit = unitsById.get(unitId);
        return unit;
    }

    @Override
    public Cell getKingTargetCell(int playerId) {
        Unit unit = getKingTarget(playerId);
        if (unit == null) return null;
        return unit.getCell();
    }

    @Override
    public int getKingUnitIsAttackingTo(Unit unit) {
        if (unit == null) return -1;
        if (unit.getTargetId() == -1) return -1;
        if (unit.getTargetId() >= 4) return -1;
        return unit.getTargetId();
    }

    @Override
    public int getKingUnitIsAttackingTo(int unitId) {
        Unit unit = unitsById.get(unitId);
        if (unit == null) return -1;
        return getKingUnitIsAttackingTo(unit);
    }

    public ClientInitMessage getClientInitMessage() {
        return clientInitMessage;
    }

    public void setClientInitMessage(ClientInitMessage clientInitMessage) {
        this.clientInitMessage = clientInitMessage;
    }

    private Player getPlayerById(int playerId) {
        for (Player player : players)
            if (player.getPlayerID() == playerId)
                return player;
        return null;
    }

    private void setPLayersUnits() {
        for (Player player : players) {
            player.setPlayerUnits(calcPlayerUnits(player.getPlayerID()));
        }
    }

    private void emptyPlayersCastSpells() {
        for (Player player : players) {
            player.setCalcCastAreaSpell(false);
            player.setCalcCastUnitSpell(false);
        }
    }

    private void calcUnitsById() {
        //todo unitid doroste dige ? ya nadarim dige?
        unitsById = new HashMap<>();
        for (Unit unit : turnMessage.getUnits()) {
            if (unitsById.get(unit.getUnitId()) == null)
                unitsById.put(unit.getUnitId(), unit);
        }
    }

    private void calcMyTurnSpells() {
        List<Spell> spells = getSpellsByIds(clientTurnMessage.getMySpells());
        myTurnSpells = new HashMap<Spell, Integer>();
        for (Spell spell : spells) {
            int currentCounter = 0;
            if (myTurnSpells.containsKey(spell)) {
                currentCounter = myTurnSpells.get(spell);
                myTurnSpells.remove(spell);
            }
            currentCounter++;
            myTurnSpells.put(spell, currentCounter + 1);
        }
    }

    private void calcPlayerplayedUnits(Player player) {
        List<Unit> playedUnits = new ArrayList<>();
        for (Unit unit : turnMessage.getUnits())
            if (unit.getPlayerId() == player.getPlayerID() && unit.isWasPlayedThisTurn())
                playedUnits.add(unit);
        player.setTurnPlayedUnits(playedUnits);
    }

    private void calcPlayersPlayedUnits() {
        for (Player player : players)
            calcPlayerplayedUnits(player);
    }

    public void handleTurnMessage(Message msg) {
        System.out.println(Json.GSON.toJson(msg));
        this.clientTurnMessage = Json.GSON.fromJson(msg.getInfo(), ClientTurnMessage.class);
        this.clientTurnMessage.setTurnTime(System.currentTimeMillis());
        turnMessage = clientTurnMessage.castToTurnMessage(initMessage);

        setPLayersUnits();
        emptyPlayersCastSpells();
        calcUnitsById();
        calcMyTurnSpells();
        calcPlayersPlayedUnits();
    }

    private void createPLayers() {
        Player myPLayer = new Player(getMyId());
        Player friendPlayer = new Player(getFriendId());
        Player firstEnemyPlayer = new Player(getFirstEnemyId());
        Player secondEnemyPlayer = new Player(getSecondEnemyId());
        players.add(myPLayer);
        players.add(friendPlayer);
        players.add(firstEnemyPlayer);
        players.add(secondEnemyPlayer);
    }

    private Path calcShortestPathToCellFromPlayer(int playerId, Cell cell){
        int minDis = 100 * 1000;
        Cell playerCell = getPlayerPosition(playerId);
        if(cell == null)return null;
        Path bestPath = null;
        for(Path path : initMessage.getMapp().getPaths()){
            if(path.getCells().indexOf(playerCell) != 0)Collections.reverse(path.getCells());
            if(path.getCells().indexOf(playerCell) == 0){
                int index = path.getCells().indexOf(cell);
                if(index < minDis){
                    minDis = index;
                    bestPath = path;
                }
            }
        }
        return bestPath;
    }

    private void setShortestPathsOfPlayer(Player player) {
        //todo ino doros konimmmm
        Path[][] shortestPathsFromPlayer = new Path[initMessage.getMapp().getRows()][initMessage.getMapp().getCols()];
        for (int i = 0; i < initMessage.getMapp().getRows(); i++) {
            for (int j = 0; j < initMessage.getMapp().getCols(); j++) {
                Cell cell = initMessage.getMapp().getCells()[i][j];
                shortestPathsFromPlayer[i][j] = calcShortestPathToCellFromPlayer(player.getPlayerID(), cell);
            }
        }
        player.setShortestPathsToCells(shortestPathsFromPlayer);

        Path[][] shortestPaths = new Path[initMessage.getMapp().getRows()][initMessage.getMapp().getCols()];

        for (int i = 0; i < initMessage.getMapp().getRows(); i++) {
            for (int j = 0; j < initMessage.getMapp().getCols(); j++) {
                Cell cell = initMessage.getMapp().getCells()[i][j];
                shortestPaths[i][j] = calcShortestPathToCell(player.getPlayerID(), cell);
            }
        }
        player.setShortestPathsToCells(shortestPaths);
    }

    //todo shortestpaths
    private void setShortestPathsOfPlayers() {
        for (Player player : players) {
            setShortestPathsOfPlayer(player);
        }
    }

    private void setSpellsById() {
        for (Spell spell : initMessage.getSpells()) {
            if (spellsByTypeId.get(spell.getTypeId()) == null)
                spellsByTypeId.put(spell.getTypeId(), spell);
        }
    }

    private void calcPathsFromPlayer(Player player) {
        if(player == null)return;
        Cell playerKingCell = getPlayerKing(player.getPlayerID()).getCenter();
        Cell friendKingCell = getPlayerKing(getFriendIdOfPlayer(player.getPlayerID())).getCenter();

        List<Path> paths = new ArrayList<>();
        for (Path path : initMessage.getMapp().getPaths())
            if (path.getCells().indexOf(playerKingCell) == 0 || path.getCells().lastIndexOf(playerKingCell) == path.getCells().size() - 1)
                if (!path.getCells().contains(friendKingCell))
                    paths.add(path);
        player.setPathsFromPlayer(paths);
    }

    private void calcPathsFromPlayers() {
        for (Player player : players)
            calcPathsFromPlayer(player);
    }

    private void calcPathToFriend(Player player) {
        Cell playerKingCell = getPlayerKing(player.getPlayerID()).getCenter();
        Cell friendKingCell = getPlayerKing(getFriendIdOfPlayer(player.getPlayerID())).getCenter();

        for (Path path : initMessage.getMapp().getPaths()) {
            List<Cell> pathCells = path.getCells();
            if (pathCells.indexOf(playerKingCell) == 0 || pathCells.lastIndexOf(playerKingCell) == pathCells.size() - 1)
                if (pathCells.indexOf(friendKingCell) == 0 || pathCells.lastIndexOf(friendKingCell) == pathCells.size() - 1)
                    player.setPathToFriend(path);
        }
    }

    private void calcPathsToFriends() {
        for (Player player : players)
            calcPathToFriend(player);
    }

    public void handleInitMessage(Message msg) {
        this.clientInitMessage = Json.GSON.fromJson(msg.getInfo(), ClientInitMessage.class);
        this.initMessage = clientInitMessage.castToInitMessage();
        createPLayers();
        setSpellsById();
        calcPathsToFriends();
        calcPathsFromPlayers();
        setShortestPathsOfPlayers();
    }
}
