package com.troytd.waves;

import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.DebugEnemy;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.enemies.enemyAmount;
import com.troytd.maps.Map;

import java.util.ArrayList;
import java.util.Collections;

public class DebugWave extends Wave {
    /**
     * @param game          game instance
     * @param mapDistortion the distortion of the map
     * @param path          the path of the wave
     */
    public DebugWave(TroyTD game, Vector2 mapDistortion, Vector2[] path, Map map) {
        super(game, mapDistortion,
              new ArrayList<enemyAmount>(Collections.singleton(new enemyAmount(5, DebugEnemy.class))), path, map);
    }

    public static Class<? extends Enemy>[] getEnemyList() {
        return new Class[]{DebugEnemy.class};
    }
}
