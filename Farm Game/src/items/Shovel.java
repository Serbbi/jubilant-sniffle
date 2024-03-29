package items;

import guis.GUITexture;
import org.joml.Vector3f;
import terrain.Terrain;
import toolbox.MousePicker;

import java.util.Map;

public class Shovel implements Item{
    private final GUITexture icon;
    private final Map<String, Object> options;

    public Shovel(GUITexture icon, Map<String, Object> options) {
        this.icon = icon;
        this.options = options;
    }

    @Override
    public boolean use() {
        Terrain terrain = (Terrain) options.get("terrain");
        MousePicker mousePicker = (MousePicker) options.get("mousePicker");
        Vector3f v = mousePicker.getCurrentTerrainPoint();
        terrain.changeTile(v.x, v.z, "mud");
        return true;
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
        return "Shovel";
    }
}
