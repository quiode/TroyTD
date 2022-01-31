package com.troytd.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.maps.Map;

import java.util.HashMap;

public class Trojan_Horse extends Enemy {
    public final static int speed = 30;
    public final static short damage = 0;
    public final static float sizeModifier = 0.1f;
    public final static short range = 0;
    public final static HashMap<String, Stat> defaultStats = (HashMap<String, Stat>) Enemy.defaultStats.clone();

    static {
        defaultStats.put("speed", new Stat<>("speed", 30));
        defaultStats.put("damage", new Stat<>("damage", 0));
        defaultStats.put("range", new Stat<>("range", 0));
        defaultStats.put("worth", new Stat<>("worth", 60));
    }

    /**
     * @param line       the line where the enemy is located, 0 is the top line, 1 is the middle line, 2 is the bottom line
     * @param game       the game instance
     * @param position   the position of the enemy
     * @param path       the path the enemy will follow, in precalculated points
     * @param map
     */
    public Trojan_Horse(byte line, TroyTD game, Vector2 position, Vector2[] path, Map map) {
        super(line, game, position, game.assetManager.get("enemies/Trojan_Horse.png", Texture.class), path,
              map);
    }
}
