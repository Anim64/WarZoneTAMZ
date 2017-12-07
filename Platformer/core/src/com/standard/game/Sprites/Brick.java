package com.standard.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.standard.game.PlatformerGame;
import com.standard.game.Scenes.HUD;
import com.standard.game.Screens.PlayScreen;

/**
 * Created by Standard on 03.12.2017.
 */

public class Brick extends InteractiveTileObject
{
    public Brick(PlayScreen screen, Rectangle bounds)
    {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(PlatformerGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {

        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(PlatformerGame.DESTROYED_BIT);
        getCell().setTile(null);
        HUD.addScore(200);
        PlatformerGame.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }
}
