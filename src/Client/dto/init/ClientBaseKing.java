package Client.dto.init;

import  Client.Model.King;
import Client.dto.ClientCell;

/**
 * This class has properties of the king before the start of the game. Data is sent by the server in the initial message.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class ClientBaseKing {
    private int playerId;
    private int hp;
    private int attack;
    private int range;
    private ClientCell center;

    public King castToKing() {
        King king = new King();
        king.setCenter(center.castToCell());
        king.setHp(hp);
        king.setAlive(true);
        king.setAttack(attack);
        king.setRange(range);
        king.setPlayerId(playerId);
        return king;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public ClientCell getCenter() {
        return center;
    }

    public void setCenter(ClientCell center) {
        this.center = center;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
