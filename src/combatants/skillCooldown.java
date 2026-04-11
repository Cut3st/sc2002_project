package combatants;

import combat.BattleInfo;

public abstract class skillCooldown implements SpecialSkill{
    protected int cooldown=0;
    protected static final int MAX_COOLDOWN=3;

    protected boolean powerStoneMode = false; 
    private boolean usedNormallyThisTurn = false;

    @Override
    public boolean isAvailable(){
        return cooldown==0; // if cooldown == 0 will return true, else false
    }

    protected void triggerCooldown(){
        if (!powerStoneMode)
        {
            cooldown=MAX_COOLDOWN;
            usedNormallyThisTurn = true;
        }
    }

    @Override
    public void tickCooldown() {
        if (usedNormallyThisTurn)
        {
            usedNormallyThisTurn = false;
            return;
        }
        if (cooldown > 0) cooldown--;
    }

    @Override
    public void reduceCooldown()
    {
        if (cooldown > 0) cooldown--;
    }

    @Override
    public int getCooldown()
    {
        return cooldown;
    }

    @Override
    public void executeFromPowerStone(Combatant user, BattleInfo context)
    {
        powerStoneMode = true;
        execute(user, context);
        powerStoneMode = false; 
    }
}



