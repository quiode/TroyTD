package com.troytd.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.troytd.game.TroyTD;
import com.troytd.maps.Map;

public class Spear_Thrower extends Enemy {
    public final static short spawnSpeed = 2000;
    public final static int speed = 60;
    public final static short damage = 30;
    public final static float sizeModifier = 0.1f;
    public final static short range = 120;

    /**
     * @param line       the line where the enemy is located, 0 is the top line, 1 is the middle line, 2 is the bottom line
     * @param game       the game instance
     * @param position   the position of the enemy
     * @param distortion
     * @param path       the path the enemy will follow, in precalculated points
     * @param map
     */
    public Spear_Thrower(byte line, TroyTD game, Vector2 position, Vector2 distortion, Vector2[] path, Map map,
                         Stage stage) {
        super(line, game, position, game.assetManager.get("Spear_Thrower.png", Texture.class), distortion,
              path, map, stage);
    }
}
