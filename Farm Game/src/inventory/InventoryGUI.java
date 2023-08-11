package inventory;

import guis.GUITexture;
import org.joml.Vector2f;
import renderEngine.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryGUI {
    private static final float DISTANCE_BETWEEN_SLOTS = 0.01f;
    private static final float SLOT_SIZE = 0.1f;
    private static final float INVENTORY_WIDTH = 0.5f;
    private static final float INVENTORY_HEIGHT = 0.4f;
    private GUITexture bigInventoryBackground;
    private List<GUITexture> slots;
    private HashMap<Integer, GUITexture> itemIcons;
    private final int unSelected;
    private final int selected;
    private final int lightYellow;

    public InventoryGUI(Loader loader) {
        slots = new ArrayList<>();
        itemIcons = new HashMap<>();

        unSelected = loader.loadTexture("inventory/blueGrey");
        selected = loader.loadTexture("inventory/white");
        lightYellow = loader.loadTexture("inventory/lightYellow");
        setInventoryGUI();
    }

    private void setInventoryGUI() {
        // 5 slots on the bottom inventory bar, indexing starts at 1
        for (int i = 0; i <= 5; i++) {
            slots.add(new GUITexture(unSelected, new Vector2f(0f, -0.88f), new Vector2f(SLOT_SIZE, SLOT_SIZE)));
        }

        bigInventoryBackground = new GUITexture(lightYellow, new Vector2f(0f, 0f), new Vector2f(INVENTORY_WIDTH, INVENTORY_HEIGHT));

        // 15 slots in the middle inventory
        // we start at 6 because we already have 5 slots in the bottom inventory,
        // so the indexing for the big inventory starts at 6
        for (int i = 6; i <= 20; i++) {
            slots.add(new GUITexture(unSelected, new Vector2f(0f, 0f), new Vector2f(SLOT_SIZE, SLOT_SIZE)));
        }

        resetInventoryGUI();
    }

    public void resetInventoryGUI() {
        // the bottom inventory bar
        slots.get(2).setToLeftOfTexture(slots.get(3), DISTANCE_BETWEEN_SLOTS);
        slots.get(1).setToLeftOfTexture(slots.get(2), DISTANCE_BETWEEN_SLOTS);
        slots.get(4).setToRightOfTexture(slots.get(3), DISTANCE_BETWEEN_SLOTS);
        slots.get(5).setToRightOfTexture(slots.get(4), DISTANCE_BETWEEN_SLOTS);

        // the middle inventory
        slots.get(12).setToLeftOfTexture(slots.get(13), DISTANCE_BETWEEN_SLOTS);
        slots.get(11).setToLeftOfTexture(slots.get(12), DISTANCE_BETWEEN_SLOTS);
        slots.get(14).setToRightOfTexture(slots.get(13), DISTANCE_BETWEEN_SLOTS);
        slots.get(15).setToRightOfTexture(slots.get(14), DISTANCE_BETWEEN_SLOTS);

        slots.get(6).setToAboveOfTexture(slots.get(11), DISTANCE_BETWEEN_SLOTS);
        slots.get(7).setToAboveOfTexture(slots.get(12), DISTANCE_BETWEEN_SLOTS);
        slots.get(8).setToAboveOfTexture(slots.get(13), DISTANCE_BETWEEN_SLOTS);
        slots.get(9).setToAboveOfTexture(slots.get(14), DISTANCE_BETWEEN_SLOTS);
        slots.get(10).setToAboveOfTexture(slots.get(15), DISTANCE_BETWEEN_SLOTS);

        slots.get(7).setToLeftOfTexture(slots.get(8), DISTANCE_BETWEEN_SLOTS);
        slots.get(6).setToLeftOfTexture(slots.get(7), DISTANCE_BETWEEN_SLOTS);
        slots.get(9).setToRightOfTexture(slots.get(8), DISTANCE_BETWEEN_SLOTS);
        slots.get(10).setToRightOfTexture(slots.get(9), DISTANCE_BETWEEN_SLOTS);

        slots.get(16).setToBelowOfTexture(slots.get(11), DISTANCE_BETWEEN_SLOTS);
        slots.get(17).setToBelowOfTexture(slots.get(12), DISTANCE_BETWEEN_SLOTS);
        slots.get(18).setToBelowOfTexture(slots.get(13), DISTANCE_BETWEEN_SLOTS);
        slots.get(19).setToBelowOfTexture(slots.get(14), DISTANCE_BETWEEN_SLOTS);
        slots.get(20).setToBelowOfTexture(slots.get(15), DISTANCE_BETWEEN_SLOTS);

        slots.get(17).setToLeftOfTexture(slots.get(18), DISTANCE_BETWEEN_SLOTS);
        slots.get(16).setToLeftOfTexture(slots.get(17), DISTANCE_BETWEEN_SLOTS);
        slots.get(19).setToRightOfTexture(slots.get(18), DISTANCE_BETWEEN_SLOTS);
        slots.get(20).setToRightOfTexture(slots.get(19), DISTANCE_BETWEEN_SLOTS);

        for (int i = 1; i <= 20; i++) {
            GUITexture icon = itemIcons.getOrDefault(i, null);
            if (icon != null) {
                icon.setPosition(slots.get(i).getPosition());
                itemIcons.put(i, icon);//idk
            }
        }
    }

    public void addIcon(GUITexture icon, int spot) {
        icon.setPosition(slots.get(spot).getPosition());
        itemIcons.put(spot, icon);
    }

    public void removeIcon(int spot) {
        itemIcons.remove(spot);
    }

    public void unselect() {
        for (int i = 1; i <= 5; i++) {
            slots.get(i).setTexture(unSelected);
        }
    }

    public void select(int n) {
        unselect();
        if(n <= 5) {
            slots.get(n).setTexture(selected);
        }
    }

    public List<GUITexture> getAllGuis() {
        List<GUITexture> allGuis = new ArrayList<>();
        allGuis.add(bigInventoryBackground);
        allGuis.addAll(slots);
        allGuis.addAll(itemIcons.values());
        return allGuis;
    }

    public List<GUITexture> getBigInventory() {
        List<GUITexture> bigInventory = new ArrayList<>();
        bigInventory.add(bigInventoryBackground);
        bigInventory.addAll(slots.subList(6, 21));
        for (Map.Entry<Integer, GUITexture> entry : itemIcons.entrySet()) {
            if(entry.getKey() > 5) {
                bigInventory.add(entry.getValue());
            }
        }
        return bigInventory;
    }

    public List<GUITexture> getInventoryBar() {
        return new ArrayList<>(slots.subList(1, 6));
    }

    public List<GUITexture> getSlots() {
        return slots;
    }

    public List<GUITexture> getItemIconsFromInventoryBar() {
        List<GUITexture> inventoryBarIcons = new ArrayList<>();
        for (Map.Entry<Integer, GUITexture> entry : itemIcons.entrySet()) {
            if(entry.getKey() <= 5) {
                inventoryBarIcons.add(entry.getValue());
            }
        }
        return inventoryBarIcons;
    }
}
