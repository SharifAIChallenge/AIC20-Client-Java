package Client.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has cells of the path and the id of the path.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class Path {
    private int id;
    private List<Cell> cells;

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Path))
            return false;
        Path path = (Path) object;
        return path.getId() == this.id;
    }

    public Path() {}

    public Path(Path path) {
        this.id = path.getId();
        this.cells = new ArrayList<>(path.getCells());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
