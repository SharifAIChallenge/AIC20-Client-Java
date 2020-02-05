package Client.dto.end;

import Client.dto.turn.ClientTurnMessage;

import java.util.List;

public class ClientEndMessage {
    private ClientTurnMessage turnMessage;
    private List<PlayerScore> scores;

    public ClientTurnMessage getTurnMessage() {
        return turnMessage;
    }

    public void setTurnMessage(ClientTurnMessage turnMessage) {
        this.turnMessage = turnMessage;
    }

    public List<PlayerScore> getScores() {
        return scores;
    }

    public void setScores(List<PlayerScore> scores) {
        this.scores = scores;
    }
}
