package Entity.Items;

import Control.BattleInfo;
import Entity.Combatants.Player;

public class Potion implements Item {
    private static final int HEAL_AMOUNT = 100;

    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public void use(Player player, BattleInfo context) {
        int oldHp = player.getHp();
        int healed = player.heal(HEAL_AMOUNT);
        System.out.println(player.getName() + " uses Potion. HP: " + oldHp + " -> "
                + player.getHp() + " (+" + healed + ")");
    }
}
