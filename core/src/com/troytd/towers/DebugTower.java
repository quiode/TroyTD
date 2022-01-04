package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.shots.DebugShot;


/**
 * Class for debugging towers.
 */
public class DebugTower extends Tower {
    public static final String name = "DebugTower";

    public DebugTower(TroyTD game, Vector2 position, Vector2 distortion) {
        super(game, position, game.assetManager.get("towers/" + name + ".png", Texture.class), name, TowerTypes.MELEE,
              distortion, DebugShot.class);
    }
}
