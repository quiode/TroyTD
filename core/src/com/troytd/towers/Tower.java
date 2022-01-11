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
import com.troytd.shots.Shot;

import java.util.ArrayList;

public abstract class Tower {
    public final static int size = 100;
    public final static int cost = 100;
    public final static int damage = 100;
    public final static int range = 100;
    public final static int speed = 100;
    public final static int maxHP = 100;
    public final static int atspeed = 100;
    public final static int AOE = 1;
    protected final Vector2 distortion;
    protected final TowerTypes type;
    protected final TroyTD game;
    public String name = "Tower";
    public int kills = 0;
    public int hp = maxHP;
    public int totalDamage = 0;
    protected Class<? extends Shot> shotClass;
    protected Sprite towerSprite;
    private long lastShot = TimeUtils.millis();

    public Tower(final TroyTD game, Vector2 position, Texture texture, final String name, final TowerTypes type,
                 Vector2 distortion, Class<? extends Shot> shotClass) {
        this.game = game;
        this.towerSprite = new Sprite(texture);
        towerSprite.setPosition(position.x, position.y);
        towerSprite.setSize(towerSprite.getWidth() * distortion.x, towerSprite.getHeight() * distortion.y);
        this.name = name;
        this.type = type;
        this.distortion = distortion;
        this.shotClass = shotClass;
    }

    public void dispose() {
        towerSprite.getTexture().dispose();
    }

    public Rectangle getRect() {
        return towerSprite.getBoundingRectangle();
    }

    public void draw() {
        towerSprite.draw(game.batch);
    }

    public void setSize(float width, float height) {
        towerSprite.setSize(width * distortion.x, height * distortion.y);
    }

    public Texture getTexture() {
        return towerSprite.getTexture();
    }

    /**
     * shoots a shot
     *
     * @return a new shot instance
     */
    public Shot shoot(ArrayList<Enemy> enemies) {
        try {
            return (Shot) ClassReflection.getConstructor(shotClass, TroyTD.class, Tower.class, Enemy.class,
                                                         Vector2.class)
                    .newInstance(game, this, Enemy.getClosest(getPosition(), enemies), distortion);
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Vector2 getPosition() {
        Vector2 position = new Vector2();
        towerSprite.getBoundingRectangle().getPosition(position);
        return position;
    }

    public void setPosition(Vector2 position) {
        towerSprite.setPosition(position.x, position.y);
    }

    public String getType() {
        return type.toString();
    }

    public void update(float delta, ArrayList<Enemy> enemies, final ArrayList<Shot> shots) {
        if (enemies.isEmpty()) return;

        try {
            if (TimeUtils.timeSinceMillis(lastShot) > 1 / ((int) ClassReflection.getField(this.getClass(), "atspeed")
                    .get(null) / 100000f)) {
                shots.add(shoot(enemies));
                lastShot = TimeUtils.millis();
            }
        } catch (ReflectionException e) {
            e.printStackTrace();
            if (TimeUtils.timeSinceMillis(lastShot) > atspeed) {
                shots.add(shoot(enemies));
                lastShot = TimeUtils.millis();
            }
        }
    }
}
