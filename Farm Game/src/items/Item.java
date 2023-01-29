package items;

import guis.GUITexture;
import textures.ModelTexture;

public interface Item {
    void use();
    GUITexture getTexture();
}
