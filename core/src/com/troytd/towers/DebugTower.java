package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;


/**
 * Class for debugging towers.
 */
public class DebugTower extends Tower {
    public static final String fileName = "DebugTower.png";

    public DebugTower(TroyTD game, Vector2 position) {
        super(game, position, game.assetManager.get(fileName, Texture.class), fileName);
    }
}
