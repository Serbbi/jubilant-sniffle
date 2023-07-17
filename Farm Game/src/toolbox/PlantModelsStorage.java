package toolbox;

import guis.GUIManager;
import guis.GUITexture;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.joml.Vector2f;
import renderEngine.Loader;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlantModelsStorage {
    private static HashMap<String, List<TexturedModel>> plantModels = new HashMap<>();
    private static HashMap<String, GUITexture> plantIcons = new HashMap<>();

    public static void initializePlantModels(Loader loader) {
        addPlantModel("carrot", loader);
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
            TexturedModel texturedPlant = new TexturedModel(plantRaw, plantTexture);
            plantModels.add(texturedPlant);
            stage++;
        }
        PlantModelsStorage.plantModels.put(plantName, plantModels);

        PlantModelsStorage.plantIcons.put(plantName,
                new GUITexture(loader.loadTexture("items/plants/" + plantName + "/" + plantName + "Icon"), new Vector2f(0,0), new Vector2f(0.1f,0.1f)));
    }

    public static TexturedModel getPlantModel(String plantName, int stage) {
        return plantModels.get(plantName).get(stage);
    }

    public static GUITexture getPlantIcon(String plantName) {
        return plantIcons.get(plantName).clone();
    }
}
