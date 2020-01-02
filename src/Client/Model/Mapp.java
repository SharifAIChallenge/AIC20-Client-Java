package Client.Model;

import java.util.List;

public class Mapp {
    private int cols, rows;
    private List<Path> paths;
    private List<BaseUnit> baseUnits;
    private List<King> kings;

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

    public List<BaseUnit> getBaseUnits() {
        return baseUnits;
    }

    public void setBaseUnits(List<BaseUnit> baseUnits) {
        this.baseUnits = baseUnits;
    }

    public List<King> getKings() {
        return kings;
    }

    public void setKings(List<King> kings) {
        this.kings = kings;
    }
}
