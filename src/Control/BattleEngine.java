package Control;

import Boundary.CLI;
import Entity.Combatants.Combatant;
import Entity.Effects.StatusEffect;
import Entity.Strategy.TurnOrderStrategy;
import java.util.*;
import java.util.stream.Collectors;

public class BattleEngine implements BattleEventListener {
    private final Combatant player;
    private final List<Combatant> enemies;
    private final List<Combatant> backupSpawn;
    private final TurnOrderStrategy turnOrderStrategy;
    private final CLI cli;
    private final int mode;

    private boolean backupSpawned = false;
    private int roundCount = 0;
    private int playerDamageDealt = 0;
    private int playerDamageTaken = 0;
    private int enemiesDefeated = 0;

    // persist effects across rounds
    private final Map<Combatant, List<StatusEffect>> statusEffects = new HashMap<>();

    public BattleEngine(Combatant player, List<Combatant> enemies, List<Combatant> backupSpawn, TurnOrderStrategy strategy, CLI cli, int mode) {
        this.player = player;
        this.enemies = new ArrayList<>(enemies);
        this.backupSpawn = (backupSpawn != null) ? backupSpawn : new ArrayList<>();
        this.turnOrderStrategy = strategy;
        this.cli = cli;
        this.mode = mode;
    }

    @Override
    public void onPlayerDamageTaken(int amount) {
        playerDamageTaken += amount;
    }

    public void runBattle() {
        while (!isBattleOver()) {
            roundCount++;
            BattleInfo context = new BattleInfo(
                player,
                enemies.stream().filter(Combatant::isAlive).collect(Collectors.toList()),
                roundCount,
                cli,
                statusEffects,
                this
            );
            cli.showBattleStatus(player, enemies, roundCount, context, mode);

            List<Combatant> allCombatants = getAllCombatants();
            List<Combatant> turnOrder = turnOrderStrategy.getTurnOrder(allCombatants);

            for (Combatant c : turnOrder) {
                if (!c.isAlive()) continue;
                if (isBattleOver()) break;

                int playerHpBefore = player.getHp();
                Map<Combatant, Integer> enemyHpBefore = snapshotEnemyHp();
                Map<Combatant, Boolean> enemyAliveBefore = snapshotEnemyAlive();

                c.TakeTurn(context);

                // Player effects are ticked at end of round, so they remain active for enemy attacks
                if (c != player) {
                    context.tickEffectsFor(c);
                }

                playerDamageTaken += Math.max(0, playerHpBefore - player.getHp());
                updateEnemyStats(enemyHpBefore, enemyAliveBefore);
            }

            // tick player status effects once per round, after every combatant has acted
            context.tickEffectsFor(player);

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
                cli.showBackupSpawn(backupSpawn);
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

        if (player instanceof Entity.Combatants.Player p) {
            System.out.println("Items: " + p.getItemsSummary());
            System.out.println("Items used: " + p.getUsageSummary());
            System.out.println("Skill cooldown: " + p.getSkill().getCooldown());
        }
    }

    private void displayResult() {
        String itemUsage = player instanceof Entity.Combatants.Player p ? p.getUsageSummary() : "No items used";
        if (player.isAlive()) {
            cli.showVictoryScreen(player.getHp(), roundCount, playerDamageDealt, playerDamageTaken,
                    enemiesDefeated, itemUsage, mode);
        } else {
            long remaining = enemies.stream().filter(Combatant::isAlive).count();
            cli.showDefeatScreen((int) remaining, roundCount, playerDamageDealt, playerDamageTaken,
                    enemiesDefeated, itemUsage, mode);
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
