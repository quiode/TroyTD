package com.troytd.towers.shots;

import com.troytd.enemies.Enemy;
import com.troytd.screens.GameScreen;

import java.util.ArrayList;

public interface Shot {
    void update(float delta, final ArrayList<Shot> shots, final ArrayList<Enemy> enemies,
                GameScreen gameScreen);

    void draw();

    ShotType getShotType();
}
