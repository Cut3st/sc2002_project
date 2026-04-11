package combatants;

import combat.BattleInfo;

public abstract class Combatant{
    protected int hp;
    protected int attack;
    protected int defense;
    protected int speed;
    protected String name;
    protected int maxHp;
    
    public abstract void TakeTurn(BattleInfo context);//info about previous move for e.g. who's fighting,current stats,whose turn etc...

    public void receiveDamage(int damage){
        hp=Math.max(0,hp-damage);//choose between 0 or current hp, include 0 as could go negative
    }

    public int heal(int amount)
    {
        int oldHP = hp;
        hp = Math.min(maxHp, hp + amount);
        return hp - oldHP;
    }

    public boolean isAlive(){
        return hp>0; //if hp more than 0 return true, else it will return false
    }
    public void increaseAttack(int amount) {
        this.attack += amount;
    }
    public int getAttack(){
        return attack;
    }
    public int getDefense(){
        return defense;
    }
    public void setDefense(int defense) { this.defense = defense; }
    public int getSpeed() {
        return speed;
    }
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp(){ return maxHp; }

}

