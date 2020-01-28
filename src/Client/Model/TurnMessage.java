package Client.Model;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

public class TurnMessage {
    private List<King> kings;
    private List<Unit> units;
    private List<CastSpell> castSpells;
    private List<BaseUnit>  hand = new ArrayList<>();
    private List<BaseUnit> deck = new ArrayList<>();

    public List<King> getKings() {
        return kings;
    }

    public void setKings(List<King> kings) {
        this.kings = kings;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<CastSpell> getCastSpells() {
        return castSpells;
    }

    public void setCastSpells(List<CastSpell> castSpells) {
        this.castSpells = castSpells;
    }

    public List<BaseUnit> getHand() {
        return hand;
    }

    public void setHand(List<BaseUnit> hand) {
        this.hand = hand;
    }

    public List<BaseUnit> getDeck() {
        return deck;
    }

    public void setDeck(List<BaseUnit> deck) {
        this.deck = deck;
    }
}
