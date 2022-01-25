package com.troytd.waves.Map_1;

import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.*;
import com.troytd.game.TroyTD;
import com.troytd.maps.Map;
import com.troytd.waves.Wave;

import java.util.ArrayList;
import java.util.Arrays;

public class Wave_10 extends Wave {
    /**
     * @param game          game instance
     * @param mapDistortion the distortion of the map
     * @param path          the path the enemies traverse
     * @param map           the map
     */
    public Wave_10(TroyTD game, Vector2 mapDistortion, ArrayList<enemyAmount> enemyList, Vector2[] path, Map map,
                   int pauseTime) {
        super(game, mapDistortion, new ArrayList<enemyAmount>(Arrays.asList(new enemyAmount(10, Swordsman.class),
                                                                            new enemyAmount(8, Archer.class),
                                                                            new enemyAmount(4, Horseman.class))),
              path, map,
              15000);

    }

    public static Class<? extends Enemy>[] getEnemyList() {return new Class[]{Swordsman.class, Archer.class,
                                                                              Horseman.class};}

}
