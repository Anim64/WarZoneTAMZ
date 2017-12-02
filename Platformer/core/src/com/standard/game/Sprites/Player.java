package com.standard.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.standard.game.PlatformerGame;

/**
 * Created by Standard on 02.12.2017.
 */

public class Player extends Sprite
{
    public World world;
    public Body b2body;

    public Player(World world)
    {
        this.world = world;
        definePlayer();
    }

    public void definePlayer()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(20 / PlatformerGame.PPM,32 / PlatformerGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / PlatformerGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
