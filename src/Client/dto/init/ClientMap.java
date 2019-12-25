package Client.dto.init;

import java.util.List;

public class ClientMap {
    private int rows;
    private int cols;
    private List<ClientPath> paths;
    private List<ClientBaseKing> kings;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public List<ClientPath> getPaths() {
        return paths;
    }

    public void setPaths(List<ClientPath> paths) {
        this.paths = paths;
    }

    public List<ClientBaseKing> getKings() {
        return kings;
    }

    public void setKings(List<ClientBaseKing> kings) {
        this.kings = kings;
    }
}
