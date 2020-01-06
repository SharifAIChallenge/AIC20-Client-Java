package Client.dto;

import Client.Model.Cell;

public class ClientCell {
    private int row;
    private int col;

    public Cell castToCell() {
        return new Cell(row, col);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
