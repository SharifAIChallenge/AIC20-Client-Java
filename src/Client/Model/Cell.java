package Client.Model;

import Client.dto.ClientCell;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has properties of the cell in the map.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class Cell {
    private int row;
    private int col;
    private List<Unit> units = new ArrayList<>();

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cell)) return false;
        Cell cell = (Cell) object;
        return cell.getRow() == this.row && cell.getCol() == this.col;
    }

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
