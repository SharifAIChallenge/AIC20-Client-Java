package Client.dto.init;


public class ClientSpell {
    private int typeId;
    private int turnEffect;
    private boolean isAreaSpell;
    private int range;          //invalid for unit spell
    private int power;          //invalid for unit spell
    private boolean isDamaging; //invalid for unit spell
}
