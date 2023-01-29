package toolbox;

import items.plants.Plant;

import java.util.ArrayList;
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
}
