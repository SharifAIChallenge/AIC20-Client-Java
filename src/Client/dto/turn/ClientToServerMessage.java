package Client.dto.turn;

public class ClientToServerMessage {
    private String type;
    private Information info;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
