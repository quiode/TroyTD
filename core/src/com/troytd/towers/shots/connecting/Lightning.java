package com.troytd.towers.shots.connecting;

import com.badlogic.gdx.graphics.Texture;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.screens.GameScreen;
import com.troytd.towers.shots.Shot;

import java.util.ArrayList;

public class Lightning extends ConnectingShot {
    public Lightning(TroyTD game) {
        super(game, game.assetManager.get("shots/Lightning.png", Texture.class));
    }

    @Override
    public void update(float delta, ArrayList<Shot> shots, ArrayList<Enemy> enemies, GameScreen gameScreen) {

    }

    @Override
    public void draw() {

    }
}
