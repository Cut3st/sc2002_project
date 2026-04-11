package combatants;

import combat.BattleInfo;

public interface SpecialSkill
{
    void execute(Combatant user,BattleInfo context);
    void executeFromPowerStone(Combatant user, BattleInfo context);
    boolean isAvailable();
    void reduceCooldown();
    void tickCooldown();
    int getCooldown();
}

