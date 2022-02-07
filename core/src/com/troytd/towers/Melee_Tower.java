package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.maps.Map;
import com.troytd.towers.shots.single.DebugShot;
import com.troytd.towers.units.DebugUnit;

import java.util.HashMap;

public class Melee_Tower extends Tower {
    public final static TowerTypes type = TowerTypes.MELEE;
    public static final HashMap<String, Stat> defaultStats = (HashMap<String, Stat>) Tower.defaultStats.clone();
    /**
     * description of the tower
     */
    public final static String description = "A tower which spawns units that stop the enemies.";

    static {
        defaultStats.put("damage", new Stat<>("damage", 20));
        defaultStats.put("cost", new Stat<>("cost", 100));
        defaultStats.put("range", new Stat<>("range", 240));
        defaultStats.put("speed", new Stat<>("speed", 100));
        defaultStats.put("maxHP", new Stat<>("maxHP", 2500));
        defaultStats.put("range2", new Stat("range2", 10));
        defaultStats.put("atspeed", new Stat("atspeed", 15));
    }


    public Melee_Tower(TroyTD game, Vector2 position, Vector2 distortion, Map map) {
        super(game, position, game.assetManager.get("towers/Melee_Tower.png", Texture.class), "Swordsmen", distortion,
              null,DebugUnit.class, map);
    }


}

