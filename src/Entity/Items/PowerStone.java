package Entity.Items;

import Control.BattleInfo;
import Entity.Combatants.Player;

public class PowerStone implements Item {
    @Override
    public String getName() {
        return "Power Stone";
    }

    @Override
    public void use(Player player, BattleInfo context) {
        System.out.println(player.getName() + " uses Power Stone!");
        player.getSkill().executeFromPowerStone(player, context);
    }
}
