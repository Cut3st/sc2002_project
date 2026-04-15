package Entity.Actions;

import Control.BattleInfo;
import Entity.Combatants.Combatant;
import Entity.Effects.ArcaneBuff;

public class ArcaneBlast extends skillCooldown {
    @Override
    public void execute(Combatant user, BattleInfo context) {
        if (!isAvailable() && !powerStoneMode) {
            System.out.println("Arcane Blast is on cooldown!");
            return;
        }

        System.out.println(user.getName() + " unleashes Arcane Blast on all enemies!");

        for (Combatant enemy : context.getEnemies()) {
            if (!enemy.isAlive()) continue;

            int damage = Math.max(0, user.getAttack() - enemy.getDefense());
            int oldHp = enemy.getHp();
            enemy.receiveDamage(damage);

            System.out.println("  " + enemy.getName() + " HP: " + oldHp + " -> " + enemy.getHp()
                    + " (dmg: " + damage + ")");
            if (!enemy.isAlive()){
                context.applyStatusEffect(user, new ArcaneBuff(1));
            }
        }

        triggerCooldown();
    }
}
