package Client.Model;

import Client.dto.init.ClientInitMessage;

public class Galaxy implements World {
    private ClientInitMessage clientInitMessage

    public ClientInitMessage getClientInitMessage() {
        return clientInitMessage;
    }

    public void setClientInitMessage(ClientInitMessage clientInitMessage) {
        this.clientInitMessage = clientInitMessage;
    }
}
