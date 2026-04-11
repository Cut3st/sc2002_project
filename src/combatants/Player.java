package combatants;

import actions.Action;
import combat.BattleInfo;
import items.Item;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Player extends Combatant {
    protected SpecialSkill skill;
    private final List<Item> inventory = new ArrayList<>();
    private final Map<String, Integer> usageTracker = new LinkedHashMap<>();

    @Override
    public void TakeTurn(BattleInfo context) {
        if (!isAlive()) return;

        Action action = context.getPlayerAction(this);
        action.execute(this, context);

        // Avoid decrementing cooldown on the same turn skill was normally used.
        skill.tickCooldown();
    }

    public SpecialSkill getSkill() {
        return skill;
    }

    public void addItem(Item item) {
        inventory.add(item);
        usageTracker.putIfAbsent(item.getName(), 0);
    }

    public boolean hasItems() {
        return !inventory.isEmpty();
    }

    public int getItemCount() {
        return inventory.size();
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void useItem(int index, BattleInfo context) {
        if (index < 0 || index >= inventory.size()) {
            System.out.println("Invalid item choice.");
            return;
        }

        Item item = inventory.remove(index);
        item.use(this, context);
        usageTracker.put(item.getName(), usageTracker.getOrDefault(item.getName(), 0) + 1);
    }

    public String getItemsSummary() {
        if (inventory.isEmpty()) return "None";

        Map<String, Integer> counts = new LinkedHashMap<>();
        for (Item item : inventory) {
            counts.put(item.getName(), counts.getOrDefault(item.getName(), 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (!first) sb.append(", ");
            sb.append(entry.getKey()).append(" x").append(entry.getValue());
            first = false;
        }
        return sb.toString();
    }

    public String getUsageSummary() {
        if (usageTracker.isEmpty()) return "No items used";

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Integer> entry : usageTracker.entrySet()) {
            if (!first) sb.append(", ");
            sb.append(entry.getKey()).append(": ").append(entry.getValue());
            first = false;
        }
        return sb.toString();
    }
}
