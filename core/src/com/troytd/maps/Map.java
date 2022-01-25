package com.troytd.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Loadable;
import com.troytd.screens.GameScreen;
import com.troytd.screens.LoadingScreen;
import com.troytd.towers.Tower;
import com.troytd.towers.TowerTypes;
import com.troytd.towers.shots.Shot;
import com.troytd.towers.units.Unit;
import com.troytd.waves.Wave;

import java.util.ArrayList;

/**
 * A Map with a texture, it's path, and the places where towers can be placed
 */
public abstract class Map implements Loadable {
    /**
     * towers that can be used with this map
     */
    public final ArrayList<Class<? extends Tower>> towers;
    /**
     * The name of the map
     */
    public final String name;
    /**
     * All waives of the map
     */
    protected final ArrayList<Class<? extends Wave>> waves;
    /**
     * game instance
     */
    protected final TroyTD game;
    protected final String pathName;
    /**
     * calculated path points with a precision of 100000 (100000 points for the line)
     */
    protected final Vector2[] pathPointsCalculated = new Vector2[100000];
    /**
     * list of all singleShots
     */
    private final ArrayList<Shot> shots;
    /**
     * list of all tower units
     */
    private final ArrayList<Unit> units = new ArrayList<>();
    /**
     * true if game is won
     */
    public boolean won = false;
    /**
     * true if game is lost
     */
    public boolean lost = false;
    /**
     * the vector by which the map is scaled
     */
    public Vector2 mapDistortion;
    /**
     * instance of the current wave
     */
    protected Wave currentWave;
    /**
     * the index of the current wave in the waves list
     */
    protected short currentWaveIndex;
    /**
     * the places where towers can be placed and the tower
     */
    protected TowerPlace[] towerPlaces;
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
     * @param name        the name of the map
     * @param towers      the towers that can be used with this map
     * @param waves       the waves of the map
     */
    public Map(final TroyTD game, final String texturePath, final Vector2[] towerPlaces, final Vector2[] pathPoints,
               final String name, final ArrayList<Class<? extends Tower>> towers,
               ArrayList<Class<? extends Wave>> waves, ArrayList<Shot> shots) {
        // Load assets
        game.assetManager.load(texturePath, Texture.class);

        // load enemy textures
        for (Class<? extends Wave> wave : waves) {
            try {
                for (Class<? extends Enemy> enemy : (Class<? extends Enemy>[]) ClassReflection.getMethod(wave,
                                                                                                         "getEnemyList",
                                                                                                         null)
                        .invoke(null, null)) {
                    game.assetManager.load("enemies/" + enemy.getSimpleName() + ".png", Texture.class);
                }
            } catch (ReflectionException e) {
                e.printStackTrace();
            }
        }

        // load tower textures and shot textures
        for (Class<? extends Tower> tower : towers) {
            game.assetManager.load("towers/" + tower.getSimpleName() + ".png", Texture.class);

            TowerTypes towerType;
            try {
                towerType = (TowerTypes) ClassReflection.getField(tower, "type").get(null);
            } catch (ReflectionException e) {
                e.printStackTrace();
                towerType = Tower.type;
            }

            switch (towerType) {
                case MELEE:
                    game.assetManager.load("units/" + tower.getSimpleName() + "Unit" + ".png", Texture.class);
                    break;
                case SINGLE:
                case AOE:
                    game.assetManager.load("shots/" + tower.getSimpleName() + "Shot" + ".png", Texture.class);
                    break;
                case NONE:
                    break;
            }

        }

        // set values
        this.game = game;
        this.pathName = texturePath;
        this.towerPlaces = new TowerPlace[towerPlaces.length];
        for (int i = 0; i < towerPlaces.length; i++) {
            this.towerPlaces[i] = new TowerPlace(towerPlaces[i], null);
        }
        this.name = name;
        this.towers = towers;
        this.waves = waves;
        this.shots = shots;

        // calculated path points
        final int precision = pathPointsCalculated.length;
        CatmullRomSpline<Vector2> myCatmull = new CatmullRomSpline<Vector2>(pathPoints, false);
        for (int i = 0; i < precision; ++i) {
            pathPointsCalculated[i] = new Vector2();
            myCatmull.valueAt(pathPointsCalculated[i], ((float) i) / ((float) precision - 1));
        }
    }


