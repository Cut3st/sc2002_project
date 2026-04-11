package combatants;
public class Goblin extends Enemy{
    private static final int DEFEND_THRESHOLD = 20;

    public Goblin(String name){
        this.name = name;
        this.hp = 55;
        this.maxHp = 55;
        this.attack=35;
        this.defense=15;
        this.speed=25;
        this.action=new BasicEnemyAttack();
    }
    public Goblin() {
        this("Goblin");
    }

    @Override
    protected EnemyAction chooseAction(combat.BattleInfo context) {
        if (getHp() <= DEFEND_THRESHOLD && !context.isDefending(this)) {
            return new EnemyDefendAction();
        }
        return super.chooseAction(context);
    }
}
