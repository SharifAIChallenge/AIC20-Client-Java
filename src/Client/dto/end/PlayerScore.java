package Client.dto.end;

/**
 * This class has the result of the game.
 */

public class PlayerScore {
    private int playerId;
    private int score;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