    /**
     * draws the map, the dowers, and the enemies
     */
    public void draw(final SpriteBatch batch, final GameScreen gameScreen, float delta, Stage stage) {
        if (!game.assetManager.isFinished()) game.setScreen(new LoadingScreen(game, gameScreen, this));

        if (mapSprite != null) {
            mapSprite.draw(batch);
        }

        updateEnemies(stage, gameScreen);
        drawEnemies();
        if (currentWave != null) updateTowers(currentWave.getEnemies());
        drawTowers();
        if (currentWave != null) updateUnits(gameScreen);
        drawUnits();
        updateShots(delta, gameScreen);
        drawShots();
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
        for (Vector2 pathPoint : pathPointsCalculated) {
            pathPoint.x *= mapDistortion.x;
            pathPoint.y *= mapDistortion.y;
        }

        // recalculate the tower places
        for (TowerPlace place : towerPlaces) {
            place.place.x *= mapDistortion.x;
            place.place.y *= mapDistortion.y;
        }

        // set first wave
        currentWaveIndex = 0;
        try {
            currentWave = (Wave) ClassReflection.getConstructor(waves.get(currentWaveIndex), TroyTD.class,
                                                                Vector2.class, Vector2[].class, Map.class)
                    .newInstance(game, mapDistortion, pathPointsCalculated, this);
        } catch (ReflectionException e) {
            e.printStackTrace();
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
                towerPlaceRectangle.x += game.settingPreference.getInteger("width") / 2f;
                towerPlaceRectangle.y += game.settingPreference.getInteger("height") / 2f;
            } else {
                towerPlaceRectangle = new Rectangle(towerPlace.place.x - Tower.getSize(game) / 2f,
                                                    towerPlace.place.y - Tower.getSize(game) / 2f, Tower.getSize(game),
                                                    Tower.getSize(game));
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

    private void drawEnemies() {
        if (currentWave != null) {
            currentWave.draw();
        }
    }

    private void updateTowers(ArrayList<Enemy> enemies) {
        for (TowerPlace towerPlace : towerPlaces) {
            if (towerPlace.getTower() != null) {
                towerPlace.getTower().update(enemies, shots, units);
            }
        }
    }

    private void updateEnemies(Stage stage, GameScreen gameScreen) {
        if (currentWave != null) {
            if (currentWave.isFinished()) {
                if (++currentWaveIndex < waves.size()) {
                    try {
                        currentWave = (Wave) ClassReflection.getConstructor(waves.get(currentWaveIndex), TroyTD.class,
                                                                            Vector2.class, Vector2[].class)
                                .newInstance(game, mapDistortion, pathPointsCalculated);
                    } catch (ReflectionException e) {
                        e.printStackTrace();
                    }
                } else {
                    currentWave = null;
                    won = true;
                }
            } else {
                currentWave.update(stage, gameScreen, units);
            }
        }
    }

    private void updateShots(float delta, GameScreen gameScreen) {
        for (int i = 0; i < shots.size(); i++) {
            if (currentWave != null) {
                shots.get(i).update(delta, shots, currentWave.getEnemies(), gameScreen);
            } else {
                shots.clear();
            }
        }
    }

    private void drawShots() {
        for (Shot shot : shots) {
            shot.draw();
        }
    }

    public int maxWaves() {
        return waves.size();
    }

    public short currentWave() {
        return currentWaveIndex;
    }

    public void drawUnits() {
        for (Unit unit : units) {
            unit.draw();
        }
    }

    public void updateUnits(GameScreen gameScreen) {
        for (Unit unit : units) {
            unit.update(units, currentWave.getEnemies(), gameScreen);
        }
    }
}

