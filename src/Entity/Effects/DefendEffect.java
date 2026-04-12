package Entity.Effects;
import Entity.Combatants.Combatant;

public class DefendEffect extends StatusEffect {
    private static final int BONUS = 10;

    public DefendEffect() { super("DEFEND", 2); }

    @Override
    public void onApply(Combatant target) {
        target.setDefense(target.getDefense() + BONUS);
        System.out.println(target.getName() + " defends! +" + BONUS + " DEF for 2 turns.");
    }

    @Override
    public void onExpire(Combatant target) {
        target.setDefense(target.getDefense() - BONUS);
        System.out.println(target.getName() + "'s defense bonus wears off.");
    }
}