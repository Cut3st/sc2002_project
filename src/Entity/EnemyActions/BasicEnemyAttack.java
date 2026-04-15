package Entity.EnemyActions;

import Control.BattleInfo;
import Entity.Combatants.Combatant;
import Entity.Combatants.Enemy;

public class BasicEnemyAttack implements EnemyAction {
    @Override
    public void execute(Enemy enemy, BattleInfo context) {
        Combatant player = context.getPlayer();

        if (context.isSmokeBombed(player)) {
            System.out.println(enemy.getName() + " attacks " + player.getName()
                    + " but the smoke bomb absorbs the hit! (0 damage)");
            return;
        }

        int damage = Math.max(0, enemy.getAttack() - player.getDefense());
        int oldHp = player.getHp();
        player.receiveDamage(damage);
        System.out.println(enemy.getName() + " uses BasicAttack on " + player.getName()
                + " for " + damage + " damage. HP: " + oldHp + " -> " + player.getHp());
    }
}
