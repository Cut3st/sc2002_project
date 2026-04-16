package Entity.Combatants;
import Entity.EnemyActions.EnemyAction;
import Control.BattleInfo;
public abstract class Enemy extends Combatant{
    protected EnemyAction action;

    protected EnemyAction chooseAction(BattleInfo context) {
        return action;
    }

    protected EnemyAction previewAction(BattleInfo context) {
        return chooseAction(context);
    }

    public void TakeTurn(BattleInfo context) {
        if (!isAlive()) return;
        if (context.isStunned(this)) {
            System.out.println(getName() + " is stunned and cannot act!");
            return;
        }
        chooseAction(context).execute(this, context);
    }

    public String getIntent(BattleInfo context) {
        EnemyAction preview = previewAction(context);
        if (preview == null) {
            return getName() + " is preparing an action.";
        }

        String intent = preview.getIntent(this, context);
        if (intent == null || intent.isBlank()) {
            return getName() + " is preparing an action.";
        }
        return intent;
    }
}
