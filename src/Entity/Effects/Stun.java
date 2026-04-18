package Entity.Effects;

import Entity.Combatants.Combatant;

public class Stun extends StatusEffect {
    public Stun() {
        super("STUNNED", 2);
    }

    public void onApply(Combatant target) {
    }

    public void onExpire(Combatant target) {
    }
}
