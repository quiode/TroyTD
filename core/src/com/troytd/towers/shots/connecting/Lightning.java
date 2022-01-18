package com.troytd.towers.shots.connecting;

import com.badlogic.gdx.graphics.Texture;
import com.troytd.game.TroyTD;

public class Lightning extends ConnectingShot {
    public Lightning(TroyTD game) {
        super(game, game.assetManager.get("shots/Lightning.png", Texture.class));
    }
}
