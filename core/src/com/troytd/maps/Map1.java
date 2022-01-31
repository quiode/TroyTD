package com.troytd.maps;

import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.towers.*;
import com.troytd.towers.shots.Shot;
import com.troytd.waves.Debug.ArcherWave;
import com.troytd.waves.Map_1.*;
import com.troytd.waves.Wave;

import java.util.ArrayList;
import java.util.Arrays;

public class Map1 extends Map {
    /**
     * A Map with a texture, it's path, and the places where towers can be placed on the map
     *
     * @param game the game instance
     */
    final static Vector2[] pathPoints;

    static {
        pathPoints = new Vector2[]{
                new Vector2(924, 1536 + 46),
                new Vector2(940, 1536),
                new Vector2(936, 1536 - 166),
                new Vector2(860, 1536 - 292),
                new Vector2(765, 1536 - 339),
                new Vector2(670, 1536 - 386),
                new Vector2(650, 1536 - 564),
                new Vector2(757, 1536 - 605),
                new Vector2(864, 1536 - 645),
                new Vector2(970, 1536 - 686),
                new Vector2(1083, 1536 - 696),
                new Vector2(1196, 1536 - 706),
                new Vector2(1310, 1536 - 717),
                new Vector2(1423, 1536 - 727),
                new Vector2(1536, 1536 - 738),
                new Vector2(1662, 1536 - 910),
                new Vector2(1638, 1536 - 1050),
                new Vector2(1554, 1536 - 1071),
                new Vector2(1467, 1536 - 1093),
                new Vector2(1382, 1536 - 1111),
                new Vector2(1304, 1536 - 1136),
                new Vector2(1204, 1536 - 1147),
                new Vector2(1104, 1536 - 1158),
                new Vector2(1004, 1536 - 1169),
                new Vector2(904, 1536 - 1180),
                new Vector2(804, 1536 - 1191),
                new Vector2(704, 1536 - 1202),
                new Vector2(604, 1536 - 1213),
                new Vector2(504, 1536 - 1224),
                new Vector2(404, 1536 - 1235),
                new Vector2(304, 1536 - 1246),
                new Vector2(204, 1536 - 1257),
                new Vector2(104, 1536 - 1270),
                new Vector2(0, 1536 - 1288),
                new Vector2(-68, 1536 - 1300)
        };
        for (Vector2 point : pathPoints) {
            point.y += 100;
        }
    }

    public Map1(TroyTD game, ArrayList<Shot> shots) {
        super(game, "maps/Map1.png", new Vector2[]{
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
              }, pathPoints, "Map1", new ArrayList<Class<? extends Tower>>(Arrays.asList(Spearthrower.class,
                                                                                         DebugTower3.class,
                                                                                         DebugTower5.class)),
              new ArrayList<Class<? extends Wave>>(Arrays.asList(Wave_1.class, Wave_2.class, Wave_3.class,
                                                                 Wave_4.class, Wave_5.class, Wave_6.class,
                                                                 Wave_7.class, Wave_8.class, Wave_9.class,
                                                                 Wave_10.class, Wave_11.class, Wave_12.class)),
              shots);
    }
}
