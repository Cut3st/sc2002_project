package combatants;
public class Wolf extends Enemy{
    private static final int SAVAGE_BITE_THRESHOLD = 100;
    private static final int SAVAGE_BITE_COOLDOWN = 2;
    private int savageBiteCooldown = 0;

    public Wolf(String name){
        this.name = name;
        this.hp = 40;
        this.maxHp = 40;
        this.attack=45;
        this.defense=5;
        this.speed=35;
        this.action=new BasicEnemyAttack();
    }
    public Wolf() {
        this("Wolf");
    }

    @Override
    protected EnemyAction chooseAction(combat.BattleInfo context) {
        if (context.getPlayer().getHp() <= SAVAGE_BITE_THRESHOLD && savageBiteCooldown == 0) {
            savageBiteCooldown = SAVAGE_BITE_COOLDOWN;
            return new SavageBite();
        }
        if (savageBiteCooldown > 0) {
            savageBiteCooldown--;
        }
        return super.chooseAction(context);
    }
}
        
