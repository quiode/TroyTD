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
    public Tower tower;

    public TowerPlace(final Vector2 place, final Tower tower) {
        this.place = place;
        this.tower = tower;
    }
}
