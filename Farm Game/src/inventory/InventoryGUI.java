package inventory;

import guis.GUITexture;
import org.joml.Vector2f;
import renderEngine.DisplayManager;
import renderEngine.Loader;

import java.util.ArrayList;
import java.util.List;

public class InventoryGUI {
    private static final float DISTANCE_BETWEEN_SLOTS = 0.01f;
    private static final float SLOT_SIZE = 0.1f;
    private static final float INVENTORY_WIDTH = 0.5f;
    private static final float INVENTORY_HEIGHT = 0.4f;
    private List<GUITexture> slotTextures;
    private List<GUITexture> itemTextures;
    private List<GUITexture> bigInventory;
    private final int unSelected;
    private final int selected;
    private final int lightYellow;

    public InventoryGUI(Loader loader) {
        slotTextures = new ArrayList<>();
        itemTextures = new ArrayList<>();
        bigInventory = new ArrayList<>();
        unSelected = loader.loadTexture("inventory/blueGrey");
        selected = loader.loadTexture("inventory/white");
        lightYellow = loader.loadTexture("inventory/lightYellow");
        setInventoryGUI();
    }

    private void setInventoryGUI() {
        for (int i = 0; i < 5; i++) {
            slotTextures.add(new GUITexture(unSelected, new Vector2f(0f, -0.88f), new Vector2f(SLOT_SIZE, SLOT_SIZE)));
        }

        GUITexture invBackground = new GUITexture(lightYellow, new Vector2f(0f, 0f), new Vector2f(INVENTORY_WIDTH, INVENTORY_HEIGHT));
        bigInventory.add(invBackground);

        for (int i = 0; i < 15; i++) {
            bigInventory.add(new GUITexture(unSelected, new Vector2f(0f, 0f), new Vector2f(SLOT_SIZE, SLOT_SIZE)));
        }

        resetInventoryGUI();
    }

    public void resetInventoryGUI() {
        setToRightOf(slotTextures.get(2), slotTextures.get(3), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(slotTextures.get(3), slotTextures.get(4), DISTANCE_BETWEEN_SLOTS);
        setToLeftOf(slotTextures.get(2), slotTextures.get(1), DISTANCE_BETWEEN_SLOTS);
        setToLeftOf(slotTextures.get(1), slotTextures.get(0), DISTANCE_BETWEEN_SLOTS);

        setToRightOf(bigInventory.get(3), bigInventory.get(4), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(bigInventory.get(4), bigInventory.get(5), DISTANCE_BETWEEN_SLOTS);
        setToLeftOf(bigInventory.get(3), bigInventory.get(2), DISTANCE_BETWEEN_SLOTS);
        setToLeftOf(bigInventory.get(2), bigInventory.get(1), DISTANCE_BETWEEN_SLOTS);

        setToAboveOf(bigInventory.get(1), bigInventory.get(6), DISTANCE_BETWEEN_SLOTS);
        setToAboveOf(bigInventory.get(2), bigInventory.get(7), DISTANCE_BETWEEN_SLOTS);
        setToAboveOf(bigInventory.get(3), bigInventory.get(8), DISTANCE_BETWEEN_SLOTS);
        setToAboveOf(bigInventory.get(4), bigInventory.get(9), DISTANCE_BETWEEN_SLOTS);
        setToAboveOf(bigInventory.get(5), bigInventory.get(10), DISTANCE_BETWEEN_SLOTS);
        setToLeftOf(bigInventory.get(8), bigInventory.get(7), DISTANCE_BETWEEN_SLOTS);
        setToLeftOf(bigInventory.get(7), bigInventory.get(6), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(bigInventory.get(8), bigInventory.get(9), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(bigInventory.get(9), bigInventory.get(10), DISTANCE_BETWEEN_SLOTS);

        setToBelowOf(bigInventory.get(1), bigInventory.get(11), DISTANCE_BETWEEN_SLOTS);
        setToBelowOf(bigInventory.get(2), bigInventory.get(12), DISTANCE_BETWEEN_SLOTS);
        setToBelowOf(bigInventory.get(3), bigInventory.get(13), DISTANCE_BETWEEN_SLOTS);
        setToBelowOf(bigInventory.get(4), bigInventory.get(14), DISTANCE_BETWEEN_SLOTS);
        setToBelowOf(bigInventory.get(5), bigInventory.get(15), DISTANCE_BETWEEN_SLOTS);
        setToLeftOf(bigInventory.get(13), bigInventory.get(12), DISTANCE_BETWEEN_SLOTS);
        setToLeftOf(bigInventory.get(12), bigInventory.get(11), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(bigInventory.get(13), bigInventory.get(14), DISTANCE_BETWEEN_SLOTS);
        setToRightOf(bigInventory.get(14), bigInventory.get(15), DISTANCE_BETWEEN_SLOTS);

        for (int i = 0; i < itemTextures.size(); i++) {
            itemTextures.get(i).setPosition(slotTextures.get(i).getPosition());
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

    public List<GUITexture> getInventoryTextures() {
        List<GUITexture> inventoryTextures = new ArrayList<>();
        inventoryTextures.addAll(slotTextures);
        inventoryTextures.addAll(itemTextures);
        return inventoryTextures;
    }

    public List<GUITexture> getBigInventoryTextures() {
        return bigInventory;
    }

    public List<GUITexture> getItemTextures() {
        return itemTextures;
    }

    public List<GUITexture> getSlotTextures() {
        return slotTextures;
    }

    public void addIcon(GUITexture icon, int spot) {
        icon.setPosition(slotTextures.get(spot).getPosition());
        itemTextures.add(icon);
    }

    public void unselect() {
        for(int i = 0; i < 5; i++) {
            slotTextures.get(i).setTexture(unSelected);
        }
    }

    public void select(int n) {
        unselect();
        if(n <= slotTextures.size()) {
            slotTextures.get(n - 1).setTexture(selected);
        }
    }
}
