package com.troytd.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.troytd.game.TroyTD;
import com.troytd.maps.Map;
import com.troytd.screens.GameScreen;

public class InfoTowerHUD extends SideHUD {
    private final Image towerImage;

    public InfoTowerHUD(TroyTD game, Stage stage, Map map, float topHUDHeight, final GameScreen gameScreen) {
        super(game, stage, map, topHUDHeight, "Tower Stats", gameScreen);

        // tower preview image
        towerImage = new Image(game.assetManager.get("towers/NoTower.png", Texture.class));

        table.add(towerImage).width(topHUDHeight * 0.8f).height(topHUDHeight * 0.8f).pad(topHUDHeight * 0.1f);
    }

    /**
     * <li>adds the assets to the asset manager</li>
     * <li>needs to be called before the class is initialized</li>
     * <li>asset manager has to have finished loading to initialize the class</li>
     *
     * @param game the game instance
     */
    public static void loadAssets(final TroyTD game) {
        game.assetManager.load("towers/NoTower.png", Texture.class);
    }

    /**
     * gets called when the tower place is updated
     * used to update the labels
     */
    @Override
    protected void updatedTowerPlace() {
        towerImage.setDrawable(new TextureRegionDrawable(towerPlace.getTower().getTexture()));
    }
}
