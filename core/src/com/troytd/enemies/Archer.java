package com.troytd.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.troytd.game.TroyTD;
import com.troytd.maps.Map;

public class Archer extends Enemy {
    public final static short spawnSpeed = 2000;
    public final static int speed = 50;
    public final static short damage = 20;
    public final static float sizeModifier = 0.1f;
    public final static short range = 160;

    /**
     * @param line       the line where the enemy is located, 0 is the top line, 1 is the middle line, 2 is the bottom line
     * @param game       the game instance
     * @param position   the position of the enemy
     * @param distortion the distortion of the map
     * @param path       the path the enemy will follow, in precalculated points
     * @param map        the map the enemy is on
     */
    public Archer(byte line, TroyTD game, Vector2 position, Vector2 distortion, Vector2[] path, Map map, Stage stage) {
        super(line, game, position, game.assetManager.get("enemies/Archer.png", Texture.class), distortion,
              path, map, stage);
    }
}
