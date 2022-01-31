package com.troytd.waves.Map_1;

import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.Enemy;
import com.troytd.enemies.Swordsman;
import com.troytd.enemies.enemyAmount;
import com.troytd.game.TroyTD;
import com.troytd.maps.Map;
import com.troytd.waves.Wave;

import java.util.ArrayList;
import java.util.Arrays;

public class Wave_1 extends Wave {
    /**
     * @param game          game instance
     * @param mapDistortion the distortion of the map
     * @param path          the path the enemies traverse
     * @param map           the map
     */
    public Wave_1(TroyTD game, Vector2 mapDistortion, Vector2[] path, Map map) {
        super(game, mapDistortion, new ArrayList<enemyAmount>(Arrays.asList(new enemyAmount(10, Swordsman.class))),
              path, map,
              0);

    }

    public static Class<? extends Enemy>[] getEnemyList() {return new Class[]{Swordsman.class};}

}
