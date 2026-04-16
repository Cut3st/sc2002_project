package Entity.EnemyActions;

import Control.BattleInfo;
import Entity.Combatants.Enemy;
import Entity.Effects.DefendEffect;

public class EnemyDefendAction implements EnemyAction {
    @Override
    public void execute(Enemy enemy, BattleInfo context) {
        context.applyStatusEffect(enemy, new DefendEffect());
        System.out.println(enemy.getName() + " takes a defensive stance.");
    }

    @Override
    public String getIntent(Enemy enemy, BattleInfo context) {
        return enemy.getName() + " is low on HP and chooses to defend.";
    }
}
