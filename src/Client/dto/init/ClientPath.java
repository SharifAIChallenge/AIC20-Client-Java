package Client.dto.init;


import Client.Model.Mapp;
import Client.Model.Path;
import Client.dto.ClientCell;

import java.util.List;
import java.util.stream.Collectors;

public class ClientPath {
    private int id;
    private List<ClientCell> cells;

    public Path castToPath() {
        Path path = new Path();
        path.setId(getId());
        path.setCells(getCells().stream().map(
                ClientCell::castToCell
        ).collect(Collectors.toList()));
        return path;
    }

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
