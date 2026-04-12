//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Entity.Effects;

import java.io.PrintStream;

import Entity.Combatants.Combatant;

public class ArcaneBuff extends StatusEffect {//Define ArcaneBuff Properties
    private final int attackBonus;

    public ArcaneBuff(int numDefeated) {
        super("ARCANE_BUFF", -1);
        this.attackBonus = numDefeated * 10;//Each enemy defeated gets 10
    }

    public void onApply(Combatant ArcaneBuffUser) {
        ArcaneBuffUser.increaseAttack(this.attackBonus);
        PrintStream printing = System.out;
        printing.println(ArcaneBuffUser.getName() + " gains +" + this.attackBonus + " ATK from Arcane Buff!");
    }

    public void onExpire(Combatant var1) {
    }
}
