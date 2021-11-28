package com.troytd.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.towers.Tower;

/**
 * A Map with a texture, it's path, and the places where towers can be placed
 */
public class Map {
    protected final TroyTD game;
    protected final String pathName;
    /**
     * the vector by which the map is scaled
     */
    protected Vector2 mapDistortion;
    /**
     * the places where towers can be placed and the tower
     */
    protected towerPlace[] towerPlaces;
    /**
     * the points where the path is
     */
    protected Vector2[] pathPoints;
    /**
     * the path for the enemies to follow
     */
    protected CatmullRomSpline<Vector2> path;
    // Assets
    /**
     * the texture of the map
     */
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
        this.towerPlaces = new towerPlace[towerPlaces.length];
        for (int i = 0; i < towerPlaces.length; i++) {
            this.towerPlaces[i] = new towerPlace(towerPlaces[i], null);
        }
        this.pathPoints = pathPoints;
    }


    /**
     * draws the map
     */
    public void draw(final SpriteBatch batch) {
        if (mapSprite != null) {
            mapSprite.draw(batch);
        }
    }

    /**
     * function that gets called after the assets are loaded
     * sets the path and the map distortion
     */
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

    /**
     * @param position the position of the tower
     * @param tower    the tower to place
     * @return returns 1 if the tower can be placed, 0 if it can't, and -1 if it can be placed but there is already a tower
     */
    public byte placeTower(final Vector2 position, final Tower tower) {
        for (towerPlace towerPlace : towerPlaces) {
            if (towerPlace.place.equals(position)) {
                if (towerPlace.tower == null) {
                    towerPlace.tower = tower;
                    return 1;
                }
                return -1;
            }
        }
        return 0;
    }

    /**
     * deletes all no longer used stuff
     */
    public void dispose() {
        game.assetManager.unload(pathName);
        mapSprite.getTexture().dispose();
    }
}

class towerPlace {
    public final Vector2 place;
    public Tower tower;

    public towerPlace(final Vector2 place, final Tower tower) {
        this.place = place;
        this.tower = tower;
    }
}