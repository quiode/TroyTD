package com.troytd.maps;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.DebugEnemy;
import com.troytd.game.TroyTD;
import com.troytd.helpers.enemyAmount;

import java.util.ArrayList;
import java.util.Collections;

public class DebugWave extends Wave {
    public static final ArrayList<enemyAmount> enemyList = new ArrayList<enemyAmount>(
            Collections.singleton(new enemyAmount(10,
                                                  DebugEnemy.class)));

    public DebugWave(TroyTD game, Vector2 mapDistortion, CatmullRomSpline<Vector2> path) {
        super(game, mapDistortion, path);
    }
}
