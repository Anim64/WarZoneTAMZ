package com.standard.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.standard.game.PlatformerGame;
import com.standard.game.Screens.PlayScreen;

/**
 * Created by Standard on 05.12.2017.
 */

public class Ground extends InteractiveTileObject
{
    public Ground(PlayScreen screen, Rectangle bounds)
    {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(PlatformerGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Ground", "Collision");
        setCategoryFilter(PlatformerGame.DESTROYED_BIT);
        //getCell().setTile(null);
    }


}
