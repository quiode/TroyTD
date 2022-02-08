package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.maps.Map;
import com.troytd.towers.shots.connecting.DebugConnectingShot;
import com.troytd.towers.shots.single.DebugShot;

import java.util.HashMap;

public class Zeus extends Tower {
    public final static TowerTypes type = TowerTypes.AOE;
    public static final HashMap<String, Stat> defaultStats = (HashMap<String, Stat>) Tower.defaultStats.clone();
    /**
     * description of the tower
     */
    public final static String description = "A tower which attacks multiple enemies at the same time.";

    static {
        defaultStats.put("damage", new Stat<>("damage", 10000));
        defaultStats.put("cost", new Stat<>("cost", 130));
        defaultStats.put("range", new Stat<>("range", 220));
        defaultStats.put("speed", new Stat<>("speed", 100));
        defaultStats.put("maxHP", new Stat<>("maxHP", 100));
        defaultStats.put("range2", new Stat<>("range2", 200));
        defaultStats.put("atspeed", new Stat<>("atspeed", 35));
    }


    public Zeus(TroyTD game, Vector2 position, Vector2 distortion, Map map) {
        super(game, position, game.assetManager.get("towers/Zeus.png", Texture.class), "Zeus", distortion,
              DebugConnectingShot.class, map);
    }
}
