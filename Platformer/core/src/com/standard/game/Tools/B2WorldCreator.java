package com.standard.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.standard.game.PlatformerGame;
import com.standard.game.Screens.PlayScreen;
import com.standard.game.Sprites.Coin;
import com.standard.game.Sprites.Brick;

/**
 * Created by Standard on 03.12.2017.
 */

public class B2WorldCreator
{
    public B2WorldCreator(PlayScreen screen)
    {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        //ground
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PlatformerGame.PPM, (rect.getY() + rect.getHeight() / 2) / PlatformerGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / PlatformerGame.PPM, rect.getHeight() / 2 / PlatformerGame.PPM);
            fDef.shape = shape;
            body.createFixture(fDef);
        }

        //pipes
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PlatformerGame.PPM, (rect.getY() + rect.getHeight() / 2) / PlatformerGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / PlatformerGame.PPM, rect.getHeight() / 2 / PlatformerGame.PPM);
            fDef.shape = shape;
            fDef.filter.categoryBits = PlatformerGame.OBJECT_BIT;
            body.createFixture(fDef);
        }

        //coin
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen,rect);
        }

        //brick
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Brick(screen, rect);
        }
    }
}
