package Entity.Combatants;

import Entity.EnemyActions.BasicEnemyAttack;

public class Wolf extends Enemy {
    public Wolf(String name) {
        this.name = name;
        this.hp = 40;
        this.maxHp = 40;
        this.attack = 45;
        this.defense = 5;
        this.speed = 35;
        this.action = new BasicEnemyAttack();
    }

    public Wolf() {
        this("Wolf");
    }
}
