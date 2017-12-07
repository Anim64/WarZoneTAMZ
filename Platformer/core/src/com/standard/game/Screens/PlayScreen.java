package com.standard.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.standard.game.Controller;
import com.standard.game.PlatformerGame;
import com.standard.game.Scenes.HUD;
import com.standard.game.Sprites.Enemies.Enemy;
import com.standard.game.Sprites.Items.Item;
import com.standard.game.Sprites.Items.ItemDef;
import com.standard.game.Sprites.Items.Mushroom;
import com.standard.game.Sprites.Player;
import com.standard.game.Tools.B2WorldCreator;
import com.standard.game.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

import static com.standard.game.Sprites.Player.State.DEAD;
import static com.standard.game.Sprites.Player.State.WON;

/**
 * Created by Standard on 02.12.2017.
 */

public class PlayScreen implements Screen
{
    private PlatformerGame game;
    private TextureAtlas atlas;

    Controller controller;


    private OrthographicCamera gameCamera;
    private Viewport gamePort;

    private HUD hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

     private Player player;



    private Music music;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

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

         creator = new B2WorldCreator(this);

        //create player
        player = new Player(this);

        controller = new Controller(game);

        world.setContactListener(new WorldContactListener());

        music = PlatformerGame.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.play();

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();






    }

    public void spawnItem(ItemDef iDef)
    {
        itemsToSpawn.add(iDef);
    }

    public void handleSpawningItems()
    {
        if(!itemsToSpawn.isEmpty())
        {
            ItemDef iDef = itemsToSpawn.poll();
            if(iDef.type == Mushroom.class)
            {
                items.add(new Mushroom(this, iDef.position.x, iDef.position.y));
            }
        }
    }

    public TextureAtlas getAtlas()
    {
        return atlas;
    }



    @Override
    public void show() {

    }

    public void handleInput(float dt) {
    if(player.currentState != DEAD)
    {
        if (controller.isUpPressed() && player.isOnGround()) {
            player.setOnGround(false);
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);

        }

        if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        if (controller.isLeftPressed() && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }
    }

    public void update(float dt)
    {
        handleInput(dt);
        handleSpawningItems();

        world.step(1/60f, 6, 2);

        player.update(dt);

        for(Enemy enemy : creator.getEnemies())
        {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224 / PlatformerGame.PPM)
            {
                enemy.b2body.setActive(true);
            }
        }

        for(Item item: items)
        {
            item.update(dt);
        }

        hud.update(dt);

        if(player.currentState != DEAD)
        {
            gameCamera.position.x = player.b2body.getPosition().x;
        }


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
        for(Enemy enemy : creator.getEnemies())
        {
            enemy.draw(game.batch);
        }

        for(Item item: items)
        {
            item.draw(game.batch);
        }
        game.batch.end();




        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        controller.draw();

        if(gameOver() || hud.isWorldTimerZero())
        {
            game.setScreen(new GameOverScreen(game));
        }

        if(winTheGame())
        {
            game.setScreen(new WinScreen(game));
        }
    }

    public boolean gameOver()
    {
        return player.currentState == DEAD && player.getStateTimer() > 3;

    }

    public boolean winTheGame()
    {
        return player.currentState == WON && player.getStateTimer() > 3;
    }

    @Override
    public void resize(int width, int height)
    {

        gamePort.update(width, height);
        controller.resize(width, height);
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
