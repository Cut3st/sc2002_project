package Entity.Items;

import Control.BattleInfo;
import Entity.Combatants.Player;

public interface Item {
    String getName();
    void use(Player player, BattleInfo context);
}
