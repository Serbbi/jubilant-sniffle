package inventory;

import guis.GUITexture;
import items.Item;
import kotlin.Pair;
import org.joml.Vector2f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import toolbox.Keyboard;
import toolbox.Mouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Inventory {
    private static final int TOTAL_SPOTS = 20;
    private static final float COOLDOWN = 0.3f;
    private Map<Integer, Item> items;
    private int firstFreeSpot = 1;
    private InventoryGUI gui;
    private Item selected;
    private boolean isOpen = false;
    private double lastTimeOpened = 0;
    private Item flyingItem;
    private int previousSpot;

    public Inventory(Loader loader) {
        gui = new InventoryGUI(loader);
        items = new HashMap<>();
    }

    public void select() {
        if (Keyboard.isKeyDown(GLFW_KEY_1)) {
            gui.select(1);
            selected = items.getOrDefault(1, null);
        } else if (Keyboard.isKeyDown(GLFW_KEY_2)) {
            gui.select(2);
            selected = items.getOrDefault(2, null);
        } else if (Keyboard.isKeyDown(GLFW_KEY_3)) {
            gui.select(3);
            selected = items.getOrDefault(3, null);
        } else if (Keyboard.isKeyDown(GLFW_KEY_4)) {
            gui.select(4);
            selected = items.getOrDefault(4, null);
        } else if (Keyboard.isKeyDown(GLFW_KEY_5)) {
            gui.select(5);
            selected = items.getOrDefault(5, null);
        }
    }

    public void mouseSelect() {
        if(flyingItem != null) {
            return;
        }
        float mouseX = Mouse.getMouseX();
        float mouseY = Mouse.getMouseY();
        List<GUITexture> slots = gui.getSlots();
        int shownSlots = 5;
        if(isOpen) {
            shownSlots = TOTAL_SPOTS;
        }
        for (int i = 1; i <= shownSlots; i++) {
            Pair<Float, Float> bottomLeft = slots.get(i).getTextureCoords().getFirst();
            Pair<Float, Float> topRight = slots.get(i).getTextureCoords().getSecond();
            if(mouseX >= bottomLeft.getFirst() && mouseX <= topRight.getFirst() &&
                    mouseY <= bottomLeft.getSecond() && mouseY >= topRight.getSecond()) {
                if(Mouse.isDragging()) {
                    if(flyingItem != null) {
                        return;
                    }
                    if(items.containsKey(i)) {
                        flyingItem = items.get(i);
                        previousSpot = i;
                        selected = null;
                        gui.unselect();
//                        System.out.println("Picked up item");
                        break;
                    }
                }
                gui.select(i);
                selected = items.getOrDefault(i, null);
                break;
            }
        }
    }

    public void updateFlyingItem() {
        if(!Mouse.isPressedLeftButton()) {
            if(flyingItem != null) {
                int slot = isAboveInventory();
                if(slot != -1) {
                    removeItem(previousSpot);
                    if(!addItem(flyingItem, slot)) {
                        addItem(flyingItem, previousSpot);
                    }
                    updateFirstFreeSpot();
//                    System.out.println("Added item to slot " + slot);
                } else {
                    addItem(flyingItem, previousSpot);
                    flyingItem.getTexture().setPosition(gui.getSlots().get(previousSpot).getPosition());
//                    System.out.println("Added item back");
                }
                flyingItem = null;
            }
            return;
        }
        if(flyingItem == null) {
            return;
        }
        int displayWidth = DisplayManager.getWidth();
        int displayHeight = DisplayManager.getHeight();
        float scalingFactorWidth = 2.0f / displayWidth;
        float scalingFactorHeight = -2.0f/ displayHeight;
        Vector2f newPosition = new Vector2f(Mouse.getMouseX() * scalingFactorWidth - 1, Mouse.getMouseY() * scalingFactorHeight + 1);
        flyingItem.getTexture().setPosition(newPosition);
    }

    public InventoryGUI getGui() {
        return gui;
    }

    public boolean addItemAtFirstFreeSpot(Item item) {
        if(firstFreeSpot <= TOTAL_SPOTS) {
            items.put(firstFreeSpot, item);
            gui.addIcon(item.getTexture(), firstFreeSpot);
            updateFirstFreeSpot();
        } else {
            return false;
        }
        return true;
    }

    public boolean addItem(Item item, int slot) {
        if(!items.containsKey(slot)) {
            items.put(slot, item);
            gui.addIcon(item.getTexture(), slot);
            return true;
        }
        return false;
    }

    public void removeItem(int slot) {
        items.remove(slot);
        gui.removeIcon(slot);
    }

    private void updateFirstFreeSpot() {
        for (int i = 1; i <= TOTAL_SPOTS; i++) {
            if(!items.containsKey(i)) {
                firstFreeSpot = i;
                break;
            }
        }
    }

    public void useItem() {
        if (selected == null || isAboveInventory() != -1 || flyingItem != null) {
            return;
        }
        selected.use();
    }

    public int isAboveInventory() {
        float mouseX = Mouse.getMouseX();
        float mouseY = Mouse.getMouseY();
        List<GUITexture> slots = gui.getSlots();
        int shownSlots = 5;
        if(isOpen) {
            shownSlots = TOTAL_SPOTS;
        }
        for (int i = 1; i <= shownSlots; i++) {
            Pair<Float, Float> bottomLeft = slots.get(i).getTextureCoords().getFirst();
            Pair<Float, Float> topRight = slots.get(i).getTextureCoords().getSecond();
            if(mouseX >= bottomLeft.getFirst() && mouseX <= topRight.getFirst() &&
                    mouseY <= bottomLeft.getSecond() && mouseY >= topRight.getSecond()) {
                return i;
            }
        }
        return -1;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        if(lastTimeOpened + COOLDOWN < glfwGetTime()) {
            lastTimeOpened = glfwGetTime();
            isOpen = open;
        }
    }
}
