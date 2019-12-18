package Client.Model;

import java.util.List;

public class World {
    public void chooseDeck(List<Integer> typeIds){

    }

    // todo void chooseDeck(List<Enum> typeIds)

    public int getMyId(){
        return -1;
    }

    public int getFriendId(){
        return -1;
    }

    public Cell getPLayerPosition(int playerId){
        return null;
    }

    public List<Path> getPathsFromPlayer(int playerID){
        return null;
    }

    public Path getPathToFriend(int playerId){
        return null;
    }

    public int getMapHeight(){
        return -1;
    }

    public int getMapWidth(){
        return -1;
    }

    public List<Path> getPathsCrossingCell(Cell cell){
        return null;

    }

}
