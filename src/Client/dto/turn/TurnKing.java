package Client.dto.turn;

import Client.Model.King;

/**
 * This class has information of the king. The data is sent by the server each turn.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class TurnKing {
    private int playerId;
    private int hp;
    private int target;
    private boolean isAlive;

    public void updateKing(King king) {
        king.setPlayerId(playerId);
        king.setAlive(isAlive);
        king.setHp(hp);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
