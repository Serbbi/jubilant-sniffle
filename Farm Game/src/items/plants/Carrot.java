package items.plants;

import entities.Entity;
import guis.GUITexture;
import items.Item;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.joml.Vector2f;
import org.joml.Vector3f;
import renderEngine.Loader;
import textures.ModelTexture;
import toolbox.StorageObjects;

import java.util.ArrayList;
import java.util.List;

public class Carrot extends Entity implements Item, Plant {
    private GUITexture icon;
    private List<TexturedModel> models;
    private int currentStage = 0;
    private static final int MAX_STAGE = 4;
    private final Loader loader;

    public Carrot(Loader loader, int x, int z) {
        super();
        this.loader = loader;
        setUp();
        updateEntity(x, z);
        StorageObjects.addPlant(this);
    }

    //TODO: MAKE A CLASS WHERE ALL THE MODELS AND TEXTURES ARE LOADED TO IMPROVE PERFORMANCE
    public void setUp() {
        models = new ArrayList<>();
        ModelData carotdata = OBJFileLoader.loadOBJ("items/plants/carrot/carrotStage0");
        RawModel carotraw = loader.loadToVAO(carotdata.getVertices(), carotdata.getTextureCoords(), carotdata.getNormals(), carotdata.getIndices());
        ModelTexture cartex = new ModelTexture(loader.loadTexture("items/plants/carrot/seedTexture"));
        TexturedModel carrotTex = new TexturedModel(carotraw, cartex);
        models.add(carrotTex);
        carotdata = OBJFileLoader.loadOBJ("items/plants/carrot/carrotStage1");
        carotraw = loader.loadToVAO(carotdata.getVertices(), carotdata.getTextureCoords(), carotdata.getNormals(), carotdata.getIndices());
        cartex = new ModelTexture(loader.loadTexture("items/plants/carrot/smallCarrotTexture"));
        carrotTex = new TexturedModel(carotraw, cartex);
        models.add(carrotTex);
        carotdata = OBJFileLoader.loadOBJ("items/plants/carrot/carrotStage2");
        carotraw = loader.loadToVAO(carotdata.getVertices(), carotdata.getTextureCoords(), carotdata.getNormals(), carotdata.getIndices());
        cartex = new ModelTexture(loader.loadTexture("items/plants/carrot/smallCarrotTexture"));
        carrotTex = new TexturedModel(carotraw, cartex);
        models.add(carrotTex);
        carotdata = OBJFileLoader.loadOBJ("items/plants/carrot/carrotStage3");
        carotraw = loader.loadToVAO(carotdata.getVertices(), carotdata.getTextureCoords(), carotdata.getNormals(), carotdata.getIndices());
        cartex = new ModelTexture(loader.loadTexture("items/plants/carrot/smallCarrotTexture"));
        carrotTex = new TexturedModel(carotraw, cartex);
        models.add(carrotTex);
        carotdata = OBJFileLoader.loadOBJ("items/plants/carrot/carrotStage4");
        carotraw = loader.loadToVAO(carotdata.getVertices(), carotdata.getTextureCoords(), carotdata.getNormals(), carotdata.getIndices());
        cartex = new ModelTexture(loader.loadTexture("items/plants/carrot/CarrotTexture"));
        carrotTex = new TexturedModel(carotraw, cartex);

        icon = new GUITexture(loader.loadTexture("items/plants/carrot/carrotIcon"), new Vector2f(0,0), new Vector2f(0.1f,0.1f));

        models.add(carrotTex);
    }

    public void updateEntity(int x, int z) {
        setModel(models.get(currentStage));
        setPosition(new Vector3f(x+0.5f,0,z+0.5f));
        setRotX(0);
        setRotY(0);
        setRotZ(0);
        setScale(1);
    }

    @Override
    public void grow() {
        if (currentStage < MAX_STAGE) {
            setModel(models.get(++currentStage));
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
