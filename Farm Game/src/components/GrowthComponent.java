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
                    case "carrot" -> {
                        if (n > 30) {
                            plant.grow();
                        }
                    }
                    case "wheat" -> {
                        if (n > 35) {
                            plant.grow();
                        }
                    }
                    case "cabbage" -> {
                        if (n > 40) {
                            plant.grow();
                        }
                    }
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
