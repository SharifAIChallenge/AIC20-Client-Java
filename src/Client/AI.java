package Client;

import Client.Model.*;
import java.util.List;
import java.util.Map;

public class AI
{
    private boolean first = true;

    public void pick(World world)
    {
        System.out.println("pre process started");
        System.out.println(world.getGameConstants().getHandSize());
        world.chooseDeck(world.getAllBaseUnits());
    }

    public void turn(World world) {
        System.out.println("turn started: " + world.getCurrentTurn());
        List<Path> myPaths = world.getMe().getPathsFromPlayer();
        System.out.println("base units:");
        for(BaseUnit baseUnit:world.getMe().getHand()){
            world.putUnit(baseUnit.getTypeId(), myPaths.get(0));
            System.out.println("TypeId:"+baseUnit.getTypeId());
        }

    }

    public void end(World world, Map<Integer, Integer> scores) {
        System.out.println("end started");
    }
}