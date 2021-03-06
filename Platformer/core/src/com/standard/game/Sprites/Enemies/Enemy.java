package com.standard.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.standard.game.Screens.PlayScreen;
import com.standard.game.Sprites.Player;

/**
 * Created by Standard on 05.12.2017.
 */

public abstract class Enemy extends Sprite
{

    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    public Enemy(PlayScreen screen, float x, float y)
    {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
        velocity = new Vector2(1, 0);
        b2body.setActive(false);
    }


    protected abstract void defineEnemy();
    public abstract void hitOnHead(Player player);
    public abstract void onEnemyHit(Enemy enemy);

    public void reverseVelocity(boolean x, boolean y)
    {
        if(x)
        {
            velocity.x = -velocity.x;
        }

        if(y)
        {
            velocity.y = -velocity.y;
        }
    }

    public abstract void update(float dt);




}
