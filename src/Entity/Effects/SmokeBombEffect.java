//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Entity.Effects;

import Entity.Combatants.Combatant;

public class SmokeBombEffect extends StatusEffect {
    public SmokeBombEffect() {
        super("SMOKE_BOMB", 2);
    }//Define SmokeBombEffect Properties

    public void onApply(Combatant smokeUser) {
        System.out.println(smokeUser.getName() + " is shrouded in smoke! Enemy attacks deal 0 damage for 2 turns.");
    }

    public void onExpire(Combatant smokeUser) {
        System.out.println("Smoke clears around " + smokeUser.getName() + ".");
    }
}
