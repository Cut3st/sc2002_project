package combat;
import combatants.Combatant;
import java.util.List;

public interface TurnOrderStrategy {
    List<Combatant> getTurnOrder(List<Combatant> combatants);
}