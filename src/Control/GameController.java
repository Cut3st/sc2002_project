package Control;

import Boundary.CLI;
import Entity.Combatants.*;
import Entity.Items.*;
import Entity.Strategy.SpeedBasedTurnOrder;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final CLI cli;

    public GameController(CLI cli) {
        this.cli = cli;
    }

    public void run() {
        cli.showTitleScreen();
        boolean playing = true;
        int mode = cli.showModeSelection();

        while (playing) {
            int playerChoice = cli.showPlayerSelection();
            int[] items = cli.showItemSelection(mode);
            int difficulty = cli.showDifficultySelection(mode);

            boolean playAgainSame = true;
            while (playAgainSame) {
                Player player = playerChoice == 1 ? new Warrior() : new Wizard();
                assignItems(player, items);

                List<Combatant> enemies = buildEnemies(difficulty, mode);
                List<Combatant> backup = buildBackup(difficulty, mode);

                BattleEngine engine = new BattleEngine(player, enemies, backup,
                        new SpeedBasedTurnOrder(), cli, mode);
                engine.runBattle();

                int choice = cli.showPostGameMenu();
                switch (choice) {
                    case 1 -> { }
                    case 2 -> playAgainSame = false;
                    case 3 -> {
                        playAgainSame = false;
                        playing = false;
                    }
                }
            }
        }
        cli.close();
    }

    private void assignItems(Player player, int[] items) {
        for (int choice : items) {
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

    private List<Combatant> buildEnemies(int difficulty, int mode) {
        boolean ext = (mode == 2);
        List<Combatant> enemies = new ArrayList<>();
        switch (difficulty) {
            case 1 -> {
                enemies.add(new Goblin("Goblin A", ext));
                enemies.add(new Goblin("Goblin B", ext));
                enemies.add(new Goblin("Goblin C", ext));
            }
            case 2 -> {
                enemies.add(new Goblin("Goblin A", ext));
                enemies.add(new Wolf("Wolf A", ext));
            }
            case 3 -> {
                if (ext) {
                    enemies.add(new Goblin("Goblin A", true));
                    enemies.add(new Shaman("Shaman"));
                } else {
                    enemies.add(new Goblin("Goblin A"));
                    enemies.add(new Goblin("Goblin B"));
                }
            }
            default -> throw new IllegalArgumentException("Invalid difficulty: " + difficulty);
        }
        return enemies;
    }

    private List<Combatant> buildBackup(int difficulty, int mode) {
        boolean ext = (mode == 2);
        List<Combatant> backup = new ArrayList<>();
        switch (difficulty) {
            case 2 -> {
                backup.add(new Wolf("Wolf B", ext));
                backup.add(new Wolf("Wolf C", ext));
            }
            case 3 -> {
                if (ext) {
                    backup.add(new Goblin("Goblin B", true));
                    backup.add(new Wolf("Wolf", true));
                } else {
                    backup.add(new Goblin("Goblin C"));
                    backup.add(new Wolf("Wolf A"));
                    backup.add(new Wolf("Wolf B"));
                }
            }
            default -> {
            }
        }
        return backup;
    }
}
