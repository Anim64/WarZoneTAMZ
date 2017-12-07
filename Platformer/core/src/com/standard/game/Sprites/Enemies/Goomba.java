package com.standard.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.standard.game.PlatformerGame;
import com.standard.game.Screens.PlayScreen;

/**
 * Created by Standard on 05.12.2017.
 */

public class Goomba extends com.standard.game.Sprites.Enemies.Enemy
{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> movementFrames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        //defineEnemy();
        movementFrames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)
        {
            movementFrames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
        }
        walkAnimation = new Animation<TextureRegion>(0.4f, movementFrames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / PlatformerGame.PPM, 16 / PlatformerGame.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt)
    {
        stateTime += dt;
        if(setToDestroy && !destroyed)
        {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16));
            stateTime = 0;
        }

        else if(!destroyed)
        {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PlatformerGame.PPM);

        fdef.filter.categoryBits = PlatformerGame.ENEMY_BIT;
        fdef.filter.maskBits =  PlatformerGame.GROUND_BIT |
                                PlatformerGame.COIN_BIT |
                                PlatformerGame.BRICK_BIT |
                                PlatformerGame.ENEMY_BIT |
                                PlatformerGame.OBJECT_BIT |
                                PlatformerGame.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-5, 8).scl(1/PlatformerGame.PPM);
        vertices[1] = new Vector2(5, 8).scl(1/PlatformerGame.PPM);
        vertices[2] = new Vector2(-3, 3).scl(1/PlatformerGame.PPM);
        vertices[3] = new Vector2(3, 3).scl(1/PlatformerGame.PPM);
        head.set(vertices);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = PlatformerGame.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);



    }

    public void draw(Batch batch)
    {
        if(!destroyed || stateTime < 1)
        {
            super.draw(batch);
        }
    }

    @Override
    public void hitOnHead()
    {
        setToDestroy = true;
    }
}
