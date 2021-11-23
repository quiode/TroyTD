package com.troytd.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

public class Map {
    protected Texture texture;
    protected CatmullRomSpline<Vector2> path;
    protected Vector2[] dataSet;

    public void dispose() {
        texture.dispose();
    }
}
