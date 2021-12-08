package com.troytd.maps;

import com.badlogic.gdx.math.Vector2;
import com.troytd.towers.Tower;

/**
 * <strong>A place for a tower</strong>
 * <p>
 * An object which stands for a place on the map where a tower can be placed.
 * Stores the place as a Vector and the tower that is placed there. (null if there is no tower)
 * </p>
 */
public class TowerPlace {
    public final Vector2 place;
    private Tower tower;

    public TowerPlace(final Vector2 place, final Tower tower) {
        this.place = place;
        this.tower = tower;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
        this.tower.setSize(Tower.size, Tower.size);
        this.tower.setPosition(new Vector2(place.x - Tower.size / 2f, place.y - Tower.size / 2f));
    }
}
