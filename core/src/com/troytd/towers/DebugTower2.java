package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

public class DebugTower2 extends Tower {
    public static final String name = "DebugTower2";
    public final static int cost = 200;

    public DebugTower2(TroyTD game, Vector2 position,
                       Vector2 distortion) {
        super(game, position, game.assetManager.get("towers/" + name + ".png", Texture.class), name,
              TowerTypes.SINGLE_TARGET,
              distortion);
    }
}
