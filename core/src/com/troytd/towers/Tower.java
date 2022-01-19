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
import com.troytd.screens.GameScreen;
import com.troytd.towers.shots.Shot;
import com.troytd.towers.shots.connecting.ConnectingShot;
import com.troytd.towers.shots.single.SingleShot;
import com.troytd.towers.units.Unit;

import java.util.ArrayList;

public abstract class Tower {
    public final static int cost = 100;
    public final static int damage = 200;
    public final static int range = 300;
    public final static long lifeDuration = 500;
    public final static int range2 = 100;
    public final static int speed = 100;
    public final static int maxHP = 100;
    public final static int atspeed = 100;
    public final static short enemyAmount = 3;
    public final static short unitAmount = 3;
    public final static TowerTypes type = TowerTypes.NONE;
    protected final Vector2 distortion;
    protected final TroyTD game;
    private final ArrayList<Unit> units = new ArrayList<Unit>(unitAmount);
    public String name = "Tower";
    public int kills = 0;
    public int hp = maxHP;
    public int totalDamage = 0;
    protected Class<? extends Shot> shotClass;
    protected Sprite towerSprite;
    Vector2 position;
    private int ndamage;
    private int nrange;
    private int nrange2;
    private int nspeed;
    private int natspeed;
    private int nmaxHP;
    private short nenemyAmount;
    private short nunitAmount;
    private long nlifeDuration;
    private boolean statsChanged = false;
    private long lastShot = TimeUtils.millis();

    public Tower(final TroyTD game, Vector2 position, Texture texture, final String name, Vector2 distortion,
                 Class<? extends Shot> shotClass) {
        this.game = game;
        this.towerSprite = new Sprite(texture);
        towerSprite.setPosition(position.x, position.y);
        towerSprite.setSize(getSize(game), getSize(game));
        this.name = name;
        this.distortion = distortion;
        this.shotClass = shotClass;
    }

    static public float getSize(TroyTD game) {
        return game.settingPreference.getInteger("width") * 0.075f;
    }

    public int getDamage() {
        if (!statsChanged) {
            try {
                return (Integer) ClassReflection.getField(this.getClass(), "damage").get(null);
            } catch (ReflectionException e) {
                return damage;
            }
        } else {
            return ndamage;
        }
    }

    public void setDamage(int damage) {
        this.ndamage = damage;
        statsChanged = true;
    }

    public int getRange() {
        if (!statsChanged) {
            try {
                return (Integer) ClassReflection.getField(this.getClass(), "range").get(null);
            } catch (ReflectionException e) {
                return range;
            }
        } else {
            return nrange;
        }
    }

    public void setRange(int range) {
        this.nrange = range;
        statsChanged = true;
    }

    public int getRange2() {
        if (!statsChanged) {
            try {
                return (Integer) ClassReflection.getField(this.getClass(), "range2").get(null);
            } catch (ReflectionException e) {
                return range2;
            }
        } else {
            return nrange2;
        }
    }

    public void setRange2(int range2) {
        this.nrange2 = range2;
        statsChanged = true;
    }

    public int getSpeed() {
        if (!statsChanged) {
            try {
                return (Integer) ClassReflection.getField(this.getClass(), "speed").get(null);
            } catch (ReflectionException e) {
                return speed;
            }
        } else {
            return nspeed;
        }
    }

    public void setSpeed(int speed) {
        this.nspeed = speed;
        statsChanged = true;
    }

    public int getAtSpeed() {
        if (!statsChanged) {
            try {
                return (Integer) ClassReflection.getField(this.getClass(), "atspeed").get(null);
            } catch (ReflectionException e) {
                return atspeed;
            }
        } else {
            return natspeed;
        }
    }

    public void setAtSpeed(int atspeed) {
        this.natspeed = atspeed;
        statsChanged = true;
    }

    public int getMaxHP() {
        if (!statsChanged) {
            try {
                return (Integer) ClassReflection.getField(this.getClass(), "maxHP").get(null);
            } catch (ReflectionException e) {
                return maxHP;
            }
        } else {
            return nmaxHP;
        }
    }

    public void setMaxHP(int maxHP) {
        this.nmaxHP = maxHP;
        statsChanged = true;
    }

    public short getEnemyAmount() {
        if (!statsChanged) {
            try {
                return (Short) ClassReflection.getField(this.getClass(), "enemyAmount").get(null);
            } catch (ReflectionException e) {
                return enemyAmount;
            }
        } else {
            return nenemyAmount;
        }
    }

    public void setEnemyAmount(short enemyAmount) {
        this.nenemyAmount = enemyAmount;
        statsChanged = true;
    }

    public short getUnitAmount() {
        if (!statsChanged) {
            try {
                return (Short) ClassReflection.getField(this.getClass(), "unitAmount").get(null);
            } catch (ReflectionException e) {
                return unitAmount;
            }
        } else {
            return nunitAmount;
        }
    }

    public void setUnitAmount(short unitAmount) {
        this.nunitAmount = unitAmount;
        statsChanged = true;
    }

    public long getLifeDuration() {
        if (!statsChanged) {
            try {
                return (Long) ClassReflection.getField(this.getClass(), "lifeDuration").get(null);
            } catch (ReflectionException e) {
                return lifeDuration;
            }
        } else {
            return nlifeDuration;
        }
    }

    public void setLifeDuration(long lifeDuration) {
        this.nlifeDuration = lifeDuration;
        statsChanged = true;
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

    public Texture getTexture() {
        return towerSprite.getTexture();
    }

    /**
     * shoots a shot
     *
     * @return a new shot instance or null if no shot can be made
     */
    public Shot shoot(ArrayList<Enemy> enemies) {
        switch (type) {
            case SINGLE:
                try {
                    Enemy target = Enemy.getClosest(getPosition(), enemies);
                    float distanceToTarget = target.getPosition().dst(getPosition());
                    if (distanceToTarget > range) return null;
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

    public String getType() {
        return type.toString();
    }

    public void update(ArrayList<Enemy> enemies, final ArrayList<Shot> shots) {
        if (enemies.isEmpty()) return;

        try {
            if (TimeUtils.timeSinceMillis(lastShot) > 1 / ((int) ClassReflection.getField(this.getClass(), "atspeed")
                    .get(null) / 100000f)) {
                Shot shot = shoot(enemies);
                if (shot != null) {
                    shots.add(shot);
                    lastShot = TimeUtils.millis();
                }
            }
        } catch (ReflectionException e) {
            e.printStackTrace();
            if (TimeUtils.timeSinceMillis(lastShot) > atspeed) {
                Shot shot = shoot(enemies);
                if (shot != null) {
                    shots.add(shot);
                    lastShot = TimeUtils.millis();
                }
            }
        }
    }
}
