package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.towers.shots.single.DebugShot;

import java.util.HashMap;

public class DebugTower2 extends Tower {
    public final static TowerTypes type = TowerTypes.SINGLE;
    public static final HashMap<String, Stat> defaultStats = (HashMap<String, Stat>) Tower.defaultStats.clone();

    static {
        defaultStats.put("damage", new Stat<>("damage", 26));
        defaultStats.put("cost", new Stat<>("cost", 200));
    }


    public DebugTower2(TroyTD game, Vector2 position, Vector2 distortion) {
        super(game, position, game.assetManager.get("towers/DebugTower2.png", Texture.class), "DebugTower2", distortion,
              DebugShot.class);
    }
}
