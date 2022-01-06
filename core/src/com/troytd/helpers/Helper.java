package com.troytd.helpers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A helper class for various things.
 */

public class Helper {
    /**
     * @param center Point to start the search
     * @param points points to find the closest point to
     * @return The closest point as index in points
     */
    public static int getClosest(Vector2 center, Vector2[] points) {
        int shortestDistance = 0;
        for (int i = 0; i < points.length; i++) {
            if (center.dst(points[i]) < center.dst(points[shortestDistance])) {
                shortestDistance = i;
            }
        }
        return shortestDistance;
    }

    /**
     * @param rect1 Rectangle to start the search
     * @param rects Rectangles to find the closest point to
     * @return The closest point as index in rects
     */
    public static int getClosest(Rectangle rect1, Rectangle[] rects) {
        Vector2 center = rect1.getPosition(new Vector2());
        Vector2[] points = new Vector2[rects.length];
        for (int i = 0; i < rects.length; i++) {
            points[i] = rects[i].getPosition(new Vector2());
        }
        return getClosest(center, points);
    }

    /**
     * @param sprite  Sprite to start the search
     * @param sprites Sprites to find the closest point to
     * @return The closest sprite in sprites
     */
    public static Sprite getClosest(Sprite sprite, Sprite[] sprites) {
        Rectangle center = sprite.getBoundingRectangle();
        Rectangle[] rects = new Rectangle[sprites.length];
        for (int i = 0; i < sprites.length; i++) {
            rects[i] = sprites[i].getBoundingRectangle();
        }
        return sprites[getClosest(center, rects)];
    }

    /**
     * @param sprite  Sprite to start the search
     * @param sprites Sprites to find the closest point to
     * @return The closest point as index in sprites
     */
    public static int getClosestIndex(Sprite sprite, Sprite[] sprites) {
        Rectangle center = sprite.getBoundingRectangle();
        Rectangle[] rects = new Rectangle[sprites.length];
        for (int i = 0; i < sprites.length; i++) {
            rects[i] = sprites[i].getBoundingRectangle();
        }
        return getClosest(center, rects);
    }
}
