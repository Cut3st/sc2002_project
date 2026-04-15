package Entity.Actions;

import Control.BattleInfo;
import Entity.Combatants.Combatant;

public interface Action {
    void execute(Combatant user, BattleInfo context);
    String getName();
}
