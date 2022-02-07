package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.maps.Map;
import com.troytd.towers.shots.single.DebugShot;

import java.util.HashMap;

public class Spearthrower extends Tower {
    public final static TowerTypes type = TowerTypes.SINGLE;
    public static final HashMap<String, Stat> defaultStats = (HashMap<String, Stat>) Tower.defaultStats.clone();
    /**
     * description of the tower
     */
    public final static String description = "A tower which deals high damage to a single target.";

    static {
        defaultStats.put("damage", new Stat<>("damage", 6000));
        defaultStats.put("cost", new Stat<>("cost", 80));
        defaultStats.put("range", new Stat<>("range", 280));
        defaultStats.put("speed", new Stat<>("speed", 700));
        defaultStats.put("maxHP", new Stat<>("maxHP", 100));
        defaultStats.put("atspeed", new Stat<>("atspeed", 50));
    }


    public Spearthrower(TroyTD game, Vector2 position, Vector2 distortion, Map map) {
        super(game, position, game.assetManager.get("towers/Spearthrower.png", Texture.class), "Spearthrower",
              distortion, DebugShot.class, map);
    }
}
