package com.standard.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.standard.game.PlatformerGame;
import com.standard.game.Screens.PlayScreen;

/**
 * Created by Standard on 05.12.2017.
 */

public class Goomba extends Enemy
{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> movementFrames;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        defineEnemy();
        movementFrames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)
        {
            movementFrames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
        }
        walkAnimation = new Animation<TextureRegion>(0.4f, movementFrames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / PlatformerGame.PPM, 16 / PlatformerGame.PPM);
    }

    public void update(float dt)
    {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight() /2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    protected void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(20 / PlatformerGame.PPM,32 / PlatformerGame.PPM);
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
                                PlatformerGame.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);


    }
}
