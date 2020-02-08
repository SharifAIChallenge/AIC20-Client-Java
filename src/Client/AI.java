package Client;

import Client.Model.*;
import common.network.Json;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * You must put your code in this class {@link AI}.
 * This class has {@link #pick}, to choose units before the start of the game;
 * {@link #turn}, to do orders while game is running;
 * {@link #end}, to process after the end of the game;
 */

public class AI {
    private boolean first = true;

    public void pick(World world) {
        System.out.println("pre process started");
        world.chooseDeckById(Arrays.asList(3, -324, 4, 0, 1, 400, 2, 3, 4, 5, 6, 7, 8, 9));
        System.out.println(Json.GSON.toJson(world.getMe().getKing()) + " hello world");
    }

    public void turn(World world) {
        System.out.println("turn started: " + world.getCurrentTurn());
        King mine = world.getMe().getKing();
        List<Path> myPaths = world.getMe().getPathsFromPlayer();
    }

    public void end(World world, Map<Integer, Integer> scores) {
        System.out.println("end started");
    }
}