package items;

import guis.GUITexture;
import inventory.Inventory;
import items.plants.Crop;
import items.plants.Seeds;
import org.joml.Vector2f;
import renderEngine.Loader;
import terrain.Terrain;
import toolbox.MousePicker;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {
    private Inventory inventory;
    private Loader loader;
    private Map<String, Object> options;

    public ItemFactory(Terrain terrain, MousePicker mousePicker, Inventory inventory, Loader loader) {
        this.inventory = inventory;
        this.loader = loader;
        options = new HashMap<>();
        options.put("terrain", terrain);
        options.put("mousePicker", mousePicker);
        options.put("inventory", inventory);
        options.put("cropType", "placeHolder");
    }

    public void createShovel(int slot, int quantity) {
        GUITexture icon = new GUITexture(loader.loadTexture("items/tools/shovelIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        Shovel shovel = new Shovel(icon, options);
        boolean status;
        if(slot != 0) {
            status = inventory.addItemAtSlot(shovel, slot, quantity);
        } else {
            status = inventory.addItem(shovel, quantity);
        }
        //TODO: make proper error handling
        if (!status) {
            System.out.println("Inventory is full");
        }
    }

    public void createHoe(int slot, int quantity) {
        GUITexture icon = new GUITexture(loader.loadTexture("items/tools/shovelIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        Hoe hoe = new Hoe(icon, options);
        boolean status;
        if(slot != 0) {
            status = inventory.addItemAtSlot(hoe, slot, quantity);
        } else {
            status = inventory.addItem(hoe, quantity);
        }
        if (!status) {
            System.out.println("Inventory is full");
        }
    }

    public void createSeeds(int slot, int quantity, String cropType) {
        GUITexture icon = new GUITexture(loader.loadTexture("items/plants/" + cropType + "/" + cropType + "SeedsIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        options.put("cropType", cropType);
        Seeds seeds = new Seeds(icon, options);
        boolean status;
        if(slot != 0) {
            status = inventory.addItemAtSlot(seeds, slot, quantity);
        } else {
            status = inventory.addItem(seeds, quantity);
        }
        if (!status) {
            System.out.println("Inventory is full");
        }
    }

    public void createCrop(int slot, int quantity, String cropType) {
        Crop crop = new Crop(cropType);
        boolean status;
        if(slot != 0) {
            status = inventory.addItemAtSlot(crop, slot, quantity);
        } else {
            status = inventory.addItem(crop, quantity);
        }
        if (!status) {
            System.out.println("Inventory is full");
        }
    }
}
