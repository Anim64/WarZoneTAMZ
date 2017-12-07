package com.standard.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.standard.game.PlatformerGame;
import com.standard.game.Screens.PlayScreen;

/**
 * Created by Standard on 02.12.2017.
 */

public class Player extends Sprite
{

    public enum State{FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;

    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> playerJump;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> playerRun;
    private float stateTimer;
    private boolean runningRight;

    public Player(PlayScreen screen)
    {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 4; i++)
        {
            frames.add(new TextureRegion(getTexture(), i* 16, 0, 16, 16));
        }
        playerRun = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i = 4; i < 6; i++)
        {
            frames.add(new TextureRegion(getTexture(), i* 16, 0, 16, 16));
        }

        playerJump = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.1f, frames);
        frames.clear();



        definePlayer();
        playerStand = new TextureRegion(getTexture(),0,0,16,16);
        setBounds(0,0, 16 / PlatformerGame.PPM, 16 / PlatformerGame.PPM);
        setRegion(playerStand);
    }

    public void definePlayer()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / PlatformerGame.PPM,32 / PlatformerGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PlatformerGame.PPM);

        fdef.filter.categoryBits = PlatformerGame.PLAYER_BIT;
        fdef.filter.maskBits = PlatformerGame.GROUND_BIT |
                                PlatformerGame.COIN_BIT |
                                PlatformerGame.BRICK_BIT |
                                PlatformerGame.ENEMY_BIT |
                                PlatformerGame.OBJECT_BIT |
                                PlatformerGame.ENEMY_HEAD_BIT |
                                PlatformerGame.ITEM_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PlatformerGame.PPM, 6 / PlatformerGame.PPM), new Vector2(2 / PlatformerGame.PPM, 6 / PlatformerGame.PPM));
        fdef.filter.categoryBits = PlatformerGame.PLAYER_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt)
    {
        setPosition(b2body.getPosition().x - getWidth() /2 , b2body.getPosition().y - getHeight() /2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt)
    {
        currentState = getState();

        TextureRegion region;

        switch(currentState)
        {
            case JUMPING:
            {
                region = playerJump.getKeyFrame(stateTimer);
                break;
            }

            case RUNNING:
            {
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            }

            case FALLING:
            {
                region = playerStand;
                break;
            }

            case STANDING:
            {
                region = playerStand;
                break;
            }

            default:
            {
                region = playerStand;
                break;
            }

        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX())
        {
            region.flip(true, false);
            runningRight = false;
        }

        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX())
        {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState()
    {
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
        {
            return State.JUMPING;

        }
        else if(b2body.getLinearVelocity().y < 0)
        {
            return State.FALLING;
        }

        else if(b2body.getLinearVelocity().x != 0)
        {
            return State.RUNNING;
        }

        else
        {
           return State.STANDING;
        }
    }
}
