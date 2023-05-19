package guis;

import inventory.InventoryGUI;
import org.joml.Vector2f;
import renderEngine.DisplayManager;

import java.util.ArrayList;
import java.util.List;

public class GUIManager {
    private List<GUITexture> guiTextures;
    private List<GUIElement> guiElements;
    private InventoryGUI inventoryGUI;

    public GUIManager(InventoryGUI inventoryGUI) {
        this.inventoryGUI = inventoryGUI;
        guiTextures = new ArrayList<>();
        guiElements = new ArrayList<>();
    }

    public void addGUI(GUITexture guiTexture) {
        guiTextures.add(guiTexture);
    }

    public void addGUIList(List<GUITexture> list) {
        guiTextures.addAll(list);
    }

    public void addGUIElementList(List<GUIElement> list) {
        guiElements.addAll(list);
    }

    public void resizeGUIs() {
        for (GUITexture texture: guiTextures) {
            if(texture.getRatio() != 1) {
                continue;
            }
            float y = texture.getScale().y;
            float x = y * DisplayManager.getHeight() / DisplayManager.getWidth();
//            System.out.println("Height: " + DisplayManager.getHeight() + "\n");
//            System.out.println("Width: " + DisplayManager.getWidth() + "\n");
//            System.out.println("RESIZE X:" + x + " Y:" + y + "\n");
            texture.setScale(new Vector2f(x, y));
        }

        float scale = inventoryGUI.getInventoryTextures().get(0).getScale().x;
        inventoryGUI.getBigInventoryTextures().get(0).setScale(new Vector2f(scale * 5 + 0.06f, 0.3f + 0.04f));

//        for (GUIElement element: guiElements) {
//            float y = element.getScale().y;
//            float x = y * DisplayManager.getHeight() / DisplayManager.getWidth();
//            element.setScale(new Vector2f(x, y));
//        }
    }
}
