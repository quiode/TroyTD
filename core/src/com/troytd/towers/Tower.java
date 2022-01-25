package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.towers.shots.Shot;
import com.troytd.towers.shots.connecting.ConnectingShot;
import com.troytd.towers.shots.single.SingleShot;
import com.troytd.towers.units.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Tower {
    public static final int upgradeCost = 25;
    public static final HashMap<String, Stat> defaultStats = new HashMap<>();
    public final static TowerTypes type = TowerTypes.NONE;

    static {
        defaultStats.put("cost", new Stat<>("cost", 100));
        defaultStats.put("damage", new Stat<>("damage", 200));
        defaultStats.put("range", new Stat<>("range", 300));
        defaultStats.put("lifeDuration", new Stat<>("lifeDuration", 500));
        defaultStats.put("range2", new Stat<>("range2", 100));
        defaultStats.put("speed", new Stat<>("speed", 100));
        defaultStats.put("maxHP", new Stat<>("maxHP", 100));
        defaultStats.put("atspeed", new Stat<>("atspeed", 100));
        defaultStats.put("enemyAmount", new Stat<>("enemyAmount", 3));
        defaultStats.put("unitAmount", new Stat<>("unitAmount", 3));
    }

    protected final Vector2 distortion;
    protected final TroyTD game;
    private final Vector2 centerPosition;
    private final HashMap<String, Stat> stats = new HashMap<>();
    private final Class<? extends Unit> unitClass;
    public String name;
    public int kills = 0;
    public int totalDamage = 0;
    public boolean isSelected = false;
    protected Class<? extends Shot> shotClass;
    protected Sprite towerSprite;
    Vector2 position;
    private long lastShot = TimeUtils.millis();

    public Tower(final TroyTD game, Vector2 position, Texture texture, final String name, Vector2 distortion,
                 Class<? extends Shot> shotClass) {
        this(game, position, texture, name, distortion, shotClass, null);
    }

    public Tower(final TroyTD game, Vector2 position, Texture texture, final String name,
                 Class<? extends Unit> unitClass, Vector2 distortion) {
        this(game, position, texture, name, distortion, null, unitClass);
    }

    public Tower(final TroyTD game, Vector2 position, Texture texture, final String name, Vector2 distortion,
                 Class<? extends Shot> shotClass, Class<? extends Unit> unitClass) {
        TowerTypes towerType = getType();

        if (unitClass == null && towerType == TowerTypes.MELEE) {
            throw new IllegalArgumentException("No unit class specified for melee tower");
        }

        if (shotClass == null && (towerType == TowerTypes.SINGLE || towerType == TowerTypes.AOE)) {
            throw new IllegalArgumentException("No shot class specified for tower");
        }

        this.game = game;
        this.towerSprite = new Sprite(texture);
        towerSprite.setPosition(position.x, position.y);
        towerSprite.setSize(getSize(game), getSize(game));
        this.name = name;
        this.distortion = distortion;
        this.shotClass = shotClass;
        this.unitClass = unitClass;
        this.centerPosition = towerSprite.getBoundingRectangle().getCenter(new Vector2());
    }

    static public float getSize(TroyTD game) {
        return game.settingPreference.getInteger("width") * 0.075f;
    }

    public void setStat(String key, Stat stat) {
        HashMap<String, Stat> tempStats;
        try {
            tempStats = (HashMap<String, Stat>) ClassReflection.getField(this.getClass(), "defaultStats").get(null);
        } catch (ReflectionException e) {
            tempStats = defaultStats;
        }
        if (tempStats.containsKey(key)) {
            if (tempStats.get(key).getValue().getClass() == stat.getValue().getClass()) {
                stats.put(key, stat);
            } else {
                throw new IllegalArgumentException(
                        "Cannot set stat " + key + " to " + stat.getValue() + " because it is not of type " + tempStats.get(
                                key).getValue().getClass());
            }
        } else {
            throw new IllegalArgumentException("Cannot set stat " + key + " because it is not a correct stat");
        }
    }

    public Stat getStat(String key) {
        HashMap<String, Stat> tempStats;
        try {
            tempStats = (HashMap<String, Stat>) ClassReflection.getField(this.getClass(), "defaultStats").get(null);
        } catch (ReflectionException e) {
            tempStats = defaultStats;
        }
        if (stats.containsKey(key)) {
            return stats.get(key);
        } else if (tempStats.containsKey(key)) {
            return tempStats.get(key);
        } else {
            throw new IllegalArgumentException("Cannot get stat " + key + " because it is not a correct stat");
        }
    }

    public void dispose() {
        towerSprite.getTexture().dispose();
    }

    public Rectangle getRect() {
        return towerSprite.getBoundingRectangle();
    }

    public void draw() {
        if (isSelected) {
            game.shapeDrawer.setColor(0.5f, 0.5f, 0.5f, 0.25f);
            game.shapeDrawer.filledCircle(getCenterPosition(), (int) getStat("range").getValue());
        }
        towerSprite.draw(game.batch);
    }

    public Texture getTexture() {
        return towerSprite.getTexture();
    }

    /**
     * shoots a shot
     *
     * @return a new shot instance or null if no shot can be made
     */
    public Shot shoot(ArrayList<Enemy> enemies) {
        switch (getType()) {
            case SINGLE:
                try {
                    Enemy target = Enemy.getClosest(getPosition(), enemies);
                    float distanceToTarget = target.getPosition().dst(getPosition());
                    if (distanceToTarget > (int) getStat("range").getValue()) return null;
                    return (SingleShot) ClassReflection.getConstructor(shotClass, TroyTD.class, Tower.class,
                                                                       Enemy.class).newInstance(game, this, target);
                } catch (ReflectionException e) {
                    e.printStackTrace();
                    return null;
                }
            case AOE:
                try {
                    return (ConnectingShot) ClassReflection.getConstructor(shotClass, TroyTD.class, Tower.class,
                                                                           ArrayList.class)
                            .newInstance(game, this, enemies);
                } catch (ReflectionException e) {
                    e.printStackTrace();
                    return null;
                }
            default:
                return null;
        }
    }

    public Vector2 getPosition() {
        if (position == null) position = new Vector2();
        towerSprite.getBoundingRectangle().getPosition(position);
        return position;
    }

    public void setPosition(Vector2 position) {
        towerSprite.setPosition(position.x, position.y);
    }

    public TowerTypes getType() {
        TowerTypes towerType;
        try {
            towerType = (TowerTypes) ClassReflection.getField(this.getClass(), "type").get(null);
        } catch (ReflectionException e) {
            e.printStackTrace();
            towerType = type;
        }
        return towerType;
    }

    public void update(ArrayList<Enemy> enemies, final ArrayList<Shot> shots, ArrayList<Unit> units) {
        if (enemies.isEmpty()) return;

        switch (getType()) {
            case SINGLE:
            case AOE:
                if (TimeUtils.timeSinceMillis(lastShot) > 1f / ((int) getStat("atspeed").getValue() / 100000f)) {
                    Shot shot = shoot(enemies);
                    if (shot != null) {
                        shots.add(shot);
                        lastShot = TimeUtils.millis();
                    }
                }
                break;
            case MELEE:
                int unitsOfThisTower = 0;
                for (Unit unit : units) {
                    if (unit.getTower() == this) unitsOfThisTower++;
                }
                if (TimeUtils.timeSinceMillis(lastShot) > 1f / ((int) getStat("atspeed").getValue() / 100000f)) {
                    if (unitsOfThisTower < (int) getStat("unitAmount").getValue()) {
                        try {
                            units.add((Unit) ClassReflection.getConstructor(unitClass, TroyTD.class, Tower.class)
                                    .newInstance(game, this));
                            lastShot = TimeUtils.millis();
                        } catch (ReflectionException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    private void updateCenterPosition() {
        towerSprite.getBoundingRectangle().getCenter(centerPosition);
    }

    public Vector2 getCenterPosition() {
        updateCenterPosition();
        return centerPosition;
    }
}
