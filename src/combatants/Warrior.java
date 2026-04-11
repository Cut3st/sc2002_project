package combatants;
public class Warrior extends Player{
    public Warrior(){
        this.name = "Warrior";
        this.hp=260;
        this.maxHp = 260;
        this.attack=40;
        this.defense=20;
        this.speed=30;
        this.skill=new ShieldBash();
    }
}
