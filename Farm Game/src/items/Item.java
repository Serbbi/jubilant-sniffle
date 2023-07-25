package items;

import guis.GUITexture;

public interface Item {
    boolean use();
    GUITexture getTexture();
    boolean isStackable();
    String getName();
}
