package com.standard.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.standard.game.PlatformerGame;
import com.standard.game.Screens.PlayScreen;

/**
 * Created by Standard on 03.12.2017.
 */

public class Life extends InteractiveTileObject
{   private static TiledMapTileSet tileSet;
    private final int BLANK_LIFE = 386;
    public Life(PlayScreen screen, Rectangle bounds)
    {
        super(screen, bounds);
        tileSet = map.getTileSets().getTileSet("background_lvl1tiles");
        fixture.setUserData(this);
        setCategoryFilter(PlatformerGame.LIFE_BIT);


    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Life", "Collision");
        getCell().setTile(tileSet.getTile(BLANK_LIFE));
    }
}
