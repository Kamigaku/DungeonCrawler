package com.kamigaku.dungeoncrawler.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.kamigaku.dungeoncrawler.component.PhysicsComponent;
import com.kamigaku.dungeoncrawler.listener.WrapperContactListener;
import com.kamigaku.dungeoncrawler.hud.HUD;
import com.kamigaku.dungeoncrawler.map.Map;

public abstract class ALevel implements ILevel {
    
    protected ResolutionFileResolver fileResolver;
    protected World world;
    protected HUD hud;
    protected OrthographicCamera camera;
    protected AssetManager assetManager;
    protected OrthogonalTiledMapRenderer mapRenderer;
    protected Box2DDebugRenderer debugRenderer;
    private InputMultiplexer multiplexer;
    protected Map map;
    
    @Override
    public void dispose() {
        this.world.dispose();
        this.mapRenderer.dispose();
        this.assetManager.dispose();
        this.debugRenderer.dispose();
        this.hud.dispose();
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
    
    @Override
    public Body addBody(BodyDef bodyDef) {
        return this.world.createBody(bodyDef);
    }

    @Override
    public void removeBody(PhysicsComponent physics) {
        this.world.destroyBody(physics.getBody());
    }
    
    public void baseLoading() {
        this.fileResolver = new ResolutionFileResolver(new InternalFileHandleResolver(), 
                new ResolutionFileResolver.Resolution(800, 480, "480"),
                new ResolutionFileResolver.Resolution(1280, 720, "720"), 
                new ResolutionFileResolver.Resolution(1920, 1080, "1080"));
        this.world = new World(new Vector2(0, 0), true);
        
        for(int x = 0; x < 1; x++) {
            for(int y = 0; y < 1; y++) {
                this.map = new Map(x, y);
                //this.map.displayMap();
            }
        }
        
        this.world.setContactListener(new WrapperContactListener());
        this.hud = new HUD();
        this.multiplexer = new InputMultiplexer();        
        this.debugRenderer = new Box2DDebugRenderer();
        this.camera = new OrthographicCamera();
    }

}
