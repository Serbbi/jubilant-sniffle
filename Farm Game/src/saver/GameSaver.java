package saver;

import entities.Camera;
import inventory.Inventory;
import terrain.Terrain;
import toolbox.StorageObjects;
import utils.JSON.JSONObject;
import utils.JSON.parser.JSONParser;
import utils.JSON.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameSaver {
    private Terrain terrain;
    private Camera camera;
    private Inventory inventory;

    public GameSaver(Terrain terrain, Camera camera, Inventory inventory) {
        this.terrain = terrain;
        this.camera = camera;
        this.inventory = inventory;
    }

    public void saveGame() {
        try (FileWriter file = new FileWriter("Farm Game/saves/save.json")) {
            JSONObject json = new JSONObject();
            json.put("terrain", terrain.toJson());
            json.put("storageObjects", StorageObjects.toJson());
            json.put("camera", camera.toJson());
            json.put("inventory", inventory.toJson());

            file.write(json.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("Farm Game/saves/save.json")) {
            JSONObject json = (JSONObject) jsonParser.parse(reader);
            terrain.loadFromJson((JSONObject) json.get("terrain"));
            StorageObjects.loadFromJson((JSONObject) json.get("storageObjects"), terrain);
//            camera.loadFromJson((JSON Object) json.get("camera"));
//            inventory.loadFromJson((JSONObject) json.get("inventory"));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
