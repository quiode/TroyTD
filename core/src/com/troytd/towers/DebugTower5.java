package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.towers.shots.single.DebugShot;

import java.util.HashMap;


/**
 * Class for debugging towers.
 */
public class DebugTower5 extends Tower {
    public final static TowerTypes type = TowerTypes.SINGLE;
    public static final HashMap<String, Stat> defaultStats = (HashMap<String, Stat>) Tower.defaultStats.clone();

    static {
        defaultStats.put("damage", new Stat<>("damage", 53));
        defaultStats.put("cost", new Stat<>("cost", 24));
        defaultStats.put("range", new Stat<>("range", 45));
        defaultStats.put("speed", new Stat<>("speed", 45));
        defaultStats.put("maxHP", new Stat<>("maxHP", 234));
    }


    public DebugTower5(TroyTD game, Vector2 position, Vector2 distortion) {
        super(game, position, game.assetManager.get("towers/DebugTower5.png", Texture.class), "DebugTower5", distortion,
              DebugShot.class);
    }
}
