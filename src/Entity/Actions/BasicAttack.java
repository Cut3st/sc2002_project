package Entity.Actions;

import Control.BattleInfo;
import Entity.Combatants.Combatant;

public class BasicAttack implements Action {
    @Override
    public void execute(Combatant attacker, BattleInfo info) {
        if (attacker == null || !attacker.isAlive()) return;

        Combatant target = info.selectTarget(attacker);
        if (target == null || !target.isAlive()) return;

        int damage = Math.max(0, attacker.getAttack() - target.getDefense());
        int oldHp = target.getHp();
        target.receiveDamage(damage);

        System.out.println(attacker.getName() + " uses BasicAttack on " + target.getName()
                + " for " + damage + " damage. " + target.getName()
                + " HP: " + oldHp + " -> " + target.getHp());
    }

    @Override
    public String getName() {
        return "BasicAttack";
    }
}
