package com.example.standard.warzone;


import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Standard on 23.11.2017.
 */

public class ResourceManager {

    private static final ResourceManager singletonInstance = new ResourceManager();

    public Engine engine;
    public GameActivity gameActivity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    public ITextureRegion splash_region;

    private BitmapTextureAtlas splashTextureAtlas;

    public void loadMenuResources()
    {
        loadGraphicsForMenu();
        loadAudioForMenu();

    }

    public void loadGameResources()
    {
        loadGraphicsForGame();
        loadAudioForGame();
        loadFontsForGame();
    }

    private void loadGraphicsForMenu()
    {

    }

    private void loadAudioForMenu()
    {

    }

    private void loadGraphicsForGame()
    {

    }

    private void loadAudioForGame()
    {

    }

    private void loadFontsForGame()
    {

    }

    public void loadSplashScreen()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        splashTextureAtlas = new BitmapTextureAtlas(gameActivity.getTextureManager(), 800, 480, TextureOptions.BILINEAR);
        splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, gameActivity, "warzone.png", 0, 0);
        splashTextureAtlas.load();
    }

    public void unloadSplashScreen()
    {
        splashTextureAtlas.unload();
        splash_region = null;
    }

    public static ResourceManager getInstance()
    {
        return singletonInstance;
    }

    public static void initManager(Engine engine, GameActivity gameActivity, Camera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().gameActivity = gameActivity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }



}
