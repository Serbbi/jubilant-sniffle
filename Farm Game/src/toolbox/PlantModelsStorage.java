package toolbox;

import guis.GUITexture;
import inventory.InventoryStateLoader;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.joml.Vector2f;
import renderEngine.Loader;
import textures.ModelTexture;
import utils.JSON.JSONObject;
import utils.JSON.parser.JSONParser;
import utils.JSON.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlantModelsStorage {
    private static List<String> plantNames = new ArrayList<>();
    private static HashMap<String, List<TexturedModel>> plantModels = new HashMap<>();
    private static HashMap<String, GUITexture> plantIcons = new HashMap<>();
    private static HashMap<String, JSONObject> plantJson = new HashMap<>();

    public static void initializePlantModels(Loader loader) {
        makePlantNamesArray();
        JSONParser parser = new JSONParser();
        for (String plantName : plantNames) {
            addPlantModel(plantName, loader);
            addPlantJson(plantName, parser);
        }
    }

    //plant objs to be named like this: 'plant'Stage'number', ex: carrotStage0
    //plant textures to be named like this: 'plant'Stage'number'Texture, ex: carrotStage0Texture
    public static void addPlantModel(String plantName, Loader loader) {
        int stage = 0;
        List <TexturedModel> plantModels = new ArrayList<>();
        while(true) {
            ModelData plantData = OBJFileLoader.loadOBJ("items/plants/" + plantName + "/" + plantName + "Stage" + stage);
            if(plantData == null) {
                break;
            }
            RawModel plantRaw = loader.loadToVAO(plantData.getVertices(), plantData.getTextureCoords(), plantData.getNormals(), plantData.getIndices());
            ModelTexture plantTexture = new ModelTexture(loader.loadTexture("items/plants/" + plantName + "/" + plantName + "Stage" + stage + "Texture"));
            plantTexture.setReflectivity(0.1f);
            TexturedModel texturedPlant = new TexturedModel(plantRaw, plantTexture);
            plantModels.add(texturedPlant);
            stage++;
        }
        PlantModelsStorage.plantModels.put(plantName, plantModels);

        PlantModelsStorage.plantIcons.put(plantName,
                new GUITexture(loader.loadTexture("items/plants/" + plantName + "/" + plantName + "Icon"), new Vector2f(0,0), new Vector2f(0.1f,0.1f)));
    }

    public static void addPlantJson(String name, JSONParser parser) {
        try (FileReader reader = new FileReader("Farm Game/res/items/plants/" + name + "/" + name + ".json")) {
            plantJson.put(name, (JSONObject) parser.parse(reader));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static TexturedModel getPlantModel(String plantName, int stage) {
        return plantModels.get(plantName).get(stage);
    }

    public static GUITexture getPlantIcon(String plantName) {
        return plantIcons.get(plantName).clone();
    }

    public static JSONObject getPlantJson(String plantName) {
        return plantJson.get(plantName);
    }

    private static void makePlantNamesArray() {
        plantNames.add("carrot");
        plantNames.add("wheat");
        plantNames.add("cabbage");
    }
}
