package Client.dto.init;


public class ClientBaseUnit {
    private int typeId;
    private int maxHP;
    private int baseAttack;
    private int baseRange;
    private String target;      //can be enum, values: GROUND, AIR, BOTH
    private boolean isFlying;
    private boolean isMultiple;
}
