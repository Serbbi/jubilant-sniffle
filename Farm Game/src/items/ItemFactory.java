package items;

import guis.GUITexture;
import inventory.Inventory;
import items.plants.Cabbage;
import items.plants.Carrot;
import items.plants.Wheat;
import items.plants.seeds.CabbageSeeds;
import items.plants.seeds.CarrotSeeds;
import items.plants.seeds.WheatSeeds;
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

    public void createCarrotSeeds(int slot, int quantity) {
        GUITexture icon = new GUITexture(loader.loadTexture("items/plants/carrot/carrotSeedsIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        CarrotSeeds carrotSeeds = new CarrotSeeds(icon, options);
        boolean status;
        if(slot != 0) {
            status = inventory.addItemAtSlot(carrotSeeds, slot, quantity);
        } else {
            status = inventory.addItem(carrotSeeds, quantity);
        }
        if (!status) {
            System.out.println("Inventory is full");
        }
    }

    public void createWheatSeeds(int slot, int quantity) {
        GUITexture icon = new GUITexture(loader.loadTexture("items/plants/wheat/wheatSeedsIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        WheatSeeds wheatSeeds = new WheatSeeds(icon, options);
        boolean status;
        if(slot != 0) {
            status = inventory.addItemAtSlot(wheatSeeds, slot, quantity);
        } else {
            status = inventory.addItem(wheatSeeds, quantity);
        }
        if (!status) {
            System.out.println("Inventory is full");
        }
    }

    public void createCabbageSeeds(int slot, int quantity) {
        GUITexture icon = new GUITexture(loader.loadTexture("items/plants/cabbage/cabbageSeedsIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        CabbageSeeds cabbageSeeds = new CabbageSeeds(icon, options);
        boolean status;
        if(slot != 0) {
            status = inventory.addItemAtSlot(cabbageSeeds, slot, quantity);
        } else {
            status = inventory.addItem(cabbageSeeds, quantity);
        }
        if (!status) {
            System.out.println("Inventory is full");
        }
    }

    public void createCarrot(int slot, int quantity) {
        Carrot carrot = new Carrot();
        boolean status;
        if(slot != 0) {
            status = inventory.addItemAtSlot(carrot, slot, quantity);
        } else {
            status = inventory.addItem(carrot, quantity);
        }
        if (!status) {
            System.out.println("Inventory is full");
        }
    }

    public void createWheat(int slot, int quantity) {
        Wheat wheat = new Wheat();
        boolean status;
        if(slot != 0) {
            status = inventory.addItemAtSlot(wheat, slot, quantity);
        } else {
            status = inventory.addItem(wheat, quantity);
        }
        if (!status) {
            System.out.println("Inventory is full");
        }
    }

    public void createCabbage(int slot, int quantity) {
        Cabbage cabbage = new Cabbage();
        boolean status;
        if(slot != 0) {
            status = inventory.addItemAtSlot(cabbage, slot, quantity);
        } else {
            status = inventory.addItem(cabbage, quantity);
        }
        if (!status) {
            System.out.println("Inventory is full");
        }
    }
}
