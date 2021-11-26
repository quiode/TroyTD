package com.troytd.maps;

import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

public class DefaultMap extends Map {
    /**
     * A Map with a texture, it's path, and the places where towers can be placed on the map
     *
     * @param game the game instance
     */
    public DefaultMap(TroyTD game) {
        super(game, "maps/Map-Prototyp-1.png", new Vector2[]{new Vector2(146, 170), new Vector2(71, 450),
                new Vector2(102, 690), new Vector2(300, 690), new Vector2(806, 590), new Vector2(1125, 645),
                new Vector2(1180, 390), new Vector2(1090, 35)}, new Vector2[]{});
    }
}
