package Entity.Strategy;
import java.util.List;

import Entity.Combatants.Combatant;

public interface TurnOrderStrategy {
    List<Combatant> getTurnOrder(List<Combatant> combatants);
}