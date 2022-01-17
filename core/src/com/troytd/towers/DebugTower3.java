package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.towers.shots.single.DebugShot;


/**
 * Class for debugging towers.
 */
public class DebugTower3 extends Tower {
    public final static int range = 234;

    public DebugTower3(TroyTD game, Vector2 position, Vector2 distortion) {
        super(game, position, game.assetManager.get("towers/DebugTower3.png", Texture.class), "DebugTower3",
              TowerTypes.AOE,
              distortion, DebugShot.class);
    }
}
