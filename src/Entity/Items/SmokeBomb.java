package Entity.Items;

import Control.BattleInfo;
import Entity.Combatants.Player;
import Entity.Effects.SmokeBombEffect;

public class SmokeBomb implements Item {
    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    @Override
    public void use(Player player, BattleInfo context) {
        context.applyStatusEffect(player, new SmokeBombEffect());
        System.out.println(player.getName() + " uses Smoke Bomb!");
    }
}
