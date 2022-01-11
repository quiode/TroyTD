package com.troytd.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.maps.Map;

/**
 * Class for debugging enemies
 */
public class DebugEnemy extends Enemy {
    public final static short spawnSpeed = 5000;
    public final static int worth = 10;

    public DebugEnemy(byte line, final TroyTD game, Vector2 position, final Vector2 distortion, final Vector2[] path,
                      Map map) {
        super(line, game, position, game.assetManager.get("enemies/DebugEnemy.png", Texture.class), distortion, path,
              map);
    }
}
