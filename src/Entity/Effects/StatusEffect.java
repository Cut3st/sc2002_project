package Entity.Effects;

import Entity.Combatants.Combatant;

public abstract class StatusEffect {//Properties:can extend but not create an object
    protected final String name;
    protected int duration;

    protected StatusEffect(String name, int duration) {
        this.name = name;
        this.duration = duration;//Use this to solve parameter name conflict
    }

    public String getName() {
        return this.name;
    }

    public int getDuration() {
        return this.duration;
    }

    public boolean isExpired() {
        return this.duration <= 0;
    }

    public void tick() {
        if (this.duration > 0) {
            --this.duration;
        }

    }

    public void onTick(Combatant combatantUser) {
    }

    public abstract void onApply(Combatant combatantUser);//Display messages

    public abstract void onExpire(Combatant combatantUser);//Display messages
}
