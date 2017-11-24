package com.example.standard.warzone;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

/**
 * Created by Standard on 23.11.2017.
 */

public class SceneManager
{
    private BaseScene menuScene;
    private BaseScene loadingScene;
    private BaseScene gameScene;
    private BaseScene splashScene;

    private static final SceneManager singletonInstance = new SceneManager();

    private SceneType currentSceneType = SceneType.SCENE_SPLASH;

    private BaseScene currentScene;

    private Engine engine = ResourceManager.getInstance().engine;

    public enum SceneType
    {
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING,
        SCENE_SPLASH
    }

    public void setScene(BaseScene scene)
    {
        engine.setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
    }

    public void setScene(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_GAME:
            {
                setScene(gameScene);
                break;
            }

            case SCENE_LOADING:
            {
                setScene(loadingScene);
                break;
            }

            case SCENE_MENU:
            {
                setScene(menuScene);
                break;
            }

            case SCENE_SPLASH:
            {
                setScene(splashScene);
                break;
            }

            default:
                break;
        }
    }

    public static SceneManager getInstance()
    {
        return singletonInstance;
    }

    public BaseScene getCurrentScene()
    {
        return currentScene;
    }

    public SceneType getCurrentSceneType()
    {
        return currentSceneType;
    }

    public void createSplashScene(OnCreateSceneCallback onCreateSceneCallback)
    {
        ResourceManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        onCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    private void removeSplashScene()
    {
        ResourceManager.getInstance().unloadSplashScreen();
        splashScene.removeScene();
        splashScene = null;

    }


}
