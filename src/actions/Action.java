package actions;
import combatants.Combatant;// import combatants
import combat.BattleInfo;//import battle information

public interface Action {
    void execute(Combatant user, BattleInfo context);//function 'execute' to be realized
    String getName();//function 'getName' to be realized
}
