package Client.Model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int playerID, AP;
    private boolean upgradeTokens;
    private List<Spell> spells;
    private List<Unit> hand;
    private List<Unit> deck;
    private List<Unit> playerUnits = new ArrayList<>();
    private Path[][] shortestPathsToCells;
    private CastAreaSpell castAreaSpell;
    private CastUnitSpell castUnitSpell;
    private boolean isCalcCastAreaSpell = false;
    private boolean isCalcCastUnitSpell = false;
    private List<Unit> turnPlayedUnits = new ArrayList<>();
    private List<Path> pathsFromPlayer = new ArrayList<>();
    private Path pathToFriend;

    public Player(int playerID){
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getAP() {
        return AP;
    }

    public void setAP(int AP) {
        this.AP = AP;
    }

    public boolean isUpgradeTokens() {
        return upgradeTokens;
    }

    public void setUpgradeTokens(boolean upgradeTokens) {
        this.upgradeTokens = upgradeTokens;
    }


    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }

    public List<Unit> getHand() {
        return hand;
    }

    public void setHand(List<Unit> hand) {
        this.hand = hand;
    }

    public List<Unit> getDeck() {
        return deck;
    }

    public void setDeck(List<Unit> deck) {
        this.deck = deck;
    }

    public List<Unit> getPlayerUnits() {
        return playerUnits;
    }

    public void setPlayerUnits(List<Unit> playerUnits) {
        this.playerUnits = playerUnits;
    }

    public Path[][] getShortestPathsToCells() {
        return shortestPathsToCells;
    }

    public void setShortestPathsToCells(Path[][] shortestPathsToCells) {
        this.shortestPathsToCells = shortestPathsToCells;
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

    public boolean isCalcCastAreaSpell() {
        return isCalcCastAreaSpell;
    }

    public void setCalcCastAreaSpell(boolean calcCastAreaSpell) {
        isCalcCastAreaSpell = calcCastAreaSpell;
    }

    public boolean isCalcCastUnitSpell() {
        return isCalcCastUnitSpell;
    }

    public void setCalcCastUnitSpell(boolean calcCastUnitSpell) {
        isCalcCastUnitSpell = calcCastUnitSpell;
    }

    public List<Unit> getTurnPlayedUnits() {
        return turnPlayedUnits;
    }

    public void setTurnPlayedUnits(List<Unit> turnPlayedUnits) {
        this.turnPlayedUnits = turnPlayedUnits;
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
}
