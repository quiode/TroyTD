package com.troytd.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

/**
 * A Map with a texture, it's path, and the places where towers can be placed
 */
public class Map {
    protected final TroyTD game;
    protected final String pathName;
    protected final Vector2[] towerPlaces;
    /**
     * the vector by which the map is scaled
     */
    public Vector2 mapDistortion;
    protected Vector2[] pathPoints;
    protected CatmullRomSpline<Vector2> path;
    // Assets
    protected Sprite mapSprite = null;

    /**
     * A Map with a texture, it's path, and the places where towers can be placed on the map
     *
     * @param game        the game instance
     * @param texturePath the path to the texture
     * @param towerPlaces the places where towers can be placed
     * @param pathPoints  the points that make up the path
     */
    public Map(final TroyTD game, final String texturePath, final Vector2[] towerPlaces, final Vector2[] pathPoints) {
        // Load assets
        game.assetManager.load(texturePath, Texture.class);

        // set values
        this.game = game;
        this.pathName = texturePath;
        this.towerPlaces = towerPlaces;
        this.pathPoints = pathPoints;
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
        mapSprite.setSize(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"));
        mapDistortion = new Vector2((float) game.settingPreference.getInteger(
                "width") / mapSprite.getTexture().getWidth(), (float) game.settingPreference.getInteger("height") /
                mapSprite.getTexture().getHeight());

        // map has a new size so points on map have to be recalculated
        for (Vector2 pathPoint : pathPoints) {
            pathPoint.x *= mapDistortion.x;
            pathPoint.y *= mapDistortion.y;
        }
        this.path = new CatmullRomSpline<Vector2>(pathPoints, false);
    }
}
