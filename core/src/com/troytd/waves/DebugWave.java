package com.troytd.waves;

import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.DebugEnemy;
import com.troytd.game.TroyTD;
import com.troytd.helpers.enemyAmount;

import java.util.ArrayList;
import java.util.Collections;

public class DebugWave extends Wave {
    /**
     * @param game          game instance
     * @param mapDistortion the distortion of the map
     * @param path          the path of the wave
     */
    public DebugWave(TroyTD game, Vector2 mapDistortion, Vector2[] path) {
        super(game, mapDistortion,
              new ArrayList<enemyAmount>(Collections.singleton(new enemyAmount(1, DebugEnemy.class))), path);
    }
}
