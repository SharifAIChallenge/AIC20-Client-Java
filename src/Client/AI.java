package Client;

import Client.Model.*;
import Client.dto.init.GameConstants;

import java.util.*;

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
        Mapp mapp = world.getMapp();
        rows = mapp.getRowNum();
        cols = mapp.getColNum();

        List<BaseUnit> allBaseUnits = world.getAllBaseUnits();
        List<Integer> myDeck = new ArrayList<>();

        // choosing all flying units
        for (BaseUnit baseUnit : allBaseUnits) {
            if (baseUnit.isFlying()) {
                myDeck.add(baseUnit.getTypeId());
                System.out.println("max hp:" + baseUnit.getMaxHp());
            }
        }
        for (Spell spell : world.getAllSpells()) {
            System.out.println("spell.getTypeId:" + spell.getTypeId());
        }
        for (Integer id : myDeck) {
            System.out.println(id);
        }
        System.out.println("player Id: " + world.getMe().getPlayerId());
        for (Cell cell : world.getShortestPathToCell(world.getFirstEnemy().getPlayerId(), 15, 15).getCells()) {
            System.out.println("(" + cell.getRow() + "," + cell.getCol() + ")");
        }

        // picking the chosen deck - rest of the deck will automatically be filled with random baseUnits
        world.chooseDeckById(myDeck);

        //other preprocess
        pathForMyUnits = world.getFriend().getPathsFromPlayer().get(0);
        System.out.println("getMe().hp" + world.getKingById(world.getMe().getPlayerId()).getHp());
        System.out.println("getFriend().hp" + world.getKingById(world.getFriend().getPlayerId()).getHp());
        System.out.println("getFirstEnemy().hp" + world.getKingById(world.getFirstEnemy().getPlayerId()).getHp());
        System.out.println("getSecondEnemy().hp" + world.getKingById(world.getSecondEnemy().getPlayerId()).getHp());

    }

    public void turn(World world) throws Exception {
        System.out.println("getMe().hp" + world.getKingById(world.getMe().getPlayerId()).getHp());
        System.out.println("getMe().hp" + world.getMe().getHp());
        System.out.println("getFriend().hp" + world.getKingById(world.getFriend().getPlayerId()).getHp());
        System.out.println("getFirstEnemy().hp" + world.getKingById(world.getFirstEnemy().getPlayerId()).getHp());
        System.out.println("getSecondEnemy().hp" + world.getKingById(world.getSecondEnemy().getPlayerId()).getHp());
        System.out.println("world.getSpellById:" + world.getSpellById(3).getTypeId());
        System.out.println("world.getBaseUnitById:" + world.getBaseUnitById(3).getTypeId());
        System.out.println("world.getPlayerById:" + world.getPlayerById(3).getPlayerId());

        System.out.println("turn started: " + world.getCurrentTurn());
        Player myself = world.getMe();
        int maxAp = world.getGameConstants().getMaxAP();
        int someUnitId = -1;
        for (int i = 0; i < world.getMapp().getRowNum(); i++) {
            for (int j = 0; j < world.getMapp().getColNum(); j++) {
                char c = '.';
                for (Unit unit : world.getCellUnits(i, j)) {
                    if (unit.getPlayerId() == world.getMe().getPlayerId()) {
                        someUnitId = unit.getUnitId();
                        c = 'M';
                    } else if (unit.getPlayerId() == world.getFriend().getPlayerId()) {
                        c = 'F';
                    } else if (unit.getPlayerId() == world.getFirstEnemy().getPlayerId()) {
                        c = '1';
                    } else if (unit.getPlayerId() == world.getSecondEnemy().getPlayerId()) {
                        c = '2';
                    } else {
                        throw new Exception("error");
                    }
                }
                System.out.print(c);
            }
            System.out.println("");
        }
        System.out.println("world.getUnitById:" + someUnitId);
        if (someUnitId != -1)
            System.out.println("world.getUnitById:" + world.getUnitById(someUnitId).getUnitId());
        // play all of hand once your ap reaches maximum. if ap runs out, putUnit doesn't do anything
        if (myself.getAp() == maxAp) {
            for (BaseUnit baseUnit : myself.getHand()) {
                world.putUnit(baseUnit, pathForMyUnits);
                System.out.println("baseUnit.getTypeId"+baseUnit.getTypeId());
                for (BaseUnit baseUnit1 : world.getMe().getHand()) {
                    System.out.println("max hp:" + baseUnit1.getMaxHp());
                }
            }
        }

        // this code tries to cast the received spell
        Spell receivedSpell = world.getReceivedSpell();
        if (receivedSpell != null) {
            if (receivedSpell.isAreaSpell()) {
                switch (receivedSpell.getTarget()) {
                    case ENEMY:
                        List<Unit> enemyUnits = world.getFirstEnemy().getUnits();
                        if (!enemyUnits.isEmpty())
                            for(Unit unit:world.getAreaSpellTargets(enemyUnits.get(0).getCell(),receivedSpell)){
                                System.out.println("getAreaSpellTarget: "+unit.getUnitId());
                            }
                        System.out.println("endOfAreaSpellTarget");
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
        System.out.println("world.getRemainingTime:" + world.getRemainingTime());
        System.out.println("world.getRemainingTurnsToGetSpell:" + world.getRemainingTurnsToGetSpell());
        System.out.println("world.getRemainingTurnsToUpgrade:" + world.getRemainingTurnsToUpgrade());
        System.out.println("world.getRangeUpgradeNumber:" + world.getRangeUpgradeNumber());
        if (world.getRangeUpgradeNumber() > 0) {
            world.upgradeUnitRange(world.getMe().getUnits().get(0));
        }
        if (world.getDamageUpgradeNumber() > 0) {
            world.upgradeUnitDamage(world.getMe().getUnits().get(0));
        }
        System.out.println("world.getDamageUpgradeNumber:" + world.getDamageUpgradeNumber());
        if (world.getReceivedSpell() == null) {
            System.out.println("world.getReceivedSpell:null");
        } else {
            System.out.println("world.getReceivedSpell:" + world.getReceivedSpell().getTypeId());
        }
        if (world.getFriendReceivedSpell() == null) {
            System.out.println("world.getFriendReceivedSpell:null");
        } else {
            System.out.println("world.getFriendReceivedSpell:" + world.getFriendReceivedSpell().getTypeId());
        }
    }

    public void end(World world, Map<Integer, Integer> scores) {
        System.out.println("end started");
        System.out.println("My score: " + scores.get(world.getMe().getPlayerId()));
    }
}