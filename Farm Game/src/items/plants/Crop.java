package items.plants;

import entities.Entity;
import guis.GUITexture;
import items.Item;
import org.joml.Vector3f;
import toolbox.PlantModelsStorage;
import toolbox.StorageObjects;
import utils.JSON.JSONObject;
import utils.JSON.JSONable;

public class Crop extends Entity implements Item, Plant, JSONable {
    private int currentStage = 0;
    private int maxStage;
    private GUITexture icon;
    private String name;

    public Crop(int x, int z, String name) {
        super();
        this.name = name;
        getCropData();
        updateEntity(x, z);
        StorageObjects.addPlant(this);
    }

    public Crop(int x, int z, String name, int stage) {
        super();
        this.name = name;
        getCropData();
        currentStage = stage;
        updateEntity(x, z);
    }

    public Crop(String name) {
        super();
        this.name = name;
        icon = PlantModelsStorage.getPlantIcon(name);
        setPosition(new Vector3f(0,0,0));
    }

    public void updateEntity(int x, int z) {
        setModel(PlantModelsStorage.getPlantModel(name, currentStage));
        setPosition(new Vector3f(x+0.5f,0,z+0.5f));
        setRotX(0);
        setRotY(0);
        setRotZ(0);
        setScale(1);
    }

    private void getCropData() {
        icon = PlantModelsStorage.getPlantIcon(name);
        JSONObject json = PlantModelsStorage.getPlantJson(name);
        maxStage = ((Long) json.get("maxStage")).intValue();
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
        if (currentStage < maxStage) {
            setModel(PlantModelsStorage.getPlantModel(name, ++currentStage));
        }
    }

    @Override
    public boolean canHarvest() {
        return currentStage == maxStage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("stage", currentStage);
        json.put("x", getPosition().x);
        json.put("z", getPosition().z);
        return json;
    }
}
