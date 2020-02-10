package Client.dto.init;

import Client.Model.InitMessage;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class has initial information of the game that is sent by the server before the start of the game.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class ClientInitMessage {
    private GameConstants gameConstants;
    private ClientMap map;
    private List<ClientBaseUnit> baseUnits;
    private List<ClientSpell> spells;

    public InitMessage castToInitMessage() {
        InitMessage initMessage = InitMessage.getInitMessage();
        initMessage.setMap(this.map.castToMap());
        initMessage.setBaseUnitList(
                this.baseUnits.stream().map(ClientBaseUnit::castToBaseUnit).collect(Collectors.toList())
        );
        initMessage.setSpells(
                this.spells.stream().map(ClientSpell::castToSpell).collect(Collectors.toList())
        );
        return initMessage;
    }

    public GameConstants getGameConstants() {
        return gameConstants;
    }

    public void setGameConstants(GameConstants gameConstants) {
        this.gameConstants = gameConstants;
    }

    public ClientMap getMap() {
        return map;
    }

    public void setMap(ClientMap map) {
        this.map = map;
    }

    public List<ClientBaseUnit> getBaseUnits() {
        return baseUnits;
    }

    public void setBaseUnits(List<ClientBaseUnit> baseUnits) {
        this.baseUnits = baseUnits;
    }

    public List<ClientSpell> getSpells() {
        return spells;
    }

    public void setSpells(List<ClientSpell> spells) {
        this.spells = spells;
    }
}
