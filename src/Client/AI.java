package Client;

import Client.Model.Game;
import Client.Model.Path;
import Client.Model.World;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;

public class AI
{

    public void pick(World world)
    {
        System.out.println("pre process started");
        world.chooseDeck(Arrays.asList(3, -324, 4, 0, 1, 400, 2, 3, 4, 5,6 ,7, 8,9));
    }


    public void turn(Game world) {
        System.out.println("turn started");
        System.out.println(world.getDeck().size());
        Path path = world.getInitMessage().getPathById(0);

        world.putUnit(world.getDeck().get(0), path);
    }

}