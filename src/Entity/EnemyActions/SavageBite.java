package Entity.EnemyActions;

import Control.BattleInfo;
import Entity.Combatants.Combatant;
import Entity.Combatants.Enemy;

public class SavageBite implements EnemyAction {
    private static final int BONUS_DAMAGE = 15;

    @Override
    public void execute(Enemy enemy, BattleInfo context) {
        Combatant player = context.getPlayer();

        if (context.isSmokeBombed(player)) {
            System.out.println(enemy.getName() + " lunges with Savage Bite, but the smoke bomb nullifies the attack! (0 damage)");
            return;
        }

        int damage = Math.max(0, enemy.getAttack() - player.getDefense()) + BONUS_DAMAGE;
        int oldHp = player.getHp();
        player.receiveDamage(damage);
        System.out.println(enemy.getName() + " uses Savage Bite on " + player.getName()
                + " for " + damage + " damage. HP: " + oldHp + " -> " + player.getHp());
    }

    @Override
    public String getIntent(Enemy enemy, BattleInfo context) {
        return enemy.getName() + " senses weakened prey and prepares Savage Bite.";
    }
}
