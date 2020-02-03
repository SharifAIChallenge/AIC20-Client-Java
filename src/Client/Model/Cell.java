package Client.Model;

import Client.dto.ClientCell;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private int row;
    private int col;
    private List<Unit> units = new ArrayList<>(); //private access ? what should we do

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
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

    public ClientCell castToClientCell() {
        return new ClientCell(row, col);
    }
}
