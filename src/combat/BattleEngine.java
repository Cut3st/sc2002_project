package combat;

import ui.CLI;
import effects.StatusEffect;
import combatants.Combatant;

import java.util.*;
import java.util.stream.Collectors;

public class BattleEngine {
    private final Combatant player;
    private final List<Combatant> enemies;
    private final List<Combatant> backupSpawn;
    private final TurnOrderStrategy turnOrderStrategy;
    private final CLI cli;

    private boolean backupSpawned = false;
    private int roundCount = 0;
    private int playerDamageDealt = 0;
    private int playerDamageTaken = 0;
    private int enemiesDefeated = 0;

    // persist effects across rounds
    private final Map<Combatant, List<StatusEffect>> statusEffects = new HashMap<>();

    public BattleEngine(Combatant player, List<Combatant> enemies, List<Combatant> backupSpawn, TurnOrderStrategy strategy, CLI cli) {
        this.player = player;
        this.enemies = new ArrayList<>(enemies);
        this.backupSpawn = (backupSpawn != null) ? backupSpawn : new ArrayList<>();
        this.turnOrderStrategy = strategy;
        this.cli = cli;
    }

    public void runBattle() {
        while (!isBattleOver()) {
            roundCount++;
            BattleInfo context = new BattleInfo(
                player,
                enemies.stream().filter(Combatant::isAlive).collect(Collectors.toList()),
                roundCount,
                cli,
                statusEffects
            );
            cli.showBattleStatus(player, enemies, roundCount, context);

            List<Combatant> allCombatants = getAllCombatants();
            List<Combatant> turnOrder = turnOrderStrategy.getTurnOrder(allCombatants);

            for (Combatant c : turnOrder) {
                if (!c.isAlive()) continue;
                if (isBattleOver()) break;

                int playerHpBefore = player.getHp();
                Map<Combatant, Integer> enemyHpBefore = snapshotEnemyHp();
                Map<Combatant, Boolean> enemyAliveBefore = snapshotEnemyAlive();

                c.TakeTurn(context);
                context.tickEffectsFor(c); // tick only this combatant's effects after their turn

                playerDamageTaken += Math.max(0, playerHpBefore - player.getHp());
                updateEnemyStats(enemyHpBefore, enemyAliveBefore);
            }

            checkBackupSpawn();
            displayRoundSummary();
        }
        displayResult();
    }

    private void checkBackupSpawn() {
        if (!backupSpawned && !backupSpawn.isEmpty()) {
            boolean allInitialDead = enemies.stream().noneMatch(Combatant::isAlive);
            if (allInitialDead) {
                enemies.addAll(backupSpawn);
                backupSpawned = true;
                cli.showBackupSpawn(backupSpawn); // already implemented in CLI
            }
        }
    }

    private boolean isBattleOver() {
        boolean playerDead = !player.isAlive();
        boolean allEnemiesDead = enemies.stream().noneMatch(Combatant::isAlive)
                && (backupSpawned || backupSpawn.isEmpty());
        return playerDead || allEnemiesDead;
    }

    private List<Combatant> getAllCombatants() {
        List<Combatant> all = new ArrayList<>();
        all.add(player);
        all.addAll(enemies);
        return all;
    }

    private void displayRoundSummary() {
        System.out.println("\n--- End of Round " + roundCount + " ---");
        System.out.println(player.getName() + " HP: " + player.getHp() + "/" + player.getMaxHp());

        for (Combatant e : enemies) {
            String status = e.isAlive() ? "HP: " + e.getHp() : "ELIMINATED";
            System.out.println(e.getName() + " " + status);
        }

        if (player instanceof combatants.Player p)
        {
            System.out.println("Items: " + p.getItemsSummary());
            System.out.println("Items used: " + p.getUsageSummary());
            System.out.println("Skill cooldown: " + p.getSkill().getCooldown());
        }
    }

    private void displayResult() {
        String itemUsage = player instanceof combatants.Player p ? p.getUsageSummary() : "No items used";
        if (player.isAlive()) {
            cli.showVictoryScreen(player.getHp(), roundCount, playerDamageDealt, playerDamageTaken,
                    enemiesDefeated, itemUsage); // use CLI screen
        } else {
            long remaining = enemies.stream().filter(Combatant::isAlive).count();
            cli.showDefeatScreen((int) remaining, roundCount, playerDamageDealt, playerDamageTaken,
                    enemiesDefeated, itemUsage); // use CLI screen
        }
    }

    private Map<Combatant, Integer> snapshotEnemyHp() {
        Map<Combatant, Integer> hpSnapshot = new HashMap<>();
        for (Combatant enemy : enemies) {
            hpSnapshot.put(enemy, enemy.getHp());
        }
        return hpSnapshot;
    }

    private Map<Combatant, Boolean> snapshotEnemyAlive() {
        Map<Combatant, Boolean> aliveSnapshot = new HashMap<>();
        for (Combatant enemy : enemies) {
            aliveSnapshot.put(enemy, enemy.isAlive());
        }
        return aliveSnapshot;
    }

    private void updateEnemyStats(Map<Combatant, Integer> enemyHpBefore, Map<Combatant, Boolean> enemyAliveBefore) {
        for (Combatant enemy : enemies) {
            int hpBefore = enemyHpBefore.getOrDefault(enemy, enemy.getHp());
            playerDamageDealt += Math.max(0, hpBefore - enemy.getHp());

            boolean wasAlive = enemyAliveBefore.getOrDefault(enemy, false);
            if (wasAlive && !enemy.isAlive()) {
                enemiesDefeated++;
            }
        }
    }
}
