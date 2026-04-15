package Entity.Actions;

import Control.BattleInfo;
import Entity.Combatants.Combatant;
import Entity.Combatants.Player;

public class SpecialSkillAction implements Action {
    @Override
    public void execute(Combatant user, BattleInfo info) {
        if (user == null || !user.isAlive()) return;

        if (!(user instanceof Player player)) {
            System.out.println("Only players can use Special Skill.");
            return;
        }

        if (player.getSkill() == null) {
            System.out.println("No special skill available.");
        } else {
            player.getSkill().execute(player, info);
        }
    }

    @Override
    public String getName() {
        return "SpecialSkill";
    }
}
