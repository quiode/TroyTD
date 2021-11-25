package com.troytd.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

/**
 * A Map with a texture, it's path, and the places where towers can be placed
 */
public class Map {
    final protected TroyTD game;
    protected final String pathName;
    protected CatmullRomSpline<Vector2> path;
    protected Vector2[] dataSet;
    protected Circle[] towerPlaces;
    // Assets
    protected Sprite mapSprite = null;

    /**
     * A Map with a texture, it's path, and the places where towers can be placed on the map
     *
     * @param game        the game instance
     * @param texturePath the path to the texture
     */
    public Map(final TroyTD game, final String texturePath) {
        // Load assets
        game.assetManager.load(texturePath, Texture.class);

        // set values
        this.game = game;
        this.pathName = texturePath;
    }

    /**
     * deletes all no longer used stuff
     */
    public void dispose() {
        game.assetManager.unload(pathName);
        mapSprite.getTexture().dispose();
    }

    /**
     * draws the map
     */
    public void draw(final SpriteBatch batch) {
        if (mapSprite != null) {
            mapSprite.draw(batch);
        }
    }

    public void afterLoad() {
        mapSprite = new Sprite(game.assetManager.get(pathName, Texture.class));
    }
}
