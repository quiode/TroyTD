package com.troytd.maps;

import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.towers.Spearthrower;
import com.troytd.towers.Tower;
import com.troytd.waves.Wave;

import java.util.ArrayList;
import java.util.Arrays;

public class Map1 extends Map {
    /**
     * A Map with a texture, it's path, and the places where towers can be placed on the map
     *
     * @param game the game instance
     */
    public Map1(TroyTD game) {
        super(game, "map/Map1.jpg", new Vector2[]{
                      new Vector2(560, 1536 - 1446),
                      new Vector2(552, 1536 - 1000),
                      new Vector2(834, 1536 - 972),
                      new Vector2(1126, 1536 - 938),
                      new Vector2(1366, 1536 - 938),
                      new Vector2(1286, 1536 - 1370),
                      new Vector2(1810, 1536 - 1240),
                      new Vector2(1134, 1536 - 498),
                      new Vector2(900, 1536 - 486),
                      new Vector2(376, 1536 - 616),
                      new Vector2(680, 1536 - 124)
              }, new Vector2[]{
                      new Vector2(924, 1536 + 46),
                      new Vector2(940, 1536),
                      new Vector2(936, 1536 - 166),
                      new Vector2(860, 1536 - 292),
                      new Vector2(670, 1536 - 386),
                      new Vector2(650, 1536 - 564),
                      new Vector2(970, 1536 - 686),
                      new Vector2(1536, 1536 - 738),
                      new Vector2(1662, 1536 - 910),
                      new Vector2(1638, 1536 - 1050),
                      new Vector2(1304, 1536 - 1136),
                      new Vector2(0, 1536 - 1288),
                      new Vector2(-68, 1536 - 1300)
              }, (byte) 5, "Map1", new ArrayList<Class<? extends Enemy>>(),
              new ArrayList<Class<? extends Tower>>(Arrays.asList(Spearthrower.class)),
              new ArrayList<Class<? extends Wave>>());
    }
}
