package Client.Model;

/**
 * This class has information of components of the game.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

import java.util.ArrayList;
import java.util.List;

public class Map {
    private static Map map; //is it ok?
    private int colNum, rowNum;
    private Cell[][] cells;
    private List<Path> paths;
    private List<Unit> units = new ArrayList<>();
    private List<King> kings;

    private Map(int rows, int cols) {
        this.rowNum = rows;
        this.colNum = cols;
        this.cells = new Cell[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.cells[i][j] = new Cell(i, j);
    }

    public static Map createMap(int rows, int cols) {
        if (map != null) return map;
        map = new Map(rows, cols);
        return map;
    }

    public Cell getCell(int row, int col) {
        if (row >= rowNum || row < 0 || col >= colNum || col < 0)
            return null;
        return cells[row][col];
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<King> getKings() {
        return kings;
    }

    public void setKings(List<King> kings) {
        this.kings = kings;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public static Map getMap() {
        return map;
    }

    public static void setMap(Map map) {
        Map.map = map;
    }
}
