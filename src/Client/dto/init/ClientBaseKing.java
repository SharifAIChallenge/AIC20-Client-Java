package Client.dto.init;


import Client.dto.ClientCell;

public class ClientBaseKing {
    private int playerId;
    private boolean isYou;
    private boolean isYourFriend;
    private ClientCell center;
    private int hp;
    private int attack;
    private int range;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public boolean isYou() {
        return isYou;
    }

    public void setYou(boolean you) {
        isYou = you;
    }

    public boolean isYourFriend() {
        return isYourFriend;
    }

    public void setYourFriend(boolean yourFriend) {
        isYourFriend = yourFriend;
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
