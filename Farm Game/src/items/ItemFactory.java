package items;

import guis.GUITexture;
import inventory.Inventory;
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

    public void createShovel() {
        GUITexture icon = new GUITexture(loader.loadTexture("items/tools/shovelIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        Shovel shovel = new Shovel(icon, options);
        boolean status = inventory.addItem(shovel, 1);
        //TODO: make proper error handling
        if (!status) {
            System.out.println("Inventory is full");
        }
    }

    public void createHoe() {
        GUITexture icon = new GUITexture(loader.loadTexture("items/tools/shovelIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        Hoe hoe = new Hoe(icon, options);
        boolean status = inventory.addItem(hoe, 1);
        if (!status) {
            System.out.println("Inventory is full");
        }
    }

    public void createCarrotSeeds() {
        GUITexture icon = new GUITexture(loader.loadTexture("items/plants/carrot/carrotSeedsIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        CarrotSeeds carrotSeeds = new CarrotSeeds(icon, options);
        boolean status = inventory.addItem(carrotSeeds, 20);//random given quantity
        if (!status) {
            System.out.println("Inventory is full");
        }
    }

    public void createWheatSeeds() {
        GUITexture icon = new GUITexture(loader.loadTexture("items/plants/wheat/wheatSeedsIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        WheatSeeds wheatSeeds = new WheatSeeds(icon, options);
        boolean status = inventory.addItem(wheatSeeds, 20);//random given quantity
        if (!status) {
            System.out.println("Inventory is full");
        }
    }

    public void createCabbageSeeds() {
        GUITexture icon = new GUITexture(loader.loadTexture("items/plants/cabbage/cabbageSeedsIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        CabbageSeeds cabbageSeeds = new CabbageSeeds(icon, options);
        boolean status = inventory.addItem(cabbageSeeds, 20);//random given quantity
        if (!status) {
            System.out.println("Inventory is full");
        }
    }
}
