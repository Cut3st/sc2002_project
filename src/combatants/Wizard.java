package combatants;
public class Wizard extends Player{
    public Wizard(){
        this.name = "Wizard";
        this.hp=200;
        this.maxHp = 200;
        this.attack=50;
        this.defense=10;
        this.speed=20;
        this.skill=new ArcaneBlast();
    }
}
