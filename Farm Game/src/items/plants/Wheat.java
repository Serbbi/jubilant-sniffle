package items.plants;

import entities.Entity;
import guis.GUITexture;
import items.Item;
import org.joml.Vector3f;
import toolbox.PlantModelsStorage;
import toolbox.StorageObjects;
import utils.JSON.JSONObject;
import utils.JSON.JSONable;

public class Wheat extends Entity implements Item, Plant, JSONable {
    private GUITexture icon;
    private int currentStage = 0;
    private static final int MAX_STAGE = 4;

    public Wheat(int x, int z) {
        super();
        icon = PlantModelsStorage.getPlantIcon("wheat");
        updateEntity(x, z);
        StorageObjects.addPlant(this);
    }

    public Wheat(int x, int z, int stage) {
        super();
        icon = PlantModelsStorage.getPlantIcon("wheat");
        currentStage = stage;
        updateEntity(x, z);
    }

    public void updateEntity(int x, int z) {
        setModel(PlantModelsStorage.getPlantModel("wheat", currentStage));
        setPosition(new Vector3f(x+0.5f,0,z+0.5f));
        setRotX(0);
        setRotY(0);
        setRotZ(0);
        setScale(1);
    }

    @Override
    public boolean use() {
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
    public void grow() {
        if (currentStage < MAX_STAGE) {
            setModel(PlantModelsStorage.getPlantModel("wheat", ++currentStage));
        }
    }

    @Override
    public boolean canHarvest() {
        return currentStage == MAX_STAGE;
    }

    @Override
    public String getName() {
        return "Wheat";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "wheat");
        json.put("stage", currentStage);
        json.put("x", getPosition().x);
        json.put("z", getPosition().z);
        return json;
    }
}
