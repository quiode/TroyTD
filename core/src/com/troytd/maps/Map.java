package com.troytd.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

public class Map {
    final protected TroyTD game;

    protected Texture texture;

    protected CatmullRomSpline<Vector2> path;
    protected Vector2[] dataSet;

    public Map(TroyTD game) {
        this.game = game;
    }

    public void dispose() {
        texture.dispose();
    }
}
