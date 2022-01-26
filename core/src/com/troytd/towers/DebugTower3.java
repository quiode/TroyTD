package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.maps.Map;
import com.troytd.towers.shots.connecting.DebugConnectingShot;

import java.util.HashMap;


/**
 * Class for debugging towers.
 */
public class DebugTower3 extends Tower {
    public final static TowerTypes type = TowerTypes.AOE;
    public static final HashMap<String, Stat> defaultStats = (HashMap<String, Stat>) Tower.defaultStats.clone();

    static {
        defaultStats.put("range", new Stat<>("range", 234));
    }


    public DebugTower3(TroyTD game, Vector2 position, Vector2 distortion, Map map) {
        super(game, position, game.assetManager.get("towers/DebugTower3.png", Texture.class), "DebugTower3", distortion,
              DebugConnectingShot.class, map);
    }
}
