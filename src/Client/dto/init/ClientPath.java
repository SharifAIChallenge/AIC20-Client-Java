package Client.dto.init;

import Client.Model.Path;
import Client.dto.ClientCell;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class has initial information of the path. Data is received from the server before the start of the game.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

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
