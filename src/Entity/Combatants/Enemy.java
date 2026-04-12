package Entity.Combatants;
import Entity.EnemyActions.EnemyAction;
import Control.BattleInfo;
public abstract class Enemy extends Combatant{
    protected EnemyAction action;

    protected EnemyAction chooseAction(BattleInfo context) {
        return action;
    }

    public void TakeTurn(BattleInfo context) {
        if (!isAlive()) return;
        if (context.isStunned(this)) {
            System.out.println(getName() + " is stunned and cannot act!");
            return;
        }
        chooseAction(context).execute(this, context);
    }
}
