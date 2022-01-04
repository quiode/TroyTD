package com.troytd.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.maps.Map;

public class Poseidon extends Enemy{    public final static short spawnSpeed = 2000;
    public final static short hp = 600;
    public final static int speed = 50;
    public final static short damage = 50;
    public final static float sizeModifier = 0.1f;
    public final static float atspeed = 0.8f;
    public final static short range = 40;

    /**
     * @param line       the line where the enemy is located, 0 is the top line, 1 is the middle line, 2 is the bottom line
     * @param game       the game instance
     * @param position   the position of the enemy
     * @param texture
     * @param distortion
     * @param path       the path the enemy will follow, in precalculated points
     * @param map
     */
    public Poseidon(byte line, TroyTD game, Vector2 position, Texture texture, Vector2 distortion, Vector2[] path,
                    Map map) {
        super(line, game, position, new Texture("towers/range.jpg"), distortion, path, map);
    }
}
