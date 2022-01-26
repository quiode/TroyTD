package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.maps.Map;
import com.troytd.towers.units.DebugUnit;

import java.util.HashMap;


/**
 * Class for debugging towers.
 */
public class DebugTower5 extends Tower {
    public final static TowerTypes type = TowerTypes.MELEE;
    public static final HashMap<String, Stat> defaultStats = (HashMap<String, Stat>) Tower.defaultStats.clone();

    static {
        defaultStats.put("range2", new Stat("range2", 10));
        defaultStats.put("damage", new Stat("damage", 5));
        defaultStats.put("maxHP", new Stat("maxHP", 500));
        defaultStats.put("speed", new Stat("speed", 100));
    }

    public DebugTower5(TroyTD game, Vector2 position, Vector2 distortion, Map map) {
        super(game, position, game.assetManager.get("towers/DebugTower5.png", Texture.class), "DebugTower5", distortion,
              null, DebugUnit.class, map);
    }
}
