package Entity.Actions;
import Control.BattleInfo;
import Entity.combatants.Combatant;

public interface Action {
    void execute(Combatant user, BattleInfo context);//function 'execute' to be realized
    String getName();//function 'getName' to be realized
}
