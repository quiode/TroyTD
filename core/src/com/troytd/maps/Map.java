package com.troytd.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Loadable;
import com.troytd.screens.GameScreen;
import com.troytd.screens.LoadingScreen;
import com.troytd.towers.Tower;

/**
 * A Map with a texture, it's path, and the places where towers can be placed
 */
public abstract class Map implements Loadable {
    /**
     * enemies that spawn with this map
     */
    public final Class<? extends Enemy>[] enemies;
    /**
     * towers that can be used with this map
     */
    public final Class<? extends Tower>[] towers;
    /**
     * the amount of rounds needed to win
     */
    public final byte maxRounds;
    /**
     * The name of the map
     */
    public final String name;
    /**
     * game instance
     */
    protected final TroyTD game;
    protected final String pathName;
    /**
     * the vector by which the map is scaled
     */
    public Vector2 mapDistortion;
    /**
     * the places where towers can be placed and the tower
     */
    protected TowerPlace[] towerPlaces;
    /**
     * the points where the path is
     */
    protected Vector2[] pathPoints;
    // Assets
    /**
     * the path for the enemies to follow
     */
    protected CatmullRomSpline<Vector2> path;
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
     * @param maxRounds   the amount of rounds needed to win
     * @param name        the name of the map
     * @param enemies     the enemies that spawn with this map
     * @param towers      the towers that can be used with this map
     */
    public Map(final TroyTD game, final String texturePath, final Vector2[] towerPlaces, final Vector2[] pathPoints,
               byte maxRounds, final String name, final Class<? extends Enemy>[] enemies,
               final Class<? extends Tower>[] towers) {
        // Load assets
        game.assetManager.load(texturePath, Texture.class);

        for (Class<? extends Enemy> enemy : enemies) {
            game.assetManager.load("enemies/" + enemy.getSimpleName() + ".png", Texture.class);
        }

        for (Class<? extends Tower> tower : towers) {
            game.assetManager.load("towers/" + tower.getSimpleName() + ".png", Texture.class);
        }

        // set values
        this.game = game;
        this.pathName = texturePath;
        this.towerPlaces = new TowerPlace[towerPlaces.length];
        this.maxRounds = maxRounds;
        for (int i = 0; i < towerPlaces.length; i++) {
            this.towerPlaces[i] = new TowerPlace(towerPlaces[i], null);
        }
        this.pathPoints = pathPoints;
        this.name = name;
        this.enemies = enemies;
        this.towers = towers;
    }


    /**
     * draws the map, the dowers, and the enemies
     */
    public void draw(final SpriteBatch batch, final GameScreen gameScreen) {
        if (!game.assetManager.isFinished()) game.setScreen(new LoadingScreen(game, gameScreen, this));

        if (mapSprite != null) {
            mapSprite.draw(batch);
        }

        drawTowers();
        drawEnemies();
    }

    /**
     * function that gets called after the assets are loaded
     * sets the path and the map distortion
     */
    public void afterLoad() {
        mapSprite = new Sprite(game.assetManager.get(pathName, Texture.class));
        mapSprite.setSize(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"));
        mapSprite.setPosition(-(game.settingPreference.getInteger("width") / 2f),
                              -(game.settingPreference.getInteger("height") / 2f));
        mapDistortion = new Vector2(
                (float) game.settingPreference.getInteger("width") / mapSprite.getTexture().getWidth(),
                (float) game.settingPreference.getInteger("height") / mapSprite.getTexture().getHeight());

        // map has a new size so points on map have to be recalculated
        for (Vector2 pathPoint : pathPoints) {
            pathPoint.x *= mapDistortion.x;
            pathPoint.y *= mapDistortion.y;
        }

        this.path = new CatmullRomSpline<Vector2>(pathPoints, false);

        // recalculate the tower places
        for (TowerPlace place : towerPlaces) {
            place.place.x *= mapDistortion.x;
            place.place.y *= mapDistortion.y;
        }
    }

    /**
     * @param position the position of the tower
     * @param tower    the tower to place
     * @return returns 1 if the tower can be placed, 0 if it can't, and -1 if it can be placed but there is already a tower
     */
    public byte placeTower(final Vector2 position, final Tower tower) {
        for (TowerPlace towerPlace : towerPlaces) {
            if (towerPlace.place.equals(position)) {
                if (towerPlace.getTower() == null) {
                    towerPlace.setTower(tower, game);
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

    /**
     * Checks if a towerPosition (a position where a tower can be placed) is clicked, and if so, returns the object.
     *
     * @param clickPosition the position of the click
     * @return returns the tower place that is at the position of the click or null if there is no tower place at
     * the position
     */
    public TowerPlace checkTowerClick(final Vector2 clickPosition) {
        final byte clickPositionRectangleSize = 10;
        Rectangle clickPositionRectangle = new Rectangle(clickPosition.x - clickPositionRectangleSize / 2f,
                                                         clickPosition.y - clickPositionRectangleSize / 2f,
                                                         clickPositionRectangleSize, clickPositionRectangleSize);

        for (TowerPlace towerPlace : towerPlaces) {
            Rectangle towerPlaceRectangle;
            if (towerPlace.getTower() != null) {
                towerPlaceRectangle = towerPlace.getTower().getRect();
            } else {
                towerPlaceRectangle = new Rectangle(towerPlace.place.x - Tower.size / 2f,
                                                    towerPlace.place.y - Tower.size / 2f, Tower.size, Tower.size);
            }
            if (clickPositionRectangle.overlaps(towerPlaceRectangle)) {
                return towerPlace;
            }
        }
        return null;
    }

    private void drawTowers() {
        for (TowerPlace towerPlace : towerPlaces) {
            if (towerPlace.getTower() != null) {
                towerPlace.getTower().draw();
            }
        }
    }

    private void drawEnemies() {}
}

