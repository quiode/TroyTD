package com.troytd.shots;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.towers.Tower;

public class DebugShot extends Shot {
    public DebugShot(TroyTD game, Tower tower) {
        super(game, tower, game.assetManager.get("shots/DebugShot.png", Texture.class));
    }
}
