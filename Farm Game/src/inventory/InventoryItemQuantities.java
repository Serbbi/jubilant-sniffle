package inventory;

import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import kotlin.Pair;
import org.joml.Vector2f;
import renderEngine.DisplayManager;
import renderEngine.Loader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static inventory.Inventory.isOpen;

public class InventoryItemQuantities {
    private ArrayList<Integer> itemQuantities;
    private List<GUIText> quantityTexts;
    private FontType font;
    private InventoryGUI gui;

    public InventoryItemQuantities(InventoryGUI gui, Loader loader) {
        this.gui = gui;
        itemQuantities = new ArrayList<>(Collections.nCopies(Inventory.TOTAL_SPOTS + 1, 0));
        font = new FontType(loader.loadTexture("fonts/tahoma"), new File("Farm Game/res/fonts/tahoma.fnt"));
        setQuantityTexts();
        hideBigInventoryQuantities();
    }

    public void setQuantityTexts() {
        quantityTexts = new ArrayList<>();
        quantityTexts.add(null);
        for (int i = 1; i <= Inventory.TOTAL_SPOTS; i++) {
            quantityTexts.add(new GUIText(String.valueOf(itemQuantities.get(i)), 1, font, transformPosition(gui.getSlots().get(i).getTextureCoords()), 1f, false));
        }
    }

    public void resetQuantityTexts() {
        for (int i = 1; i <= Inventory.TOTAL_SPOTS; i++) {
            quantityTexts.get(i).setPosition(transformPosition(gui.getSlots().get(i).getTextureCoords()));
            quantityTexts.get(i).updateFont();
        }
    }

    private Vector2f transformPosition(Pair<Pair<Float, Float>, Pair<Float, Float>> textureCoords) {
        float x = textureCoords.getFirst().getFirst();
        float y = textureCoords.getSecond().getSecond();
        int width = DisplayManager.getWidth();
        int height = DisplayManager.getHeight();
        return new Vector2f(x / width, y / height);
    }

    public int get(int spot) {
        return itemQuantities.get(spot);
    }

    public void set(int spot, int quantity) {
        itemQuantities.set(spot, quantity);
        quantityTexts.get(spot).setTextString(String.valueOf(quantity));
        if (!isOpen) {
            hideBigInventoryQuantities();
        }
        hideSpotsUnder1Quantity();
    }

    public void hideBigInventoryQuantities() {
        for (int i = 6; i <= Inventory.TOTAL_SPOTS; i++) {
            quantityTexts.get(i).setTextString("");
        }
        hideSpotsUnder1Quantity();
    }

    public void showBigInventoryQuantities() {
        for (int i = 6; i <= Inventory.TOTAL_SPOTS; i++) {
            quantityTexts.get(i).setTextString(String.valueOf(itemQuantities.get(i)));
        }
        hideSpotsUnder1Quantity();
    }

    public void hideSpotsUnder1Quantity() {
        for (int i = 1; i <= Inventory.TOTAL_SPOTS; i++) {
            if(itemQuantities.get(i) <= 1) {
                quantityTexts.get(i).setTextString("");
            }
        }
    }
}
