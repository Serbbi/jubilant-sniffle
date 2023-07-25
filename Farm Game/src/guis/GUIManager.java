package guis;

import inventory.Inventory;
import inventory.InventoryGUI;
import inventory.InventoryItemQuantities;
import org.joml.Vector2f;
import renderEngine.DisplayManager;

import java.util.ArrayList;
import java.util.List;

public class GUIManager {
    private List<GUITexture> guiTextures;
    private InventoryGUI inventoryGUI;
    private InventoryItemQuantities itemQuantities;

    public GUIManager(Inventory inventory) {
        this.inventoryGUI = inventory.getGui();
        this.itemQuantities = inventory.getItemQuantities();
        guiTextures = new ArrayList<>();
    }

    public void addGUI(GUITexture guiTexture) {
        guiTextures.add(guiTexture);
    }

    public void addGUIList(List<GUITexture> list) {
        guiTextures.addAll(list);
    }

    public void resizeGUIs() {
//            System.out.println("Height: " + DisplayManager.getHeight() + "\n");
//            System.out.println("Width: " + DisplayManager.getWidth() + "\n");
        guiTextures = inventoryGUI.getAllGuis();
        for (GUITexture texture: guiTextures) {
            if(texture.getRatio() != 1) {
                continue;
            }
            float y = texture.getScale().y;
            float x = y * DisplayManager.getHeight() / DisplayManager.getWidth();
//            System.out.println("RESIZE X:" + x + " Y:" + y + "\n");
            texture.setScale(new Vector2f(x, y));
        }

        //i don't remember the values there :P
        float scale = inventoryGUI.getInventoryBar().get(1).getScale().x;
        inventoryGUI.getBigInventory().get(0).setScale(new Vector2f(scale * 5 + 0.06f, 0.3f + 0.04f));

        inventoryGUI.resetInventoryGUI();
        itemQuantities.resetQuantityTexts();
    }
}
