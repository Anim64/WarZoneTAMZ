package com.standard.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.standard.game.PlatformerGame;
import com.standard.game.Screens.PlayScreen;
import com.standard.game.Sprites.Player;
import com.standard.game.Tools.B2WorldCreator;


/**
 * Created by Standard on 07.12.2017.
 */

public class Turtle extends  Enemy{


    public static final int KICK_LEFT_SPEED = -2;
    public static final int KICK_RIGHT_SPEED = 2;
    public enum State {WALKING, STANDING_SHELL, MOVING_SHELL, DEAD};
    public State currentState;
    public State previousState;

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> movementFrames;
    private TextureRegion shell;

    private float deadRotationDegrees;
    private boolean destroyed;

    public Turtle(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        deadRotationDegrees = 0;
        movementFrames = new Array<TextureRegion>();

        movementFrames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), 0,0, 16, 24));
        movementFrames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), 16,0, 16, 24));

        shell = new TextureRegion(screen.getAtlas().findRegion("turtle"), 64,0, 16, 24);

        walkAnimation = new Animation<TextureRegion>(0.2f, movementFrames);

        currentState = previousState = State.WALKING;

        setBounds(getX(), getY(), 16 / PlatformerGame.PPM, 24 / PlatformerGame.PPM);

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
        fdef.restitution = 1.5f;
        fdef.filter.categoryBits = PlatformerGame.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void onEnemyHit(Enemy enemy)
    {
        if(enemy instanceof Turtle)
        {
            if(((Turtle) enemy).currentState == State.MOVING_SHELL && currentState != State.MOVING_SHELL)
            {
                killed();
            }
            else if (currentState == State.MOVING_SHELL && ((Turtle)enemy).currentState == State.WALKING)
            {
                return;
            }

            else
            {
                reverseVelocity(true, false);
            }
        }

        else if(currentState != State.MOVING_SHELL)
        {
            reverseVelocity(true, false);
        }
    }

    @Override
    public void hitOnHead(Player player) {
        if(currentState != State.STANDING_SHELL)
        {
            currentState = State.STANDING_SHELL;
            velocity.x = 0;
        }

        else
        {
            kick(player.getX() <= this.getX() ? KICK_RIGHT_SPEED : KICK_LEFT_SPEED);
        }


    }

    public State getCurrentState()
    {
        return currentState;
    }

    @Override
    public void update(float dt) {
        setRegion(getFrame(dt));
        if(currentState == State.STANDING_SHELL && stateTime > 5)
        {
            currentState = State.WALKING;
            velocity.x = 1;
        }

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - 8 / PlatformerGame.PPM);
        if(currentState == State.DEAD)
        {
            deadRotationDegrees += 3;
            rotate(deadRotationDegrees);
            if(stateTime > 5 && !destroyed)
            {
                world.destroyBody(b2body);
                destroyed = true;
                B2WorldCreator.removeTurtle(this);
            }
        }
        else
        {
            b2body.setLinearVelocity(velocity);
        }

    }

    public TextureRegion getFrame(float dt)
    {
        TextureRegion region;

        switch(currentState)
        {
            case STANDING_SHELL:
            case MOVING_SHELL:
                region = shell;
                break;
            case WALKING:
                region = walkAnimation.getKeyFrame(stateTime, true);
                break;
            default:
                region = walkAnimation.getKeyFrame(stateTime, true);
                break;

        }

        if(velocity.x > 0 && region.isFlipX() == false)
        {
            region.flip(true, false);
        }

        if(velocity.x < 0 && region.isFlipX() == true)
        {
            region.flip(true, false);
        }

        stateTime = currentState == previousState ? stateTime + dt : 0;

        previousState = currentState;

        return region;
    }

    public void draw(Batch batch)
    {
        if(!destroyed)
        {
            super.draw(batch);
        }
    }

    public void kick(int speed)
    {
        velocity.x = speed;
        currentState = State.MOVING_SHELL;
    }

    public void killed()
    {
        currentState = State.DEAD;
        Filter filter = new Filter();
        filter.maskBits = PlatformerGame.NOTHING_BIT;

        for(Fixture fixture: b2body.getFixtureList())
        {
            fixture.setFilterData(filter);
        }

        b2body.applyLinearImpulse(new Vector2(0,5f), b2body.getWorldCenter(), true);
    }
}
