package com.standard.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.standard.game.PlatformerGame;
import com.standard.game.Scenes.HUD;
import com.standard.game.Screens.PlayScreen;
import com.standard.game.Sprites.Items.ItemDef;
import com.standard.game.Sprites.Items.Mushroom;

/**
 * Created by Standard on 03.12.2017.
 */

public class Coin extends InteractiveTileObject
{   private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;
    public Coin(PlayScreen screen, MapObject object)
    {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(PlatformerGame.COIN_BIT);


    }

    @Override
    public void onHeadHit(Player player) {
        Gdx.app.log("Coin", "Collision");
        if(getCell().getTile().getId() == BLANK_COIN)
        {
            PlatformerGame.manager.get("audio/sounds/bump.wav", Sound.class).play();
        }
        else
        {
            if(object.getProperties().containsKey("mushroom"))
            {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / PlatformerGame.PPM),
                        Mushroom.class));
                PlatformerGame.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            }
            else
            {
                PlatformerGame.manager.get("audio/sounds/coin.wav", Sound.class).play();
            }
           

        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        HUD.addScore(100);
    }
}
