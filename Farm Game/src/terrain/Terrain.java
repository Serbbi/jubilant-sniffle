package terrain;

import entities.Entity;
import kotlin.Pair;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;
import renderEngine.Loader;
import textures.ModelTexture;

import java.util.*;

public class Terrain {
    private Map<Pair<Float, Float>, Object> placedObjects;
    private Map<Pair<Float, Float>, Entity> tiles;
    private Map<Pair<Float, Float>, String> tileNames;
    private static final int MAP_SIZE = 50;
    private static Vector2f limits;
    private final Loader loader;
    private final Map<String,TexturedModel> tileModels;

    public Terrain(Loader loader) {
        this.loader = loader;
        tiles = new HashMap<>();
        tileModels = new HashMap<>();
        tileNames = new HashMap<>();
        placedObjects = new HashMap<>();
        loadModels();
        buildTerrain();
    }

    public void loadModels() {
        ModelData pathData = OBJFileLoader.loadOBJ("environment/pathTile");
        RawModel pathRaw = loader.loadToVAO(pathData.getVertices(), pathData.getTextureCoords(), pathData.getNormals(), pathData.getIndices());
        ModelTexture pathTexture = new ModelTexture(loader.loadTexture("environment/grey"));
        TexturedModel path = new TexturedModel(pathRaw, pathTexture);

        ModelData mudData = OBJFileLoader.loadOBJ("environment/mudTile");
        RawModel mudRaw = loader.loadToVAO(mudData.getVertices(), mudData.getTextureCoords(), mudData.getNormals(), mudData.getIndices());
        ModelTexture mudTexture = new ModelTexture(loader.loadTexture("environment/mud"));
        TexturedModel mud = new TexturedModel(mudRaw, mudTexture);

        ModelData grassData = OBJFileLoader.loadOBJ("environment/grassTile");
        RawModel grassRaw = loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices());
        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("environment/grass"));
        TexturedModel grass = new TexturedModel(grassRaw, grassTexture);

        tileModels.put("mud", mud);
        tileModels.put("grass", grass);
        tileModels.put("path", path);
    }

    private void buildTerrain() {
        TexturedModel grass = tileModels.get("grass");

        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                Pair<Float,Float> pair = new Pair<>((float)(i),(float)(j));
                tiles.put(pair, new Entity(grass, new Vector3f((float) (i), 0f, (float) (j)),0,0,0,1));
                tileNames.put(pair, "grass");
            }
        }
        limits = new Vector2f(0, (float) (MAP_SIZE));
    }

    public void changeTile(float x, float z, String tile) {
        TexturedModel newTile = tileModels.get(tile);
        Pair<Float,Float> pair = new Pair<>(x = Math.floor(x),z = Math.floor(z)+1);//IDK why +1, but oh well
        if (tiles.containsKey(pair)) {
            tiles.put(pair, new Entity(newTile, new Vector3f(x,0,z), 0,0,0,1));
            tileNames.put(pair, tile);
        }
    }

    public Map<Pair<Float, Float>, Entity> getTiles() {
        return tiles;
    }

    public Map<Pair<Float, Float>, String> getTileNames() {
        return tileNames;
    }

    public static Vector2f getLimits() {
        return limits;
    }

    public void placeObject(float x, float z, Object object) {
        Pair<Float, Float> pair = new Pair<>(x,z+1);
        placedObjects.put(pair, object);
    }

    public void removeObject(float x, float z) {
        Pair<Float, Float> pair = new Pair<>(x,z+1);
        placedObjects.remove(pair);
    }

    public boolean isTileOccupied(float x, float z) {
        Pair<Float, Float> pair = new Pair<>(x,z+1);
        return placedObjects.containsKey(pair);
    }

    public Object getThatObject(float x, float z) {
        Pair<Float, Float> pair = new Pair<>(x,z+1);
        return placedObjects.get(pair);
    }
}
