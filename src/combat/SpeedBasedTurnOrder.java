package combat;
import combatants.Combatant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpeedBasedTurnOrder implements TurnOrderStrategy {
    @Override
    public List<Combatant> getTurnOrder(List<Combatant> combatants) {
        List<Combatant> order = new ArrayList<>(combatants);
        order.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());
        return order;
    }
}