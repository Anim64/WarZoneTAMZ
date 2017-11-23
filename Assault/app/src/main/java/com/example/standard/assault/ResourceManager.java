package com.example.standard.assault;


import com.example.standard.assault.GameActivity;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.vbo.VertexBufferObject;
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

    }

    public void unloadSplashScreen()
    {

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
