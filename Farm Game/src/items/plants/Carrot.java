package items.plants;

import entities.Entity;
import guis.GUITexture;
import items.Item;
import org.joml.Vector3f;
import toolbox.PlantModelsStorage;
import toolbox.StorageObjects;

public class Carrot extends Entity implements Item, Plant {
    private GUITexture icon;
    private int currentStage = 0;
    private static final int MAX_STAGE = 4;

    public Carrot(int x, int z) {
        super();
        icon = PlantModelsStorage.getPlantIcon("carrot");
        updateEntity(x, z);
        StorageObjects.addPlant(this);
    }

    public void updateEntity(int x, int z) {
        setModel(PlantModelsStorage.getPlantModel("carrot", currentStage));
        setPosition(new Vector3f(x+0.5f,0,z+0.5f));
        setRotX(0);
        setRotY(0);
        setRotZ(0);
        setScale(1);
    }

    @Override
    public void grow() {
        if (currentStage < MAX_STAGE) {
            setModel(PlantModelsStorage.getPlantModel("carrot", ++currentStage));
        }
    }

    @Override
    public boolean canHarvest() {
        return currentStage == MAX_STAGE;
    }

    @Override
    public String getName() {
        return "Carrot";
    }

    @Override
    public void use() {}

    @Override
    public GUITexture getTexture() {
        return icon;
    }

}
