package combatants;
import combat.BattleInfo;
public class BasicEnemyAttack implements EnemyAction{
    public void execute(Enemy enemy, BattleInfo context) {
        Combatant player = context.getPlayer();

        // Smoke bomb check — M4 will wire this in fully, stub it here
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
