package inventory;

import items.Item;
import renderEngine.Loader;
import toolbox.Keyboard;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Inventory {
    private static final int TOTAL_SPOTS = 20;
    private Map<Integer, Item> items;
    private int firstFreeSpot = 1;
    private InventoryGUI gui;
    private Item selected;

    public Inventory(Loader loader) {
        gui = new InventoryGUI(loader);
        gui.setInventoryGUI();
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

    public InventoryGUI getGui() {
        return gui;
    }

    public void addItem(Item item) {
        items.put(firstFreeSpot, item);
        if(firstFreeSpot < 6) {
            item.getTexture().setPosition(gui.getSlotCoordinates().get(firstFreeSpot-1));
            gui.addIcon(item.getTexture());
        }
        updateFreeSpot();
    }

    private void updateFreeSpot() {
        for (int i = 1; i <= TOTAL_SPOTS; i++) {
            if(!items.containsKey(i)) {
                firstFreeSpot = i;
                break;
            }
        }
    }

    public void useItem() {
        if (selected == null) {
            return;
        }
        selected.use();
    }

    public Item getSelected() {
        return selected;
    }
}
