package Client.dto.turn;

import Client.dto.ClientCell;

import java.util.List;

public class TurnCastSpell {
    private int typeId;
    private int casterId;
    private ClientCell cell;
    private int unitId;         // for unit spell
    private int pathId;         // for unit spell and caster == player,friend
    private List<Integer> affectedUnits;
}
