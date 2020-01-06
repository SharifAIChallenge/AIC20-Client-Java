package Client.Model;

import java.util.List;

public class Mapp {
    private static Mapp mapp;
    private int cols, rows;
    private Cell[][] cells;
    private List<Path> paths;
    private List<Unit> units;
    private List<King> kings;

    public static Mapp createMapp(int rows, int cols) {
        if (mapp != null) return mapp;
        mapp = new Mapp(rows, cols);
        return mapp;
    }

    private Mapp(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.cells[i][j] = new Cell(i, j);
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
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

    public static Mapp getMapp() {
        return mapp;
    }

    public static void setMapp(Mapp mapp) {
        Mapp.mapp = mapp;
    }
}
