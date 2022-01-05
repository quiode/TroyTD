package com.troytd.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Loadable;
import com.troytd.maps.DebugMap;
import com.troytd.maps.Map;
import com.troytd.maps.Map1;

public class ChooseMapScreen implements Screen, Loadable {
    private final TroyTD game;
    private final Stage stage;
    private final Table mapMenu;

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
        mapMenu = new Table();
        mapMenu.setFillParent(true);
        mapMenu.center();
        stage.addActor(mapMenu);

        // map buttons
        for (Class<? extends Map> map : mapList) {
            game.assetManager.load("maps/" + map.getSimpleName() + ".png", Texture.class);
        }
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(game.BACKGROUND_COLOR);

        stage.act(delta);
        stage.draw();
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

    public void afterLoad() {
        for (final Class<? extends Map> map : mapList) {
            ImageButton mapButton = new ImageButton(new TextureRegionDrawable(
                    game.assetManager.get("maps/" + map.getSimpleName() + ".png", Texture.class)));

            mapButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.setScreen(new GameScreen(game, map));
                }
            });

            mapMenu.add(mapButton)
                    .width(game.settingPreference.getInteger("width") / (float) (mapList.length + 2))
                    .height(game.settingPreference.getInteger("height") / (float) (mapList.length + 2))
                    .pad(game.settingPreference.getInteger("width") / (float) (mapList.length + 2) / 8);

        }

        mapMenu.row();

        for (Class<? extends Map> aClass : mapList) {
            Label mapLabel = new Label(aClass.getSimpleName(), game.skin);
            mapMenu.add(mapLabel)
                    .center()
                    .padLeft(game.settingPreference.getInteger("width") / (float) (mapList.length + 2) / 8)
                    .padRight(game.settingPreference.getInteger("width") / (float) (mapList.length + 2) / 8);
        }

        //mapMenu.debug();
    }
}
