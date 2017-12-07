package com.standard.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.standard.game.PlatformerGame;
import com.standard.game.Scenes.HUD;
import com.standard.game.Sprites.Goomba;
import com.standard.game.Sprites.Player;
import com.standard.game.Tools.B2WorldCreator;
import com.standard.game.Tools.WorldContactListener;

/**
 * Created by Standard on 02.12.2017.
 */

public class PlayScreen implements Screen
{
    private PlatformerGame game;
    private TextureAtlas atlas;


    private OrthographicCamera gameCamera;
    private Viewport gamePort;

    private HUD hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

     private Player player;

    private Goomba goomba;

    private Music music;

    public PlayScreen(PlatformerGame game)
    {
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;

        gameCamera = new OrthographicCamera();

        gamePort = new FitViewport(PlatformerGame.V_WIDTH / PlatformerGame.PPM, PlatformerGame.V_HEIGHT / PlatformerGame.PPM, gameCamera);

        hud = new HUD(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / PlatformerGame.PPM);

        gameCamera.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(this);

        //create player
        player = new Player(this);

        world.setContactListener(new WorldContactListener());

        music = PlatformerGame.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.play();

        goomba = new Goomba(this, .32f, .32f);


    }

    public TextureAtlas getAtlas()
    {
        return atlas;
    }



    @Override
    public void show() {

    }

    public void handleInput(float dt)
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
        {
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);

        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
        {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
        {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float dt)
    {
        handleInput(dt);

        world.step(1/60f, 6, 2);

        player.update(dt);
        goomba.update(dt);

        hud.update(dt);

        gameCamera.position.x = player.b2body.getPosition().x;

        gameCamera.update();
        renderer.setView(gameCamera);
    }

    @Override
    public void render(float delta)
    {
        //update logic
        update(delta);

        //clear game screen with black
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //render our game map
        renderer.render();

        //render our Box2DDebugLines
        b2dr.render(world,gameCamera.combined);
        game.batch.setProjectionMatrix(gameCamera.combined);

        game.batch.begin();
        player.draw(game.batch);
        goomba.draw(game.batch);
        game.batch.end();




        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        gamePort.update(width, height);
    }

    public TiledMap getMap()
    {
        return map;

    }

    public World getWorld()
    {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
