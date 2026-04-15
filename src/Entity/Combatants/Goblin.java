package Entity.Combatants;

import Entity.EnemyActions.BasicEnemyAttack;

public class Goblin extends Enemy {
    public Goblin(String name) {
        this.name = name;
        this.hp = 55;
        this.maxHp = 55;
        this.attack = 35;
        this.defense = 15;
        this.speed = 25;
        this.action = new BasicEnemyAttack();
    }

    public Goblin() {
        this("Goblin");
    }
}
