package Client.dto.init;


import Client.dto.ClientCell;

import java.util.List;

public class ClientPath {
    private int id;
    private List<ClientCell> cells;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ClientCell> getCells() {
        return cells;
    }

    public void setCells(List<ClientCell> cells) {
        this.cells = cells;
    }
}
