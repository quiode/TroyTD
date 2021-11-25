package com.troytd.maps;

import com.troytd.game.TroyTD;

public class DefaultMap extends Map {
    /**
     * A Map with a texture, it's path, and the places where towers can be placed on the map
     *
     * @param game the game instance
     */
    public DefaultMap(TroyTD game) {
        super(game, "maps/Map-Prototyp-1.png");
    }
}
