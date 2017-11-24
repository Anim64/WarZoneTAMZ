package com.example.standard.warzone;

import android.app.Activity;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.example.standard.warzone.SceneManager.SceneType;

/**
 * Created by Standard on 23.11.2017.
 */

public abstract class BaseScene extends Scene
{
    protected ResourceManager resourceManager;
    protected Engine engine;
    protected Activity activity;
    protected VertexBufferObjectManager vbom;
    protected Camera camera;

    public BaseScene()
    {
        resourceManager = ResourceManager.getInstance();
        engine = resourceManager.engine;
        activity = resourceManager.gameActivity;
        vbom = resourceManager.vbom;
        camera = resourceManager.camera;
        createScene();
    }

    public abstract void createScene();

    public abstract void removeScene();

    public abstract SceneType getSceneType();

    public abstract void OnBackKeyPressed();



}
