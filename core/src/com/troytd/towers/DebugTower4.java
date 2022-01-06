package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.shots.DebugShot;


/**
 * Class for debugging towers.
 */
public class DebugTower4 extends Tower {
    public final static int cost = 45;
    public final static int speed = 5;
    public final static int atspeed = 2000;

    public DebugTower4(TroyTD game, Vector2 position, Vector2 distortion) {
        super(game, position, game.assetManager.get("towers/DebugTower4.png", Texture.class), "DebugTower4",
              TowerTypes.MELEE, distortion, DebugShot.class);
    }
}
