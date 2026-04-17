package Entity.EnemyActions;

import Control.BattleInfo;
import Entity.Combatants.Enemy;
import Entity.Effects.PoisonEffect;

public class PoisonCurse implements EnemyAction {
    @Override
    public void execute(Enemy enemy, BattleInfo context) {
        if (context.isSmokeBombed(context.getPlayer())) {
            System.out.println(enemy.getName() + " tries to cast Poison Curse, but the smoke bomb blocks the attack!");
            return;
        }

        context.applyStatusEffect(context.getPlayer(), new PoisonEffect());
        System.out.println(enemy.getName() + " casts Poison Curse on " + context.getPlayer().getName() + ".");
    }

    @Override
    public String getIntent(Enemy enemy, BattleInfo context) {
        return enemy.getName() + " begins chanting, intent on poisoning " + context.getPlayer().getName() + ".";
    }
}
