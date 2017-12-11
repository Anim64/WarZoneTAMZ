package com.standard.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.standard.game.Sprites.Player;

/**
 * Created by Standard on 07.12.2017.
 */

public class Controller
{
    Viewport viewport;
    Stage stage;
    Stage stage2;
    boolean upPressed, downPressed, leftPressed, rightPressed, soundOn, topScorePressed;
    OrthographicCamera camera;

    public Controller(PlatformerGame game)
    {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        stage = new Stage(viewport, game.batch);
        stage2 = new Stage(viewport, game.batch);

        Gdx.input.setInputProcessor(stage);

        Preferences pref = Gdx.app.getPreferences("Preferences");

        soundOn = pref.getBoolean("soundOn");






        Image rightImg = new Image(new Texture("Controller/right.png"));
        rightImg.setSize(50, 50);
        rightImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("Controller/left.png"));
        leftImg.setSize(50, 50);
        leftImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        Image upImg = new Image(new Texture("Controller/up.png"));
        upImg.setSize(50, 50);
        upImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image soundOnImg = new Image(new Texture("audio/soundon.png"));
        soundOnImg.setSize(50, 50);
        soundOnImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                soundOn = !soundOn;
                Preferences pref = Gdx.app.getPreferences("Preferences");

                pref.putBoolean("soundOn", soundOn);

                pref.flush();
            }
        });

        Image soundOffImg = new Image(new Texture("audio/soundoff.png"));
        soundOffImg.setSize(50, 50);
        soundOffImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        Image topScoreImg = new Image(new Texture("topscore.png"));
        topScoreImg.setSize(50, 50);
        topScoreImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });



        Table table = new Table();
        table.left().bottom();
        table.add();
        table.add(soundOnImg).size(soundOnImg.getWidth(), soundOnImg.getHeight()).padBottom(350);
        table.add();
        table.add(topScoreImg).size(topScoreImg.getWidth(), topScoreImg.getHeight()).padLeft(570).padBottom(350);

        table.row();

        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());

        table.add(upImg).size(upImg.getWidth(), upImg.getHeight()).padLeft(570);



        stage.addActor(table);





    }

    public void draw()
    {
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }

    public boolean isSoundOn() {
        return soundOn;
    }

    public boolean isTopScorePressed() {
        return topScorePressed;
    }
}
