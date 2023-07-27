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
    public boolean use() {
        MousePicker mousePicker = (MousePicker) options.get("mousePicker");
        Terrain terrain = (Terrain) options.get("terrain");
        Inventory inventory = (Inventory) options.get("inventory");
        Vector3f v = mousePicker.getCurrentTerrainPoint();
        float xCoord = (float) Math.floor(v.x);
        float zCoord = (float) Math.floor(v.z);
        Object object = terrain.getThatObject(xCoord, zCoord + 1);
        if(object instanceof Plant plant) {
            if(plant.canHarvest() && inventory.addItem((Item) plant, 1)) {
                StorageObjects.removePlant(plant);
                terrain.removeObject(xCoord, zCoord + 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public GUITexture getTexture() {
        return icon;
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public String getName() {
        return "Hoe";
    }
}
