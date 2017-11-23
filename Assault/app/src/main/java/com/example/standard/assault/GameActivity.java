package com.example.standard.assault;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

public class GameActivity extends BaseGameActivity {

    private Camera camera;
    private ResourceManager resourceManager;

    public EngineOptions onCreateEngineOptions()
    {
        camera = new Camera(0,0,800, 480);
        EngineOptions engOpt = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(800, 480), camera);
        engOpt.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
        engOpt.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engOpt;
    }

    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
    {
        ResourceManager.initManager(mEngine, this, camera, getVertexBufferObjectManager());
        resourceManager = ResourceManager.getInstance();
        pOnCreateResourcesCallback.onCreateResourcesFinished();

    }

    public void OnCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException
    {

    }

    public void OnPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
    {

    }

    @Override
    public Engine onCreateEngine(EngineOptions pEngOpt)
    {
        return new LimitedFPSEngine(pEngOpt, 60);
    }
}
