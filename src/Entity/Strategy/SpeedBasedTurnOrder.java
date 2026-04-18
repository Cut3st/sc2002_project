package Entity.Strategy;
import java.util.*;

import Entity.Combatants.Combatant;

public class SpeedBasedTurnOrder implements TurnOrderStrategy {
    @Override
    public List<Combatant> getTurnOrder(List<Combatant> combatants) {
        List<Combatant> order = new ArrayList<>(combatants);
        order.sort((a, b) -> b.getSpeed() - a.getSpeed());
        return order;
    }
}