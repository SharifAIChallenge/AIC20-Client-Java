package Client;

import Client.Model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AI
{
    private boolean first = true;

    public void pick(World world)
    {
        System.out.println("pre process started");
        world.chooseDeckById(Arrays.asList(3, -324, 4, 0, 1, 400, 2, 3, 4, 5,6 ,7, 8,9));
    }

    public void turn(World world) {
        System.out.println("turn started: " + world.getCurrentTurn());
        List<Path> myPaths = world.getMe().getPathsFromPlayer();
        world.putUnit(0, myPaths.get(0));
    }

    public void end(World world, Map<Integer, Integer> scores) {
        System.out.println("end started");
    }
}