package Client;

import Client.Model.*;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AI
{

    public void pick(World world)
    {
        System.out.println("pre process started");
        world.chooseDeck(Arrays.asList(3, -324, 4, 0, 1, 400, 2, 3, 4, 5,6 ,7, 8,9));
    }


    public void turn(Game world) {
        System.out.println("turn started: " + world.getCurrentTurn());
        Path path = world.getInitMessage().getPathById(0);

//        if (world.getMyId() == 0)
            world.putUnit(world.getHand().get(0), 1);

        List<Unit> myUnits = world.getPlayerUnits(world.getMyId());

        for (Spell spell : world.getSpellsList()) {
            if (spell.getType() == SpellType.HASTE) {
                if (myUnits.size() == 0)
                    continue;
                world.castAreaSpell(myUnits.get(0).getCell().getRow(), myUnits.get(0).getCell().getCol(), spell);
            }
        }
    }

}