package inventory;

import guis.GUIElement;
import guis.GUITexture;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.joml.Vector2f;
import renderEngine.Loader;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;

public class InventoryGUI {
    private Loader loader;
    private List<GUIElement> guiElements;
    private List<GUITexture> guiIcons;
    private ModelTexture unSelected;
    private ModelTexture selected;
    private List<Vector2f> slotCoordinates;

    public InventoryGUI(Loader loader) {
        guiElements = new ArrayList<>();
        guiIcons = new ArrayList<>();
        slotCoordinates = new ArrayList<>();
        setSlotCoordinates();
        this.loader = loader;
    }

    public void setInventoryGUI() {
        selected = new ModelTexture(loader.loadTexture("inventory/white"));

        ModelData invData = OBJFileLoader.loadOBJ("inventory/inventoryBack");
        RawModel invRaw = loader.loadToVAO(invData.getVertices(), invData.getTextureCoords(), invData.getIndices());
        ModelTexture invTexture = new ModelTexture(loader.loadTexture("inventory/lightYellow"));
        TexturedModel invTextured = new TexturedModel(invRaw, invTexture);
        GUIElement inventoryBack = new GUIElement(new Vector2f(0,-0.88f), invTextured, new Vector2f(0.1f,0.1f));

        ModelData slot = OBJFileLoader.loadOBJ("inventory/inventoryFront");
        RawModel slotRaw = loader.loadToVAO(slot.getVertices(), slot.getTextureCoords(), slot.getIndices());
        unSelected = new ModelTexture(loader.loadTexture("inventory/blueGrey"));
        TexturedModel slot5Textured = new TexturedModel(slotRaw, unSelected);
        TexturedModel slot1Textured = new TexturedModel(slotRaw, unSelected);
        TexturedModel slot2Textured = new TexturedModel(slotRaw, unSelected);
        TexturedModel slot3Textured = new TexturedModel(slotRaw, unSelected);
        TexturedModel slot4Textured = new TexturedModel(slotRaw, unSelected);
        GUIElement invFront3 = new GUIElement(new Vector2f(0,0), slot3Textured, new Vector2f(0.1f,0.1f));
        GUIElement invFront1 = new GUIElement(new Vector2f(-0.38f,-0.88f), slot1Textured, new Vector2f(0.1f,0.1f));
        GUIElement invFront2 = new GUIElement(new Vector2f(-0.19f,-0.88f), slot2Textured, new Vector2f(0.1f,0.1f));
        GUIElement invFront4 = new GUIElement(new Vector2f(0.19f,-0.88f), slot4Textured, new Vector2f(0.1f,0.1f));
        GUIElement invFront5 = new GUIElement(new Vector2f(0.38f,-0.88f), slot5Textured, new Vector2f(0.1f,0.1f));

        guiElements.add(inventoryBack);
        guiElements.add(invFront1);
        guiElements.add(invFront2);
        guiElements.add(invFront3);
        guiElements.add(invFront4);
        guiElements.add(invFront5);
    }

    public List<GUIElement> getGuiElements() {
        return guiElements;
    }

    public void addIcon(GUITexture icon) {
        guiIcons.add(icon);
    }

    public List<GUITexture> getGuiIcons() {
        return guiIcons;
    }

    public void unselect() {
        for (int i = 1; i < guiElements.size(); i++) {
            guiElements.get(i).getTexturedModel().setTexture(unSelected);
        }
    }

    public void select(int n) {
        unselect();
        guiElements.get(n).getTexturedModel().setTexture(selected);
    }

    private void setSlotCoordinates() {
        slotCoordinates.add(new Vector2f(-0.38f,-0.88f));
        slotCoordinates.add(new Vector2f(-0.19f,-0.88f));
        slotCoordinates.add(new Vector2f(0,-0.88f));
        slotCoordinates.add(new Vector2f(0.19f,-0.88f));
        slotCoordinates.add(new Vector2f(0.38f,-0.88f));
    }

    public List<Vector2f> getSlotCoordinates() {
        return slotCoordinates;
    }
}
