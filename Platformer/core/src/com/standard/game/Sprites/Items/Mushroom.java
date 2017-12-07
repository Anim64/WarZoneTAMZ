package com.standard.game.Sprites.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.standard.game.PlatformerGame;
import com.standard.game.Screens.PlayScreen;
import com.standard.game.Sprites.Player;

/**
 * Created by Standard on 07.12.2017.
 */

public class Mushroom extends Item
{
    public Mushroom(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("mushroom"), 0, 0, 16, 16);
        velocity = new Vector2(0.7f,0);
    }

    @Override
    public void defineItem() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PlatformerGame.PPM);


        fdef.filter.categoryBits = PlatformerGame.ITEM_BIT;
        fdef.filter.maskBits =  PlatformerGame.GROUND_BIT |
                PlatformerGame.COIN_BIT |
                PlatformerGame.BRICK_BIT |
                PlatformerGame.OBJECT_BIT |
                PlatformerGame.PLAYER_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void use(Player player) {
        destroy();
        player.grow();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        velocity.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocity);
    }
}
