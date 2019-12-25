package Client.Model;

import Client.dto.init.ClientInitMessage;
import Client.dto.turn.ClientTurnMessage;

public class Galaxy implements World {
    private ClientInitMessage clientInitMessage;
    private ClientTurnMessage clientTurnMessage;


    public ClientInitMessage getClientInitMessage() {
        return clientInitMessage;
    }

    public void setClientInitMessage(ClientInitMessage clientInitMessage) {
        this.clientInitMessage = clientInitMessage;
    }
}
