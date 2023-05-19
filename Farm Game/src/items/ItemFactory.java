package items;

import guis.GUITexture;
import inventory.Inventory;
import items.plants.seeds.CarrotSeeds;
import org.joml.Vector2f;
import renderEngine.Loader;
import terrain.Terrain;
import toolbox.MousePicker;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {
    private Terrain terrain;
    private MousePicker mousePicker;
    private Inventory inventory;
    private Loader loader;

    public ItemFactory(Terrain terrain, MousePicker mousePicker, Inventory inventory, Loader loader) {
        this.terrain = terrain;
        this.mousePicker = mousePicker;
        this.inventory = inventory;
        this.loader = loader;
    }

    public void createShovel() {
        Map<String, Object> options = new HashMap<>();
        options.put("terrain", terrain);
        options.put("mousePicker", mousePicker);
        GUITexture icon = new GUITexture(loader.loadTexture("items/tools/shovelIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        Shovel shovel = new Shovel(icon, options);
        inventory.addItemAtFirstFreeSpot(shovel);
    }

    public void createHoe() {
        Map<String, Object> options = new HashMap<>();
        options.put("terrain", terrain);
        options.put("mousePicker", mousePicker);
        GUITexture icon = new GUITexture(loader.loadTexture("items/tools/shovelIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        Hoe hoe = new Hoe(icon, options);
        inventory.addItemAtFirstFreeSpot(hoe);
    }

    public void createCarrotSeeds() {
        Map<String, Object> options = new HashMap<>();
        options.put("terrain", terrain);
        options.put("mousePicker", mousePicker);
        options.put("loader", loader);
        GUITexture icon = new GUITexture(loader.loadTexture("items/plants/carrot/carrotSeedsIcon"), new Vector2f(0,0), new Vector2f(0.2f,0.2f));
        CarrotSeeds carrotSeeds = new CarrotSeeds(icon, options);
        inventory.addItemAtFirstFreeSpot(carrotSeeds);
    }
}
