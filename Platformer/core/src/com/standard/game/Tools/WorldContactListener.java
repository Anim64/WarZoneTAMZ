package com.standard.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.standard.game.PlatformerGame;
import com.standard.game.Sprites.Enemies.Enemy;
import com.standard.game.Sprites.InteractiveTileObject;
import com.standard.game.Sprites.Items.Item;
import com.standard.game.Sprites.Player;

/**
 * Created by Standard on 05.12.2017.
 */

public class WorldContactListener implements ContactListener
{

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;



        switch(cDef)
        {
            case PlatformerGame.PLAYER_HEAD_BIT | PlatformerGame.BRICK_BIT:
            case PlatformerGame.PLAYER_HEAD_BIT | PlatformerGame.COIN_BIT:
            {
                if(fixA.getFilterData().categoryBits == PlatformerGame.PLAYER_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Player) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Player) fixB.getUserData());
                break;
            }



            case PlatformerGame.ENEMY_HEAD_BIT | PlatformerGame.PLAYER_BIT:
            {

                    if(fixA.getFilterData().categoryBits == PlatformerGame.ENEMY_HEAD_BIT)
                        ((Enemy)fixA.getUserData()).hitOnHead();
                    else
                        ((Enemy)fixB.getUserData()).hitOnHead();
                    break;
            }

            case PlatformerGame.ENEMY_BIT | PlatformerGame.OBJECT_BIT:
            {
                if(fixA.getFilterData().categoryBits == PlatformerGame.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            }

            case PlatformerGame.PLAYER_BIT | PlatformerGame.ENEMY_BIT:
            {
                Gdx.app.log("MARIO", "DIED");
                break;
            }

            case PlatformerGame.ENEMY_BIT | PlatformerGame.ENEMY_BIT:
            {
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            }

            case PlatformerGame.ITEM_BIT | PlatformerGame.OBJECT_BIT:
            {
                if(fixA.getFilterData().categoryBits == PlatformerGame.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            }

            case PlatformerGame.ITEM_BIT | PlatformerGame.PLAYER_BIT:
            {
                if(fixA.getFilterData().categoryBits == PlatformerGame.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Player) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Player) fixA.getUserData());
                break;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
