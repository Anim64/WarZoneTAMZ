package com.example.standard.warzone;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

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

    public void onCreateResources(OnCreateResourcesCallback onCreateResourcesCallback)
    {
        ResourceManager.initManager(mEngine, this, camera, getVertexBufferObjectManager());
        resourceManager = ResourceManager.getInstance();
        onCreateResourcesCallback.onCreateResourcesFinished();

    }

    public void onCreateScene(OnCreateSceneCallback onCreateSceneCallback)
    {
        SceneManager.getInstance().createSplashScene(onCreateSceneCallback);
    }

    public void onPopulateScene(Scene scene, OnPopulateSceneCallback onPopulateSceneCallback)
    {
        mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback()
        {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);

                //ResourceManager.getInstance().loadMenuResources();

            }
        }));
        onPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public Engine onCreateEngine(EngineOptions engOpt)
    {
        return new LimitedFPSEngine(engOpt, 60);
    }
}
