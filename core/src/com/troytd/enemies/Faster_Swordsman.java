package com.troytd.enemies;

import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.maps.Map;

public class Faster_Swordsman extends Swordsman {
    /**
     * @param line     the line where the enemy is located, 0 is the top line, 1 is the middle line, 2 is the bottom line
     * @param game     the game instance
     * @param position the position of the enemy
     * @param path     the path the enemy will follow, in precalculated points
     * @param map
     */
    public Faster_Swordsman(byte line, TroyTD game, Vector2 position, Vector2[] path, Map map) {
        super(line, game, position, path, map);
    }

    static {
        defaultStats.put("spawnSpeed", new Stat<>("spawnSpeed", 20));
    }
}
