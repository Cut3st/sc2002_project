// combat/GameController.java
package combat;

import combatants.*;
import items.*;
import ui.CLI;

import java.util.*;

public class GameController {
    private final CLI cli;

    public GameController(CLI cli) {
        this.cli = cli;
    }

    public void run() {
        cli.showTitleScreen();
        boolean playing = true;

        while (playing) {
            int playerChoice = cli.showPlayerSelection();
            int[] items = cli.showItemSelection();      // M4 will use these values
            int difficulty = cli.showDifficultySelection();

            // Keep these so the loop re-runs correctly
            boolean playAgainSame = true;
            while (playAgainSame) {
                Player player = playerChoice == 1 ? new Warrior() : new Wizard();
                assignItems(player, items);

                List<Combatant> enemies = buildEnemies(difficulty);
                List<Combatant> backup  = buildBackup(difficulty);

                BattleEngine engine = new BattleEngine(player, enemies, backup,
                        new SpeedBasedTurnOrder(), cli);
                engine.runBattle();

                int choice = cli.showPostGameMenu();
                switch (choice) {
                    case 1 -> { /* replay same — inner loop continues */ }
                    case 2 -> { playAgainSame = false; /* breaks inner, outer re-runs setup */ }
                    case 3 -> { playAgainSame = false; playing = false; }
                }
            }
        }
        cli.close();
    }

    private void assignItems(Player player, int[] items)
    {
        for (int choice : items)
        {
            player.addItem(createItem(choice));
        }
    }

    private Item createItem(int choice) {
        return switch (choice) {
            case 1 -> new Potion();
            case 2 -> new PowerStone();
            case 3 -> new SmokeBomb();
            case 4 -> new Antidote();
            default -> throw new IllegalArgumentException("Invalid item choice: " + choice);
        };
    }


    private List<Combatant> buildEnemies(int difficulty) {
        List<Combatant> enemies = new ArrayList<>();
        switch (difficulty) {
            case 1 -> {
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Goblin("Goblin B"));
                enemies.add(new Goblin("Goblin C"));
            }
            case 2 -> {
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Shaman("Shaman A"));
            }
            case 3 -> {
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Wolf("Wolf A"));
                enemies.add(new Shaman("Shaman A"));
            }
        }
        return enemies;
    }

    private List<Combatant> buildBackup(int difficulty) {
        List<Combatant> backup = new ArrayList<>();
        switch (difficulty) {
            case 2 -> {
                backup.add(new Wolf("Wolf A"));
                backup.add(new Shaman("Shaman B"));
            }
            case 3 -> {
                backup.add(new Goblin("Goblin A"));
                backup.add(new Wolf("Wolf B"));
            }
        }
        return backup;
    }
}
