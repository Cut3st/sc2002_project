package Entity.Effects;

import Entity.Combatants.Combatant;

public class ArcaneBuff extends StatusEffect {//Define ArcaneBuff Properties
    private final int attackBonus;

    public ArcaneBuff(int numDefeated) {
        super("ARCANE_BUFF", -1);
        this.attackBonus = numDefeated * 10;//Each enemy defeated gets 10
    }

    public void onApply(Combatant target) {
        target.increaseAttack(this.attackBonus);
        System.out.println(target.getName() + " gains +" + this.attackBonus + " ATK from Arcane Buff!");
    }

    public void onExpire(Combatant target) {
    }
}
