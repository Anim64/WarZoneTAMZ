package com.standard.game.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.standard.game.PlatformerGame;
import com.standard.game.Screens.PlayScreen;
import com.standard.game.Sprites.Enemies.Enemy;
import com.standard.game.Sprites.Enemies.Turtle;

/**
 * Created by Standard on 02.12.2017.
 */

public class Player extends Sprite
{



    public enum State{FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD, WON};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private TextureRegion bigPlayerStand;
    private TextureRegion bigPlayerJump;
    private TextureRegion playerDead;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> bigPlayerRun;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> growMario;

    private TextureRegion playerJump;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> playerRun;

    private boolean onGround;

    private float stateTimer;
    private boolean runningRight;
    private boolean marioIsBig;
    private boolean runGrowAnimation;
    private boolean timeToDefineBigPlayer;
    private boolean timeToRedefinePlayer;
    private boolean playerIsDead;

    public Player(PlayScreen screen)
    {

        this.world = screen.getWorld();

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();



        for(int i = 1; i < 4; i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i* 16, 0, 16, 16));
        }
        playerRun = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.1f, frames);
        frames.clear();



        for(int i = 1; i < 4; i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), i* 16, 0, 16, 32));
        }
        bigPlayerRun = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));

        growMario = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.2f, frames);
        frames.clear();

        playerJump = new TextureRegion(screen.getAtlas().findRegion("little_mario"),80,0, 16, 16);
        bigPlayerJump = new TextureRegion(screen.getAtlas().findRegion("big_mario"),80,0, 16, 32);
        frames.clear();




        playerStand = new TextureRegion(screen.getAtlas().findRegion("little_mario"),0,0,16,16);
        bigPlayerStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0, 16, 32);

        playerDead = new TextureRegion(screen.getAtlas().findRegion("little_mario"),96,0, 16, 16);

        definePlayer();
        setBounds(0,0, 16 / PlatformerGame.PPM, 16 / PlatformerGame.PPM);
        setRegion(playerStand);

        onGround =true;
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

    public void defineBigPlayer()
    {

        Vector2 currentPosition = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(currentPosition.add(0, 10 / PlatformerGame.PPM));
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

        shape.setPosition(new Vector2(0, -14 / PlatformerGame.PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PlatformerGame.PPM, 6 / PlatformerGame.PPM), new Vector2(2 / PlatformerGame.PPM, 6 / PlatformerGame.PPM));
        fdef.filter.categoryBits = PlatformerGame.PLAYER_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        timeToDefineBigPlayer = false;
    }

    public void redefinePlayer()
    {

        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);


        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
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

        timeToRedefinePlayer = false;
    }

    public void update(float dt)
    {
        if(marioIsBig)
        {
            setPosition(b2body.getPosition().x - getWidth() /2 , b2body.getPosition().y - getHeight() / 2 - 6 / PlatformerGame.PPM);
        }
        else
            setPosition(b2body.getPosition().x - getWidth() /2 , b2body.getPosition().y - getHeight() /2);

        setRegion(getFrame(dt));

        if(timeToDefineBigPlayer)
        {
            defineBigPlayer();
        }

        if(timeToRedefinePlayer)
        {
            redefinePlayer();
        }
    }

    public TextureRegion getFrame(float dt)
    {
        currentState = getState();

        TextureRegion region;

        switch(currentState)
        {
            case DEAD:
            {
                region = playerDead;
                break;
            }

            case GROWING:
            {
                region = growMario.getKeyFrame(stateTimer);
                if(growMario.isAnimationFinished(stateTimer))
                {
                    runGrowAnimation = false;
                }
                break;
            }
            case JUMPING:
            {
                region = marioIsBig ? bigPlayerJump : playerJump;
                break;
            }

            case RUNNING:
            {
                region = marioIsBig ? bigPlayerRun.getKeyFrame(stateTimer, true) : playerRun.getKeyFrame(stateTimer, true) ;
                break;
            }

            case FALLING:
            {
                region = marioIsBig ? bigPlayerStand : playerStand;
                break;
            }

            case STANDING:
            {
                region = marioIsBig ? bigPlayerStand : playerStand;
                break;
            }

            default:
            {
                region = marioIsBig ? bigPlayerStand : playerStand;
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

        if(playerIsDead)
        {
            return State.DEAD;
        }
        else if(runGrowAnimation)
        {
            return State.GROWING;
        }

        else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
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

    public void grow()
    {
        runGrowAnimation = true;
        marioIsBig = true;
        timeToDefineBigPlayer = true;
        setBounds(getX(), getY(), getWidth(), getHeight() * 2);

        PlatformerGame.manager.get("audio/sounds/powerup.wav", Sound.class).play();
    }

    public boolean isBig() {
        return marioIsBig;
    }

    public void hit(Enemy enemy)
    {
        if(enemy instanceof Turtle && ((Turtle)enemy).getCurrentState() == Turtle.State.STANDING_SHELL)
        {
            ((Turtle)enemy).kick(this.getX() <= enemy.getX() ? Turtle.KICK_RIGHT_SPEED : Turtle.KICK_LEFT_SPEED);
        }

        else {
            if (marioIsBig) {
                marioIsBig = false;
                timeToRedefinePlayer = true;
                setBounds(getX(), getY(), getWidth(), getHeight() / 2);
                PlatformerGame.manager.get("audio/sounds/powerdown.wav", Sound.class).play();
            } else {
                PlatformerGame.manager.get("audio/music/mario_music.ogg", Music.class).stop();
                PlatformerGame.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
                playerIsDead = true;
                Filter filter = new Filter();
                filter.maskBits = PlatformerGame.NOTHING_BIT;
                for (Fixture fixture : b2body.getFixtureList()) {
                    fixture.setFilterData(filter);
                }
                b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            }
        }
    }

    public boolean  isDead()
    {
        return playerIsDead;
    }

    public float getStateTimer()
    {
        return stateTimer;
    }

    public void setOnGround(boolean OnGround)
    {
        onGround = OnGround;
    }

    public boolean isOnGround()
    {
        return onGround;
    }




}
