package Client.Model;

public class CastAreaSpell
{
    private Cell center;
    private int remainingTurns;

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = remainingTurns;
    }

    public Cell getCenter() {
        return center;
    }

    public void setCenter(Cell center) {
        this.center = center;
    }
}

