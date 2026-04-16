package Entity.Items;

import Control.BattleInfo;
import Entity.Combatants.Player;

public class Antidote implements Item {
    @Override
    public String getName() {
        return "Antidote";
    }

    @Override
    public void use(Player player, BattleInfo context) {
        boolean removed = context.removeEffectsByName(player, "POISON");
        if (removed) {
            System.out.println(player.getName() + " uses Antidote and cures the poison.");
        } else {
            System.out.println(player.getName() + " uses Antidote, but there is no poison to cure.");
        }
    }
}
