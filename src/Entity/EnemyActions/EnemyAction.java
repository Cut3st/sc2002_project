package Entity.EnemyActions;
import Control.BattleInfo;
import Entity.Combatants.Enemy;
public interface EnemyAction{
    void execute(Enemy enemy,BattleInfo context);
}


