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

    static {
        defaultStats.put("damage", new Stat<>("damage", 40));
        defaultStats.put("cost", new Stat<>("cost", 80));
        defaultStats.put("range", new Stat<>("range", 160));
        defaultStats.put("speed", new Stat<>("speed", 100));
        defaultStats.put("maxHP", new Stat<>("maxHP", 100));
    }


    public Spearthrower(TroyTD game, Vector2 position, Vector2 distortion, Map map) {
        super(game, position, game.assetManager.get("towers/Spearthrower.png", Texture.class), "Spearthrower",
              distortion, DebugShot.class, map);
    }
}
