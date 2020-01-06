package Client.dto.init;

import Client.Model.Cell;
import Client.Model.Mapp;

import java.util.List;
import java.util.stream.Collectors;

public class ClientMap {
    private int rows;
    private int cols;
    private List<ClientPath> paths;
    private List<ClientBaseKing> kings;

    public Mapp castToMap() {
        Mapp mapp = new Mapp(rows, cols);
        mapp.setPaths(
                paths.stream().map(ClientPath::castToPath).collect(Collectors.toList())
        );
        mapp.setKings(
                kings.stream().map(ClientBaseKing::castToKing).collect(Collectors.toList())
        );

        return mapp;
    }

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
