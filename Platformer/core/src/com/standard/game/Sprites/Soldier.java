package com.standard.game.Sprites;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.standard.game.PlatformerGame;
import com.standard.game.Screens.PlayScreen;

/**
 * Created by Standard on 05.12.2017.
 */

public class Soldier extends Enemy
{

    public Soldier(PlayScreen screen, float x, float y) {
        super(screen, x, y);
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
        fdef.filter.maskBits = PlatformerGame.GROUND_BIT | PlatformerGame.LIFE_BIT | PlatformerGame.BRICK_BIT | PlatformerGame.ENEMY_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);


    }
}
