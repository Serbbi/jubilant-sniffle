package items;

import guis.GUITexture;
import org.joml.Vector3f;
import terrain.Terrain;
import toolbox.MousePicker;
import utils.JSON.JSONObject;
import utils.JSON.JSONable;

import java.util.Map;

public class Shovel implements Item, JSONable {
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
        //idk why but it's off by 1
        terrain.changeTile(v.x, v.z + 1, "mud");
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "shovel");
        return json;
    }
}
