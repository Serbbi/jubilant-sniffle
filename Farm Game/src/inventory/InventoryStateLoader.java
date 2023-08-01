package inventory;

import items.ItemFactory;
import utils.JSON.JSONArray;
import utils.JSON.JSONObject;

public class InventoryStateLoader {
    public static void loadFromJson(JSONObject inventory, ItemFactory itemFactory) {
        JSONArray itemsJson = (JSONArray) inventory.get("items");
        for (Object itemJson: itemsJson) {
            JSONObject item = (JSONObject) itemJson;
            int slot = ((Long) item.get("slot")).intValue();
            JSONObject itemObject = (JSONObject) item.get("item");
            String itemName = (String) itemObject.get("name");
            int quantity = ((Long) item.get("quantity")).intValue();
            selectItemByName(itemName, slot, quantity, itemFactory);
        }
    }

    private static void selectItemByName(String itemName, int slot, int quantity, ItemFactory itemFactory) {
        switch (itemName) {
            case "shovel" -> itemFactory.createShovel(slot, quantity);
            case "hoe" -> itemFactory.createHoe(slot, quantity);
            case "carrot seeds" -> itemFactory.createCarrotSeeds(slot, quantity);
            case "carrot" -> itemFactory.createCarrot(slot, quantity);
            case "wheat seeds" -> itemFactory.createWheatSeeds(slot, quantity);
            case "wheat" -> itemFactory.createWheat(slot, quantity);
            case "cabbage seeds" -> itemFactory.createCabbageSeeds(slot, quantity);
            case "cabbage" -> itemFactory.createCabbage(slot, quantity);
        }
    }
}
