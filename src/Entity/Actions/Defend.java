//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Entity.Actions;

import Control.BattleInfo;
import Entity.combatants.Combatant;
import Entity.effects.DefendEffect;

public class Defend implements Action {
    public void execute(Combatant combatantUser, BattleInfo info) {//One combatant involved in it
        if (combatantUser != null && combatantUser.isAlive()) {
            info.applyStatusEffect(combatantUser, new DefendEffect());//Select DefendEffect on itself
            System.out.println(combatantUser.getName() + " uses Defend. Defense +10 for current and next turn.");
        }
    }

    public String getName() {
        return "Defend";
    }
}