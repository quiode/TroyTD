package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.maps.Map;
import com.troytd.towers.shots.single.DebugShot;

import java.util.HashMap;


/**
 * Class for debugging towers.
 */
public class DebugTower extends Tower {
    public final static TowerTypes type = TowerTypes.SINGLE;
    public static final HashMap<String, Stat> defaultStats = (HashMap<String, Stat>) Tower.defaultStats.clone();

    public DebugTower(TroyTD game, Vector2 position, Vector2 distortion, Map map) {
        super(game, position, game.assetManager.get("towers/DebugTower.png", Texture.class), "DebugTower", distortion,
              DebugShot.class, map);
    }
}
