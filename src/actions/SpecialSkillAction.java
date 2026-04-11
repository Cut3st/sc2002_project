//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package actions;

import combat.BattleInfo;
import combatants.Combatant;
import combatants.Player;

public class SpecialSkillAction implements Action {
    public void execute(Combatant attacker, BattleInfo info) {
        if (attacker != null && attacker.isAlive()) {
            if (!(attacker instanceof Player)) {
                System.out.println("Only players can use Special Skill.");
            } else {
                Player player = (Player)attacker;//Identify whether there is remaining special skill
                if (player.getSkill() == null) {
                    System.out.println("No special skill available.");
                } else {
                    player.getSkill().execute(player, info);
                }
            }
        }
    }

    public String getName() {
        return "SpecialSkill";
    }
}

