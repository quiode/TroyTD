package com.troytd.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.maps.Map;

import java.util.HashMap;

/**
 * Class for debugging enemies
 */
public class DebugEnemy extends Enemy {
    public final static HashMap<String, Stat> defaultStats = (HashMap<String, Stat>) Enemy.defaultStats.clone();

    static {
        defaultStats.put("spawnSpeed", new Stat<>("spawnSpeed", 250));
        defaultStats.put("maxHP", new Stat<>("maxHP", 500));
        defaultStats.put("worth", new Stat<>("worth", 10));
        defaultStats.put("speed", new Stat<>("speed", 25));
    }

    public DebugEnemy(byte line, final TroyTD game, Vector2 position, final Vector2[] path,
                      Map map) {
        super(line, game, position, game.assetManager.get("enemies/DebugEnemy.png", Texture.class), path,
              map);
    }
}
