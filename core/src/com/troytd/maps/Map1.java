package com.troytd.maps;

import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.towers.Tower;

public class Map1 extends Map {
    /**
     * A Map with a texture, it's path, and the places where towers can be placed on the map
     *
     * @param game        the game instance
     * @param texturePath the path to the texture
     * @param towerPlaces the places where towers can be placed
     * @param pathPoints  the points that make up the path
     * @param maxRounds   the amount of rounds needed to win
     * @param name        the name of the map
     * @param enemies
     * @param towers
     */
    public Map1(TroyTD game, String texturePath, Vector2[] towerPlaces, Vector2[] pathPoints, byte maxRounds, String name, Class<? extends Enemy>[] enemies, Class<? extends Tower>[] towers) {
        super(game, texturePath, towerPlaces, pathPoints, maxRounds, name, enemies, towers);
    }
}
