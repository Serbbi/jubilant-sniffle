package items.plants.seeds;

import guis.GUITexture;
import items.Item;
import items.plants.Carrot;
import items.plants.Wheat;
import kotlin.Pair;
import org.joml.Vector3f;
import terrain.Terrain;
import toolbox.MousePicker;

import java.util.Map;
import java.util.Objects;

public class WheatSeeds implements Item {
    private final GUITexture icon;
    private final Map<String, Object> options;

    public WheatSeeds(GUITexture icon, Map<String, Object> options) {
        this.icon = icon;
        this.options = options;
    }
    @Override
    public boolean use() {
        Terrain terrain = (Terrain) options.get("terrain");
        MousePicker mousePicker = (MousePicker) options.get("mousePicker");
        Vector3f c = mousePicker.getCurrentTerrainPoint();
        //TODO: NULL VALUE ERROR
        float xCoord = (float) Math.floor(c.x);
        float zCoord = (float) Math.floor(c.z);
        if(!terrain.isTileOccupied(xCoord, zCoord)) {
            if(Objects.equals(terrain.getTileNames().get(new Pair<>(xCoord, zCoord + 1)), "mud")) {
                Wheat wheat = new Wheat((int) xCoord, (int) zCoord);
                terrain.placeObject(xCoord,zCoord, wheat);
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
        return true;
    }

    @Override
    public String getName() {
        return "Wheat Seeds";
    }
}
