package Client.dto.init;

import Client.Model.Map;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class has information of the map. Data is received from the server in the initial message.
 */

public class ClientMap {
    private int rows;
    private int cols;
    private List<ClientPath> paths;
    private List<ClientBaseKing> kings;

    public Map castToMap() {
        Map map = Map.createMap(this.rows, this.cols);
        map.setPaths(
                this.paths.stream().map(ClientPath::castToPath).collect(Collectors.toList())
        );
        map.setKings(
                this.kings.stream().map(ClientBaseKing::castToKing).collect(Collectors.toList())
        );

        return map;
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
