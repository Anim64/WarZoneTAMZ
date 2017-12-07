package com.standard.game;

import com.badlogic.gdx.Gdx;
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
    boolean upPressed, downPressed, leftPressed, rightPressed;
    OrthographicCamera camera;

    public Controller(PlatformerGame game)
    {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        stage = new Stage(viewport, game.batch);
        stage2 = new Stage(viewport, game.batch);

        Gdx.input.setInputProcessor(stage);


        Table tableLeft = new Table();
        tableLeft.left().bottom();



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

        tableLeft.add().padRight(20f);
        tableLeft.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        tableLeft.add().padRight(40f);
        tableLeft.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight()).padRight(550);


        stage.addActor(tableLeft);



        Table tableRight = new Table();
        tableRight.right().bottom();

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

        tableLeft.add(upImg).size(upImg.getWidth(), upImg.getHeight());

        //stage2.addActor(tableRight);

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
}
