package Entity.Strategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Entity.Combatants.Combatant;

public class SpeedBasedTurnOrder implements TurnOrderStrategy {
    @Override
    public List<Combatant> getTurnOrder(List<Combatant> combatants) {
        List<Combatant> order = new ArrayList<>(combatants);
        order.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());
        return order;
    }
}