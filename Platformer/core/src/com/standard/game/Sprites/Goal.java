package com.standard.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.standard.game.Screens.PlayScreen;

/**
 * Created by Standard on 03.12.2017.
 */

public class Goal extends InteractiveTileObject
{
    public Goal(PlayScreen screen, Rectangle bounds)
    {
        super(screen, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Goal", "Collision");
    }
}
