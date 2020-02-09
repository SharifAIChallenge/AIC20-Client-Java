package Client;

import Client.Model.*;
import common.network.Json;

import javax.swing.*;
import java.util.Arrays;
import java.util.Map;

/**
 * You must put your code in this class {@link AI}.
 * This class has {@link #pick}, to choose units before the start of the game;
 * {@link #turn}, to do orders while game is running;
 * {@link #end}, to process after the end of the game;
 */

public class AI {
    public void pick(World world) {
        System.out.println("pre process started");
        world.chooseDeckById(Arrays.asList(3, -324, 4, 0, 1, 400, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    public void turn(World world) {
        System.out.println("turn started: " + world.getCurrentTurn());
        System.out.println(world.);
    }

    public void end(World world, Map<Integer, Integer> scores) {
        System.out.println("end started");
    }
}

//        System.out.println(Json.GSON.toJson(world.getCellUnits(10, 10)));
//        System.out.println(Json.GSON.toJson(me.getCastAreaSpell()));
//        System.out.println(Json.GSON.toJson(me.getCastUnitSpell()));
//        System.out.println(Json.GSON.toJson(me.getDuplicateUnits()));
//        System.out.println(Json.GSON.toJson(me.getHastedUnits()));
//        System.out.println(Json.GSON.toJson(me.getPlayedUnits()));
