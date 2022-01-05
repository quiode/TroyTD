package com.troytd.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.troytd.game.TroyTD;
import com.troytd.maps.DebugMap;
import com.troytd.maps.Map;
import com.troytd.maps.Map1;

public class ChooseMapScreen implements Screen {
    private final TroyTD game;
    private final Stage stage;

    private final Class<? extends Map>[] mapList = new Class[]{DebugMap.class, Map1.class};

    public ChooseMapScreen(TroyTD game) {
        // variables
        this.game = game;

        // stage
        OrthographicCamera camera = new OrthographicCamera(game.settingPreference.getInteger("width"),
                                                           game.settingPreference.getInteger("height"));
        FitViewport viewport = new FitViewport(game.settingPreference.getInteger("width"),
                                               game.settingPreference.getInteger("height"), camera);
        stage = new Stage(viewport);

        // map menu
        HorizontalGroup mapMenu = new HorizontalGroup();
        mapMenu.setFillParent(true);
        stage.addActor(mapMenu);

        // map buttons
        for (Class<? extends Map> map : mapList) {
            // TODO: add map buttons
        }
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
}
