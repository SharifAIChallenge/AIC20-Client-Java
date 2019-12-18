package Client.Model;

import java.util.List;

public class Cell {
    private int row;
    private int col;
    private List<Unit> unitlist;

    public List<Unit> getUnitlist() {
        return unitlist;
    }

    public void setUnitlist(List<Unit> unitlist) {
        this.unitlist = unitlist;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
