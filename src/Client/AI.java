package Client;

import Client.Model.*;

import java.util.*;
import java.util.Map;

/**
 * You must put your code in this class {@link AI}.
 * This class has {@link #pick}, to choose units before the start of the game;
 * {@link #turn}, to do orders while game is running;
 * {@link #end}, to process after the end of the game;
 */

public class AI {
    private int rows;
    private int cols;
    private Random random = new Random();
    private Path pathForMyUnits;

    public void pick(World world) {
        System.out.println("pick started");

        // preprocess
        Client.Model.Map map = world.getMap();
        rows = map.getRowNum();
        cols = map.getColNum();

        List<BaseUnit> allBaseUnits = world.getAllBaseUnits();
        List<BaseUnit> myDeck = new ArrayList<>();

        // choosing all flying units
        for (BaseUnit baseUnit : allBaseUnits) {
            if (baseUnit.isFlying())
                myDeck.add(baseUnit);
        }

        // picking the chosen deck - rest of the deck will automatically be filled with random baseUnits
        world.chooseDeck(myDeck);

        //other preprocess
        pathForMyUnits = world.getFriend().getPathsFromPlayer().get(0);
    }

    public void turn(World world) {
        System.out.println("turn started: " + world.getCurrentTurn());

        Player myself = world.getMe();
        int maxAp = world.getGameConstants().getMaxAP();

        // play all of hand once your ap reaches maximum. if ap runs out, putUnit doesn't do anything
        if (myself.getAp() == maxAp) {
            for (BaseUnit baseUnit : myself.getHand())
                world.putUnit(baseUnit, pathForMyUnits);
        }

        // this code tries to cast the received spell
        Spell receivedSpell = world.getReceivedSpell();
        if (receivedSpell != null) {
            if (receivedSpell.isAreaSpell()) {
                switch (receivedSpell.getTarget()) {
                    case ENEMY:
                        List<Unit> enemyUnits = world.getFirstEnemy().getUnits();
                        if (!enemyUnits.isEmpty())
                            world.castAreaSpell(enemyUnits.get(0).getCell(), receivedSpell);
                        break;
                    case ALLIED:
                        List<Unit> friendUnits = world.getFriend().getUnits();
                        if (!friendUnits.isEmpty())
                            world.castAreaSpell(friendUnits.get(0).getCell(), receivedSpell);
                        break;
                    case SELF:
                        List<Unit> myUnits = myself.getUnits();
                        if (!myUnits.isEmpty())
                            world.castAreaSpell(myUnits.get(0).getCell(), receivedSpell);
                }
            } else {
                List<Unit> myUnits = myself.getUnits();
                if (!myUnits.isEmpty()) {
                    Unit unit = myUnits.get(0);
                    List<Path> myPaths = myself.getPathsFromPlayer();
                    Path path = myPaths.get(random.nextInt(myPaths.size()));
                    int size = path.getCells().size();
                    Cell cell = path.getCells().get((size + 1) / 2);

                    world.castUnitSpell(unit, path, cell, receivedSpell);
                }
            }
        }

        // this code tries to upgrade damage of first unit. in case there's no damage token, it tries to upgrade range
        if (myself.getUnits().size() != 0) {
            Unit unit = myself.getUnits().get(0);
            world.upgradeUnitDamage(unit);
            world.upgradeUnitRange(unit);
        }
    }

    public void end(World world, Map<Integer, Integer> scores) {
        System.out.println("end started");
        System.out.println("My score: " + scores.get(world.getMe().getPlayerId()));
    }
}