package Entity.Combatants;

import Control.BattleInfo;
import Entity.EnemyActions.BasicEnemyAttack;
import Entity.EnemyActions.EnemyAction;
import Entity.EnemyActions.EnemyDefendAction;

public class Goblin extends Enemy {
    private static final int DEFEND_THRESHOLD = 20;
    private final boolean extendedMode;

    public Goblin(String name, boolean extendedMode) {
        this.name = name;
        this.hp = 55;
        this.maxHp = 55;
        this.attack = 35;
        this.defense = 15;
        this.speed = 25;
        this.action = new BasicEnemyAttack();
        this.extendedMode = extendedMode;
    }

    public Goblin(String name) {
        this(name, false);
    }

    public Goblin() {
        this("Goblin", false);
    }

    @Override
    protected EnemyAction chooseAction(BattleInfo context) {
        if (extendedMode && getHp() <= DEFEND_THRESHOLD && !context.isDefending(this)) {
            return new EnemyDefendAction();
        }
        return action;
    }
}
