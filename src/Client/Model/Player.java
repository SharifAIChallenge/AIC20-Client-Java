package Client.Model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int playerId;
    private List<BaseUnit> deck;
    private List<BaseUnit> hand;
    private int ap;
    private King king;
    private List<Path> pathsFromPlayer = new ArrayList<>();
    private Path pathToFriend;
    private List<Unit> units = new ArrayList<>();
    private CastAreaSpell castAreaSpell;
    private CastUnitSpell castUnitSpell;
    private List<Unit> duplicateUnits;
    private List<Unit> hastedUnits;
    private List<Unit> playedUnits = new ArrayList<>();
    private List<Unit> diedUnits;

    private Unit rangeUpgradedUnit;
    private Unit damageUpgradedUnit;

    ////////
    private Path[][] shortestPathsToCellsCrossMyself;
    private Path[][] shortestPathsToCells;
////////

    public Player(int playerID) {
        this.playerId = playerID;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public List<BaseUnit> getHand() {
        return hand;
    }

    public void setHand(List<BaseUnit> hand) {
        this.hand = hand;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public CastAreaSpell getCastAreaSpell() {
        return castAreaSpell;
    }

    public void setCastAreaSpell(CastAreaSpell castAreaSpell) {
        this.castAreaSpell = castAreaSpell;
    }

    public CastUnitSpell getCastUnitSpell() {
        return castUnitSpell;
    }

    public void setCastUnitSpell(CastUnitSpell castUnitSpell) {
        this.castUnitSpell = castUnitSpell;
    }

    public List<Unit> getPlayedUnits() {
        return playedUnits;
    }

    public void setPlayedUnits(List<Unit> playedUnits) {
        this.playedUnits = playedUnits;
    }

    public List<Path> getPathsFromPlayer() {
        return pathsFromPlayer;
    }

    public void setPathsFromPlayer(List<Path> pathsFromPlayer) {
        this.pathsFromPlayer = pathsFromPlayer;
    }

    public Path getPathToFriend() {
        return pathToFriend;
    }

    public void setPathToFriend(Path pathToFriend) {
        this.pathToFriend = pathToFriend;
    }

    public List<BaseUnit> getDeck() {
        return deck;
    }

    public void setDeck(List<BaseUnit> deck) {
        this.deck = deck;
    }

    public King getKing() {
        return king;
    }

    public void setKing(King king) {
        this.king = king;
    }

    public List<Unit> getDuplicateUnits() {
        return duplicateUnits;
    }

    public void setDuplicateUnits(List<Unit> duplicateUnits) {
        this.duplicateUnits = duplicateUnits;
    }

    public List<Unit> getHastedUnits() {
        return hastedUnits;
    }

    public void setHastedUnits(List<Unit> hastedUnits) {
        this.hastedUnits = hastedUnits;
    }

    public List<Unit> getDiedUnits() {
        return diedUnits;
    }

    public void setDiedUnits(List<Unit> diedUnits) {
        this.diedUnits = diedUnits;
    }

    public Unit getRangeUpgradedUnit() {
        return rangeUpgradedUnit;
    }

    public void setRangeUpgradedUnit(Unit rangeUpgradedUnit) {
        this.rangeUpgradedUnit = rangeUpgradedUnit;
    }

    public Unit getDamageUpgradedUnit() {
        return damageUpgradedUnit;
    }

    public void setDamageUpgradedUnit(Unit damageUpgradedUnit) {
        this.damageUpgradedUnit = damageUpgradedUnit;
    }

    /////////////

    Path[][] getShortestPathsToCellsCrossMyself() {
        return shortestPathsToCellsCrossMyself;
    }

    void setShortestPathsToCellsCrossMyself(Path[][] shortestPathsToCellsCrossMyself) {
        this.shortestPathsToCellsCrossMyself = shortestPathsToCellsCrossMyself;
    }

    Path[][] getShortestPathsToCells() {
        return shortestPathsToCells;
    }

    void setShortestPathsToCells(Path[][] shortestPathsToCells) {
        this.shortestPathsToCells = shortestPathsToCells;
    }

    ///////////////////////

    public boolean isAlive() {
        return king.isAlive();
    }

    public int getHp(){
        return king.getHp();
    }

}
