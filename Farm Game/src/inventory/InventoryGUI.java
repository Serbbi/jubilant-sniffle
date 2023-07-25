package inventory;

import guis.GUITexture;
import org.joml.Vector2f;
import renderEngine.DisplayManager;
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
        setToLeftOf(slots.get(3), slots.get(2), DISTANCE_BETWEEN_SLOTS);
        setToLeftOf(slots.get(2), slots.get(1), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(slots.get(3), slots.get(4), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(slots.get(4), slots.get(5), DISTANCE_BETWEEN_SLOTS);

        // the middle inventory
        setToLeftOf(slots.get(13), slots.get(12), DISTANCE_BETWEEN_SLOTS);
        setToLeftOf(slots.get(12), slots.get(11), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(slots.get(13), slots.get(14), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(slots.get(14), slots.get(15), DISTANCE_BETWEEN_SLOTS);

        setToAboveOf(slots.get(11), slots.get(6), DISTANCE_BETWEEN_SLOTS);
        setToAboveOf(slots.get(12), slots.get(7), DISTANCE_BETWEEN_SLOTS);
        setToAboveOf(slots.get(13), slots.get(8), DISTANCE_BETWEEN_SLOTS);
        setToAboveOf(slots.get(14), slots.get(9), DISTANCE_BETWEEN_SLOTS);
        setToAboveOf(slots.get(15), slots.get(10), DISTANCE_BETWEEN_SLOTS);

        setToLeftOf(slots.get(8), slots.get(7), DISTANCE_BETWEEN_SLOTS);
        setToLeftOf(slots.get(7), slots.get(6), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(slots.get(8), slots.get(9), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(slots.get(9), slots.get(10), DISTANCE_BETWEEN_SLOTS);

        setToBelowOf(slots.get(11), slots.get(16), DISTANCE_BETWEEN_SLOTS);
        setToBelowOf(slots.get(12), slots.get(17), DISTANCE_BETWEEN_SLOTS);
        setToBelowOf(slots.get(13), slots.get(18), DISTANCE_BETWEEN_SLOTS);
        setToBelowOf(slots.get(14), slots.get(19), DISTANCE_BETWEEN_SLOTS);
        setToBelowOf(slots.get(15), slots.get(20), DISTANCE_BETWEEN_SLOTS);

        setToLeftOf(slots.get(18), slots.get(17), DISTANCE_BETWEEN_SLOTS);
        setToLeftOf(slots.get(17), slots.get(16), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(slots.get(18), slots.get(19), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(slots.get(19), slots.get(20), DISTANCE_BETWEEN_SLOTS);

        for (int i = 1; i <= 20; i++) {
            GUITexture icon = itemIcons.getOrDefault(i, null);
            if (icon != null) {
                icon.setPosition(slots.get(i).getPosition());
                itemIcons.put(i, icon);//idk
            }
        }
    }

    public void setToRightOf(GUITexture guiTexture, GUITexture objectToPlace, float distance) {
        int displayWidth = DisplayManager.getWidth();
        int distanceInPixels = (int) (distance * displayWidth);
        float halfOfReferenceObject = guiTexture.getPosition().x * displayWidth / 2 + (displayWidth * guiTexture.getScale().x / 2.0f);
        float halfOfObjectToPlace = (displayWidth * objectToPlace.getScale().x / 2.0f);
        distanceInPixels += halfOfReferenceObject + halfOfObjectToPlace;
        float percentageOfScreen = (float) distanceInPixels / (displayWidth / 2.0f) * 100;
        objectToPlace.setPosition(new Vector2f(percentageOfScreen/100, objectToPlace.getPosition().y));
    }

    public void setToLeftOf(GUITexture guiTexture, GUITexture objectToPlace, float distance) {
        int displayWidth = DisplayManager.getWidth();
        int distanceInPixels = (int) (distance * -1 * displayWidth);
        float halfOfReferenceObject = guiTexture.getPosition().x * displayWidth / 2 - (displayWidth * guiTexture.getScale().x / 2.0f);
        float halfOfObjectToPlace = (displayWidth * objectToPlace.getScale().x / -2.0f);
        distanceInPixels += halfOfReferenceObject + halfOfObjectToPlace;
        float percentageOfScreen = (float) distanceInPixels / (displayWidth / 2.0f) * 100;
        objectToPlace.setPosition(new Vector2f(percentageOfScreen/100, objectToPlace.getPosition().y));
    }

    public void setToAboveOf(GUITexture guiTexture, GUITexture objectToPlace, float distance) {
        int displayHeight = DisplayManager.getHeight();
        int distanceInPixels = (int) (distance * displayHeight);
        float halfOfReferenceObject = guiTexture.getPosition().y * displayHeight / 2 + (displayHeight * guiTexture.getScale().y / 2.0f);
        float halfOfObjectToPlace = (displayHeight * objectToPlace.getScale().y / 2.0f);
        distanceInPixels += halfOfReferenceObject + halfOfObjectToPlace;
        float percentageOfScreen = (float) distanceInPixels / (displayHeight / 2.0f) * 100;
        objectToPlace.setPosition(new Vector2f(objectToPlace.getPosition().x, percentageOfScreen/100));
    }

    public void setToBelowOf(GUITexture guiTexture, GUITexture objectToPlace, float distance) {
        int displayHeight = DisplayManager.getHeight();
        int distanceInPixels = (int) (distance * -1 * displayHeight);
        float halfOfReferenceObject = guiTexture.getPosition().y * displayHeight / 2 - (displayHeight * guiTexture.getScale().y / 2.0f);
        float halfOfObjectToPlace = (displayHeight * objectToPlace.getScale().y / -2.0f);
        distanceInPixels += halfOfReferenceObject + halfOfObjectToPlace;
        float percentageOfScreen = (float) distanceInPixels / (displayHeight / 2.0f) * 100;
        objectToPlace.setPosition(new Vector2f(objectToPlace.getPosition().x, percentageOfScreen/100));
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
