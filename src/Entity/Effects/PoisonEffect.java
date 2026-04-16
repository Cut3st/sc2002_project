package Entity.Effects;

import Entity.Combatants.Combatant;

public class PoisonEffect extends StatusEffect {
    private static final int DAMAGE_PER_TURN = 10;

    public PoisonEffect() {
        super("POISON", 3);
    }

    @Override
    public void onApply(Combatant target) {
        System.out.println(target.getName() + " is poisoned for 3 turns.");
    }

    @Override
    public void onTick(Combatant target) {
        int oldHp = target.getHp();
        target.receiveDamage(DAMAGE_PER_TURN);
        System.out.println(target.getName() + " suffers " + DAMAGE_PER_TURN
                + " poison damage. HP: " + oldHp + " -> " + target.getHp());
    }

    @Override
    public void onExpire(Combatant target) {
        System.out.println(target.getName() + " is no longer poisoned.");
    }
}
