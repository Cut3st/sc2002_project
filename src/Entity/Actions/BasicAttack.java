//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Entity.Actions;

import java.io.PrintStream;

import Control.BattleInfo;
import Entity.combatants.Combatant;

public class BasicAttack implements Action {//Two combatant involved in it
    public void execute(Combatant attacker, BattleInfo info) {
        if (attacker != null && attacker.isAlive()) {
            Combatant target = info.selectTarget(attacker);//Then select the 'target'
            if (target != null && target.isAlive()) {
                int damage = Math.max(0, attacker.getAttack() - target.getDefense());
                int oldHp = target.getHp();//Get the old Hp
                target.receiveDamage(damage);//Update the damage to the target
                int newHp = target.getHp();//Get the new Hp
                PrintStream printing = System.out;
                printing.println(attacker.getName() + " uses BasicAttack on " + target.getName() + " for " + damage + " damage. " + target.getName() + " HP: " + oldHp + " -> " + newHp);
            }
        }
    }

    public String getName() {
        return "BasicAttack";
    }
}

