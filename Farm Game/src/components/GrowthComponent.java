package components;

import items.plants.Plant;
import toolbox.StorageObjects;

public class GrowthComponent implements Runnable{
    private static final int MAX = 100;
    private static final int MIN = 1;

    public GrowthComponent() {
    }

    @Override
    public void run() {
        int n;
        while(true) {
            for (Plant plant: StorageObjects.getPlants()) {
                n = (int) (Math.random()*(MAX-MIN+1)+MIN);
                switch (plant.getName()) {
                    case "Carrot":
                        if(n > 10) {plant.grow();}
                        break;
                    case "Wheat":
                        if(n > 35) {plant.grow();}
                        break;
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
