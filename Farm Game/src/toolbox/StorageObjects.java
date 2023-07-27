package toolbox;

import items.plants.Plant;
import terrain.Terrain;
import utils.JSON.JSONArray;
import utils.JSON.JSONObject;
import utils.JSON.JSONable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StorageObjects {
    private static List<Plant> plants = new ArrayList<>();

    public static void addPlant(Plant plant) {
        plants.add(plant);
    }

    public static List<Plant> getPlants() {
        return plants;
    }

    public static void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public static JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonPlants = new JSONArray();
        for (Plant plant : plants) {
            jsonPlants.add(((JSONable) plant).toJson());
        }
        json.put("plants", jsonPlants);
        return json;
    }

    public static void loadFromJson(JSONObject json, Terrain terrain) {
        JSONArray jsonPlants = (JSONArray) json.get("plants");
        jsonPlants.forEach(jsonPlant -> {
            JSONObject jsonPlantObject = (JSONObject) jsonPlant;
            String name = (String) jsonPlantObject.get("name");
            double x = (double) jsonPlantObject.get("x");
            double z = (double) jsonPlantObject.get("z");
            long stage = (long) jsonPlantObject.get("stage");
            Plant plant = (Plant) ObjectLoader.getObject(name, (int) Math.floor(x), (int) Math.floor(z), (int) stage);
            plants.add(plant);
            //terrain offset is 0.5f
            terrain.placeObject((float) x - 0.5f, (float) z + 0.5f, plant);
        });
    }
}
