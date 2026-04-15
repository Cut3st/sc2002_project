package Entity.Actions;

import Control.BattleInfo;
import Entity.Combatants.Combatant;
import Entity.Effects.DefendEffect;

public class Defend implements Action {
    @Override
    public void execute(Combatant user, BattleInfo info) {
        if (user == null || !user.isAlive()) return;

        info.applyStatusEffect(user, new DefendEffect());
        System.out.println(user.getName() + " uses Defend. Defense +10 for current and next turn.");
    }

    @Override
    public String getName() {
        return "Defend";
    }
}
