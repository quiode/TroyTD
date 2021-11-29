package com.troytd.maps;

import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

public class DefaultMap extends Map {
    /**
     * A Map with a texture, it's path, and the places where towers can be placed on the map
     *
     * @param game the game instance
     */
    public DefaultMap(TroyTD game) {
        super(game, "maps/Map-Prototyp-1.png", new Vector2[]{new Vector2(146, 170), new Vector2(71, 450),
                new Vector2(102, 690), new Vector2(300, 690), new Vector2(806, 590), new Vector2(1125, 645),
                new Vector2(1180, 390), new Vector2(1090, 35)}, new Vector2[]{new Vector2(-10, 590), new Vector2(0,
                590), new Vector2(10, 590), new Vector2(115, 595), new Vector2(181, 591), new Vector2(241, 591),
                new Vector2(285, 584), new Vector2(336, 581), new Vector2(366, 570), new Vector2(393, 560),
                new Vector2(420, 539), new Vector2(450, 509), new Vector2(472, 476), new Vector2(500, 435),
                new Vector2(540, 404), new Vector2(583, 392), new Vector2(633, 377), new Vector2(679, 365),
                new Vector2(727, 342), new Vector2(766, 300), new Vector2(808, 243), new Vector2(852, 230),
                new Vector2(906, 221), new Vector2(946, 221), new Vector2(948, 215), new Vector2(1116, 182),
                new Vector2(1164, 166), new Vector2(1218, 148), new Vector2(1258, 128), new Vector2(1280, 120),
                new Vector2(1330, 133)}, (byte) 3);
    }
}
