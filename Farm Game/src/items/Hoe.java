package items;

import guis.GUITexture;
import inventory.Inventory;
import items.plants.Carrot;
import items.plants.Plant;
import org.joml.Vector3f;
import terrain.Terrain;
import toolbox.MousePicker;
import toolbox.StorageObjects;

import java.util.Map;

public class Hoe implements Item{
    private final GUITexture icon;
    private final Map<String, Object> options;

    public Hoe(GUITexture icon, Map<String, Object> options) {
        this.icon = icon;
        this.options = options;
    }

    @Override
    public void use() {
        MousePicker mousePicker = (MousePicker) options.get("mousePicker");
        Terrain terrain = (Terrain) options.get("terrain");
        Inventory inventory = (Inventory) options.get("inventory");
        Vector3f v = mousePicker.getCurrentTerrainPoint();
        float xCoord = (float) Math.floor(v.x);
        float zCoord = (float) Math.floor(v.z);
        Object object = terrain.getThatObject(xCoord, zCoord);
        if(object instanceof Plant plant) {
            if(plant.canHarvest() && inventory.addItemAtFirstFreeSpot((Item) plant)) {
                StorageObjects.removePlant(plant);
                terrain.removeObject(xCoord, zCoord);
//                inventory.addItemAtFirstFreeSpot((Item) plant);
            }
        }
    }

    @Override
    public GUITexture getTexture() {
        return icon;
    }
}
