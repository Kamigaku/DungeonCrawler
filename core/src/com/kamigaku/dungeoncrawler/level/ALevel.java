package com.kamigaku.dungeoncrawler.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.kamigaku.dungeoncrawler.constants.Constants;

public abstract class ALevel implements ILevel {
    
    protected World world;
    protected OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private AssetManager assetManager;
    private Box2DDebugRenderer debugRenderer;
    private InputMultiplexer multiplexer;
    
    @Override
    public void dispose() {
        this.world.dispose();
        this.mapRenderer.dispose();
        this.assetManager.dispose();
        this.debugRenderer.dispose();
    }

    @Override
    public void addInputProcessor(InputProcessor ip) {
        this.multiplexer.addProcessor(ip);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public AssetManager getAssetManager() {
        return this.assetManager;
    }

    @Override
    public OrthographicCamera getCamera() {
        return this.camera;
    }
    
    public void baseLoading() {
        this.world = new World(new Vector2(0, 0), true);
        this.multiplexer = new InputMultiplexer();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.camera.update();
        this.assetManager = new AssetManager();
        this.assetManager.load("sprites/player.png", Texture.class);
        this.assetManager.finishLoading();
    }

}
