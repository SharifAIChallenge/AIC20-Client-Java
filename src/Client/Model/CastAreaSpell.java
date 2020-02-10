package Client.Model;

/**
 * Area spell that is cast
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class CastAreaSpell extends CastSpell
{
    private int remainingTurns;

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = remainingTurns;
    }

}

