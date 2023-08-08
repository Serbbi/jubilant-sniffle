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
            case "carrot seeds" -> itemFactory.createSeeds(slot, quantity, "carrot");
            case "carrot" -> itemFactory.createCrop(slot, quantity, "carrot");
            case "wheat seeds" -> itemFactory.createSeeds(slot, quantity, "wheat");
            case "wheat" -> itemFactory.createCrop(slot, quantity, "wheat");
            case "cabbage seeds" -> itemFactory.createSeeds(slot, quantity, "cabbage");
            case "cabbage" -> itemFactory.createCrop(slot, quantity, "cabbage");
        }
    }
}
