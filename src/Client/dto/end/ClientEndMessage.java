package Client.dto.end;

import  Client.dto.turn.ClientTurnMessage;
import java.util.List;

/**
 * This class has properties of the end message.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

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
