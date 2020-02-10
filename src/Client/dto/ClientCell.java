package Client.dto;

import Client.Model.Cell;
import Client.Model.Map;

/**
 * This class has properties of the cell which is sent by the server.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class ClientCell {
    private int row;
    private int col;

    public Cell castToCell() {
        return Map.getMap().getCells()[row][col];
    }

    public ClientCell(int row, int col) {
        this.row = row;
        this.col = col;
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
