package Boundary;

import java.util.*;

import Control.BattleInfo;
import Entity.Actions.*;
import Entity.Combatants.*;
import Entity.Items.*;

public class CLI {
    private final Scanner scanner;

    public CLI() {
        this.scanner = new Scanner(System.in);
    }

    public Combatant selectTarget(List<Combatant> aliveEnemies) {
        System.out.println();
        System.out.println("  Select a target:");
        for (int i = 0; i < aliveEnemies.size(); i++) {
            Combatant t = aliveEnemies.get(i);
            System.out.printf("  [%d] %-15s  HP: %d/%d%n",
                    i + 1, t.getName(), t.getHp(), t.getMaxHp());
        }
        int choice = getIntInput("  Enter choice: ", 1, aliveEnemies.size());
        return aliveEnemies.get(choice - 1);
    }

    public Action chooseItemAction(Player player) {
        System.out.println();
        System.out.println("  Select an item:");

        List<Item> inventory = player.getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            System.out.printf("  [%d] %s%n", i + 1, inventory.get(i).getName());
        }

        int choice = getIntInput("  Enter choice: ", 1, inventory.size());
        return new ItemAction(choice - 1);
    }

    public Action getPlayerAction(Player player) {
        showActionMenu(player);

        while (true) {
            int choice = getIntInput("  Enter choice (1-4): ", 1, 4);

            switch (choice) {
                case 1 -> {
                    return new BasicAttack();
                }
                case 2 -> {
                    return new Defend();
                }
                case 3 -> {
                    if (!player.hasItems()) {
                        System.out.println("  [!] No items remaining. Choose another action.");
                    } else {
                        return chooseItemAction(player);
                    }
                }
                case 4 -> {
                    if (!player.getSkill().isAvailable()) {
                        System.out.println("  [!] Special Skill on cooldown. Choose another action.");
                    } else {
                        return new SpecialSkillAction();
                    }
                }
            }
        }
    }

    public int showModeSelection() {
        printDivider('-', 60);
        System.out.println("  SELECT GAME MODE");
        printDivider('-', 60);
        System.out.println();
        System.out.println("  [1] STANDARD  - Original game (Easy / Medium / Hard)");
        System.out.println("  [2] EXTENDED  - Additional features (new enemies, abilities, items)");
        System.out.println();
        return getIntInput("  Enter choice (1-2): ", 1, 2);
    }

    public void showTitleScreen() {
        printDivider('=', 60);
        printCentered("TURN-BASED COMBAT ARENA", 60);
        printCentered("SC2002 Assignment", 60);
        printDivider('=', 60);
        System.out.println();
    }

    public int showPlayerSelection() {
        printDivider('-', 60);
        System.out.println("  SELECT YOUR CHARACTER");
        printDivider('-', 60);
        System.out.println();
        System.out.println("  [1] WARRIOR");
        System.out.println("      HP: 260  |  ATK: 40  |  DEF: 20  |  SPD: 30");
        System.out.println("      Special Skill: Shield Bash");
        System.out.println("        -> Deal BasicAttack damage to 1 enemy. Enemy stunned 2 turns.");
        System.out.println("        -> Cooldown: 3 turns");
        System.out.println();
        System.out.println("  [2] WIZARD");
        System.out.println("      HP: 200  |  ATK: 50  |  DEF: 10  |  SPD: 20");
        System.out.println("      Special Skill: Arcane Blast");
        System.out.println("        -> Deal BasicAttack damage to ALL enemies.");
        System.out.println("           +10 ATK per enemy eliminated by blast.");
        System.out.println("        -> Cooldown: 3 turns");
        System.out.println();
        return getIntInput("  Enter choice (1-2): ", 1, 2);
    }

    public int[] showItemSelection(int mode) {
        printDivider('-', 60);
        System.out.println("  SELECT YOUR ITEMS  (Choose 2 - duplicates allowed)");
        printDivider('-', 60);
        System.out.println();
        System.out.println("  [1] POTION       - Restore 100 HP");
        System.out.println("  [2] POWER STONE  - Free use of Special Skill (no cooldown change)");
        System.out.println("  [3] SMOKE BOMB   - Enemy attacks deal 0 dmg this turn + next");
        if (mode == 2) {
            System.out.println("  [4] ANTIDOTE     - Remove Poison from the player");
        }
        System.out.println();
        int max = (mode == 2) ? 4 : 3;
        int item1 = getIntInput("  Select item 1 (1-" + max + "): ", 1, max);
        int item2 = getIntInput("  Select item 2 (1-" + max + "): ", 1, max);
        return new int[]{item1, item2};
    }

    public int showDifficultySelection(int mode) {
        printDivider('-', 60);
        System.out.println("  SELECT DIFFICULTY");
        printDivider('-', 60);
        System.out.println();
        System.out.println("  ENEMY STATS:");
        System.out.println("  Goblin  ->  HP: 55  |  ATK: 35  |  DEF: 15  |  SPD: 25");
        System.out.println("  Wolf    ->  HP: 40  |  ATK: 45  |  DEF:  5  |  SPD: 35");
        if (mode == 2) {
            System.out.println("  Shaman  ->  HP: 45  |  ATK: 30  |  DEF: 10  |  SPD: 20");
        }
        System.out.println();
        printDivider('-', 60);
        System.out.println("  [1] EASY    - 3 Goblins");
        System.out.println("  [2] MEDIUM  - 1 Goblin + 1 Wolf  |  Backup: 2 Wolves");
        if (mode == 2) {
            System.out.println("  [3] HARD    - 1 Goblin + 1 Shaman  |  Backup: 1 Goblin + 1 Wolf");
        } else {
            System.out.println("  [3] HARD    - 2 Goblins  |  Backup: 1 Goblin + 2 Wolves");
        }
        System.out.println();
        return getIntInput("  Enter choice (1-3): ", 1, 3);
    }

    public void showBattleStatus(Combatant player, List<Combatant> enemies, int round, BattleInfo context, int mode) {
        System.out.println();
        printDivider('=', 60);
        System.out.printf("  ROUND %d%n", round);
        printDivider('=', 60);

        String playerStatuses = context.getStatusSummary(player);
        System.out.printf("  %-20s  HP: %3d / %3d %s%n",
                player.getName(), player.getHp(), player.getMaxHp(),
                playerStatuses.isEmpty() ? "" : playerStatuses);

        for (Combatant e : enemies) {
            String status = e.isAlive() ? "" : "  [ELIMINATED]";
            String effects = e.isAlive() ? context.getStatusSummary(e) : "";
            System.out.printf("  %-20s  HP: %3d / %3d%s %s%n",
                    e.getName(), e.getHp(), e.getMaxHp(), status, effects);
            if (mode == 2 && e instanceof Entity.Combatants.Enemy enemy && e.isAlive()) {
                System.out.printf("    Intent: %s%n", enemy.getIntent(context));
            }
        }

        if (player instanceof Player p) {
            System.out.println();
            System.out.printf("  Items: %s%n", p.getItemsSummary());
            System.out.printf("  Special Skill: %s - %s%n",
                    p.getSkill().getClass().getSimpleName(),
                    p.getSkill().isAvailable() ? "READY" : "On Cooldown");
        }

        printDivider('=', 60);
    }

    public void showActionMenu(Player player) {
        System.out.println();
        System.out.printf("  %s's Turn - Choose an action:%n", player.getName());
        printDivider('-', 60);
        System.out.println("  [1] Basic Attack   - Attack a selected enemy");
        System.out.println("  [2] Defend         - +10 DEF for this turn and next");
        if (player.hasItems()) {
            System.out.printf("  [3] Use Item       - %s%n", player.getItemsSummary());
        } else {
            System.out.println("  [3] Use Item       - (No items remaining)");
        }
        if (player.getSkill().isAvailable()) {
            System.out.printf("  [4] Special Skill  - %s (READY)%n",
                    player.getSkill().getClass().getSimpleName());
        } else {
            System.out.printf("  [4] Special Skill  - %s (On Cooldown)%n",
                    player.getSkill().getClass().getSimpleName());
        }
        printDivider('-', 60);
    }

    public void showBackupSpawn(List<Combatant> spawned) {
        System.out.println();
        printDivider('!', 60);
        System.out.println("  !! BACKUP SPAWN TRIGGERED !!");
        for (Combatant e : spawned) {
            System.out.printf("    + %-15s  HP: %d  ATK: %d  DEF: %d  SPD: %d%n",
                    e.getName(), e.getHp(), e.getAttack(), e.getDefense(), e.getSpeed());
        }
        printDivider('!', 60);
        System.out.println();
    }

    public void showVictoryScreen(int remainingHp, int totalRounds, int damageDealt, int damageTaken,
                                  int enemiesDefeated, String remainingItems, String itemUsage, int mode) {
        System.out.println();
        printDivider('*', 60);
        printCentered("VICTORY!", 60);
        printDivider('*', 60);
        System.out.println();
        System.out.println("  Congratulations, you have defeated all your enemies.");
        System.out.println();
        System.out.println("  STATISTICS:");
        System.out.printf("  Remaining HP     : %d%n", remainingHp);
        System.out.printf("  Total Rounds     : %d%n", totalRounds);
        System.out.printf("  Items Remaining  : %s%n", remainingItems);
        if (mode == 2) {
            System.out.printf("  Damage Dealt     : %d%n", damageDealt);
            System.out.printf("  Damage Taken     : %d%n", damageTaken);
            System.out.printf("  Enemies Defeated : %d%n", enemiesDefeated);
            System.out.printf("  Items Used       : %s%n", itemUsage);
        }
        System.out.println();
        printDivider('*', 60);
    }

    public void showDefeatScreen(int enemiesRemaining, int totalRounds, int damageDealt, int damageTaken,
                                 int enemiesDefeated, String itemUsage, int mode) {
        System.out.println();
        printDivider('x', 60);
        printCentered("DEFEATED", 60);
        printDivider('x', 60);
        System.out.println();
        System.out.println("  Defeated. Don't give up, try again!");
        System.out.println();
        System.out.println("  STATISTICS:");
        System.out.printf("  Enemies Remaining     : %d%n", enemiesRemaining);
        System.out.printf("  Total Rounds Survived : %d%n", totalRounds);
        if (mode == 2) {
            System.out.printf("  Damage Dealt          : %d%n", damageDealt);
            System.out.printf("  Damage Taken          : %d%n", damageTaken);
            System.out.printf("  Enemies Defeated      : %d%n", enemiesDefeated);
            System.out.printf("  Items Used            : %s%n", itemUsage);
        }
        System.out.println();
        printDivider('x', 60);
    }

    public int showPostGameMenu() {
        System.out.println();
        System.out.println("  What would you like to do?");
        System.out.println("  [1] Replay with the same settings");
        System.out.println("  [2] Start a new game (return to home screen)");
        System.out.println("  [3] Exit");
        return getIntInput("  Enter choice (1-3): ", 1, 3);
    }

    public int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                System.out.println();
                if (value >= min && value <= max) return value;
                System.out.printf("  [!] Enter a number between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input. Please enter a number.");
            }
        }
    }

    private void printDivider(char ch, int width) {
        System.out.println(String.valueOf(ch).repeat(width));
    }

    private void printCentered(String text, int width) {
        int padding = Math.max(0, (width - text.length()) / 2);
        System.out.println(" ".repeat(padding) + text);
    }

    public void close() {
        scanner.close();
    }
}
