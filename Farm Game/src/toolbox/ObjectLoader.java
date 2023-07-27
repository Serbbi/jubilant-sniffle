package toolbox;

import items.plants.Cabbage;
import items.plants.Carrot;
import items.plants.Wheat;

public class ObjectLoader {

    public static Object getObject(String name, int x, int z, int stage) {
        return switch (name) {
            case "carrot" -> new Carrot(x, z, stage);
            case "wheat" -> new Wheat(x, z, stage);
            case "cabbage" -> new Cabbage(x, z, stage);
            default -> null;
        };
    }
}
