package com.standard.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.standard.game.PlatformerGame;

/**
 * Created by Standard on 02.12.2017.
 */

public class HUD implements Disposable
{
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    private Label countDownLabel;
    private static Label  scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label warZoneLabel;

    public HUD(SpriteBatch sb)
    {
        worldTimer = 5;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(PlatformerGame.V_WIDTH, PlatformerGame.V_WIDTH, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table  = new Table();
        table.top();
        table.setFillParent(true);

        countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("World", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        warZoneLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(warZoneLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();
        stage.addActor(table);
    }

    public void update(float dt)
    {
        timeCount += dt;
        if(timeCount >= 1)
        {
            worldTimer--;
            countDownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value)
    {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }

    public boolean isWorldTimerZero()
    {
        return worldTimer <= 0;
    }
}
