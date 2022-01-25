package com.troytd.waves.Debug;

import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.Archer;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.enemies.enemyAmount;
import com.troytd.maps.Map;
import com.troytd.waves.Wave;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * simple wave that only spawns a single archer
 */
public class ArcherWave extends Wave {
    /**
     * @param game          game instance
     * @param mapDistortion the distortion of the map
     * @param path          the path the enemies traverse
     * @param map           the map
     */
    public ArcherWave(TroyTD game, Vector2 mapDistortion, Vector2[] path, Map map) {
        super(game, mapDistortion, new ArrayList<enemyAmount>(Arrays.asList(new enemyAmount(5, Archer.class))),
              path, map);
    }

    public static Class<? extends Enemy>[] getEnemyList() {
        return new Class[]{Archer.class};
    }
}
