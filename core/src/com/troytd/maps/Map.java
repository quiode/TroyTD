package com.troytd.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

/**
 * A Map with a texture, it's path, and the places where towers can be placed
 */
public class Map {
    final protected TroyTD game;

    protected Texture texture;

    protected CatmullRomSpline<Vector2> path;
    protected Vector2[] dataSet;

    protected Circle[] towerPlaces;

    /**
     * A Map with a texture, it's path, and the places where towers can be placed on the map
     *
     * @param game the game instance
     */
    public Map(TroyTD game) {
        this.game = game;
    }

    /**
     * deletes all no longer used stuff
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * draws the map
     */
    public void draw() {
    }
}
