package com.troytd.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.troytd.game.TroyTD;

public class SettingsScreen implements Screen {

    private final TroyTD game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Viewport viewport;

    private final TextField screenResolutionTextField1;
    private final TextField screenResolutionTextField2;
    private final Screen lastScreen;
    private Dialog dialog;

    public SettingsScreen(final TroyTD game, final Screen lastScreen) {
        this.game = game;
        this.lastScreen = lastScreen;

        camera = new OrthographicCamera(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"));
        viewport = new FitViewport(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"), camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // backToGameButton
        // UI Elements
        ImageButton backToGameButton = new ImageButton(game.skin, "backToGame");
        backToGameButton.setSize(backToGameButton.getWidth() * 0.25f, backToGameButton.getHeight() * 0.25f);

        backToGameButton.addListener(new ChangeListener() {
            /**
             * @param event The Event that triggered this listener.
             * @param actor The event target, which is the actor that emitted the change event.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(lastScreen);
                dispose();
            }
        });

        // Table
        table.add(backToGameButton).width(game.settingPreference.getInteger("icon-size")).height(game.settingPreference.getInteger("icon-size")).pad(10).expandX().left().colspan(4);
        table.top();

        // UI Elements
        Label screenResolutionLabel = new Label("Screen Resolution:", game.skin, "settings");
        screenResolutionTextField1 = new TextField(String.valueOf(game.settingPreference.getInteger("width")), game.skin, "settings");
        screenResolutionTextField1.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        screenResolutionTextField1.addListener(new ChangeListener() {
            /**
             * @param event The Event that triggered this listener.
             * @param actor The event target, which is the actor that emitted the change event.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        screenResolutionTextField1.setAlignment(Align.right);
        Label screenResolutionLabel2 = new Label("x", game.skin, "settings");
        screenResolutionTextField2 = new TextField(String.valueOf(game.settingPreference.getInteger("height")), game.skin, "settings");
        screenResolutionTextField2.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        screenResolutionTextField2.setAlignment(Align.left);
        TextButton submitButton = new TextButton("Submit", game.skin, "settings");
        submitButton.addListener(new ChangeListener() {
            /**
             * @param event The Event that triggered this listener.
             * @param actor The event target, which is the actor that emitted the change event.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                putData();
            }
        });
        TextButton cancelButton = new TextButton("Cancel", game.skin, "settings");
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(lastScreen);
                dispose();
            }
        });
        TextButton resetButton = new TextButton("Reset", game.skin, "settings");
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Dialog dialog = new Dialog("", game.skin, "info") {
                    @Override
                    public void result(Object object) {
                        if ((boolean) object) {
                            game.settingPreference.clear();
                            game.settingPreference.flush();
                            Gdx.app.exit();
                        } else {
                            game.setScreen(lastScreen);
                            dispose();
                        }
                    }
                };
                dialog.text("Are you sure you want to reset to default?", game.skin.get("info", Label.LabelStyle.class));
                dialog.button("Yes", true, game.skin.get("error", TextButton.TextButtonStyle.class));
                dialog.button("No", false, game.skin.get("info", TextButton.TextButtonStyle.class));
                dialog.center();
                if (dialog.getButtonTable().getCells().size > 1)
                    dialog.getButtonTable().getCells().first().pad(0, 0, 0, 75);
                dialog.getContentTable().pad(0, 25, 25, 25);
                dialog.setWidth(Gdx.graphics.getWidth() / 2f);
                dialog.setPosition(Gdx.graphics.getWidth() / 2f - dialog.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
                stage.addActor(dialog);
                dialog.show(stage);
            }
        });
        table.row();
        table.add(screenResolutionLabel);
        table.add(screenResolutionTextField1).right();
        table.add(screenResolutionLabel2);
        table.add(screenResolutionTextField2).left();
        table.row();
        table.add(submitButton).colspan(4).pad(10).center();
        table.row();
        table.add(cancelButton).colspan(4).padBottom(10).center();
        table.row();
        table.add(resetButton).colspan(4).padBottom(10).center();

        stage.setDebugAll(false);
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
        if (!game.assetManager.isFinished()) game.setScreen(new LoadingScreen(game, this));
        ScreenUtils.clear(game.BACKGROUND_COLOR);
        camera.update();
        stage.act(delta);
        stage.draw();
    }

    /**
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        stage.dispose();
    }

    private void putData() {
        if (Integer.parseInt(screenResolutionTextField1.getText()) != game.settingPreference.getInteger("width") || Integer.parseInt(screenResolutionTextField2.getText()) != game.settingPreference.getInteger("height")) {
            if (Integer.parseInt(screenResolutionTextField1.getText()) > 400 && Integer.parseInt(screenResolutionTextField2.getText()) > 400) {
                dialog = new Dialog("", game.skin, "info") {
                    public void result(Object object) {
                        if (object.equals(true)) {
                            game.settingPreference.putInteger("width", Integer.parseInt(screenResolutionTextField1.getText()));
                            game.settingPreference.putInteger("height", Integer.parseInt(screenResolutionTextField2.getText()));
                            game.settingPreference.flush();
                            Gdx.app.exit();
                        } else {
                            game.setScreen(lastScreen);
                            dispose();
                        }
                    }
                };
                dialog.text("The application must restart to\napply the new resolution.\nDo you still want to change it?", game.skin.get("info", Label.LabelStyle.class));
                dialog.button("Yes", true, game.skin.get("info", TextButton.TextButtonStyle.class));
                dialog.button("No", false, game.skin.get("error", TextButton.TextButtonStyle.class));
            } else {
                dialog = new Dialog("", game.skin, "error") {
                    public void result(Object object) {
                        dialog.hide();
                    }
                };
                dialog.text("Please enter a valid resolution.", game.skin.get("error", Label.LabelStyle.class));
                dialog.button("OK", true, game.skin.get("error", TextButton.TextButtonStyle.class));
            }
        } else {
            dialog = new Dialog("", game.skin, "info") {
                public void result(Object object) {
                    game.setScreen(lastScreen);
                    dispose();
                }
            };
            dialog.text("No changes have been made.", game.skin.get("info", Label.LabelStyle.class));
            dialog.button("OK", true, game.skin.get("info", TextButton.TextButtonStyle.class));
        }
        dialog.center();
        if (dialog.getButtonTable().getCells().size > 1) dialog.getButtonTable().getCells().first().pad(0, 0, 0, 75);
        dialog.getContentTable().pad(0, 25, 25, 25);
        dialog.setWidth(Gdx.graphics.getWidth() / 2f);
        dialog.setPosition(Gdx.graphics.getWidth() / 2f - dialog.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
        dialog.show(stage);

        Gdx.graphics.setWindowedMode(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"));
    }
}
