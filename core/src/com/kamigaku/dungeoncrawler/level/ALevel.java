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
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.listener.WrapperContactListener;
import com.kamigaku.dungeoncrawler.hud.HUD;
import com.kamigaku.dungeoncrawler.tile.Ground;
import com.kamigaku.dungeoncrawler.tile.GroundSelector;
import com.kamigaku.dungeoncrawler.tile.Layer;
import com.kamigaku.dungeoncrawler.tile.Tileset;
import com.kamigaku.dungeoncrawler.tile.Wall;
import com.kamigaku.dungeongenerator.Map;
import com.kamigaku.dungeongenerator.generator.GeneratorMap;
import java.util.ArrayList;

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
    protected ArrayList<Layer> layers;
    protected ArrayList<IEntity> _entities;
    
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
    public Map getMap() {
        return this.map;
    }

    @Override
    public Layer getLayer(String layerTitle) {
        for(int i = 0; i < this.layers.size(); i++) {
            if(this.layers.get(i).getTitle().equals(layerTitle))
                return this.layers.get(i);
        }
        return null;
    }
    
    @Override
    public ArrayList<IEntity> getEntities() {
        return this._entities;
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
        Tileset ts = new Tileset("sprites/walls.png", 32, 32);
        this.world = new World(new Vector2(0, 0), true);
        int x = 1;
        int y = 15;
        this.map = new GeneratorMap(x * 1000 + y).getMap();
        this.layers = new ArrayList<Layer>();
        this.layers.add(new Layer(Layer.GROUND, this.map.getMap()[0].length, 
                                  this.map.getMap().length));
        this.layers.add(new Layer(Layer.WALL, this.map.getMap()[0].length, 
                                  this.map.getMap().length));
        this.layers.add(new Layer(Layer.GROUND_SELECTOR, this.map.getMap()[0].length, 
                                  this.map.getMap().length, false));
        for(int _y = 0; _y < this.map.getMap().length; _y++) {
            for(int _x = 0; _x < this.map.getMap()[_y].length; _x++) {
                if(this.map.getMap()[_y][_x] == 'W') {
                    
                    char top = this.map.getMap()[_y + 1][_x];
                    char bot = this.map.getMap()[_y - 1][_x];
                    char left = this.map.getMap()[_y][_x - 1];
                    char right = this.map.getMap()[_y][_x + 1];
                    char topLeft = this.map.getMap()[_y + 1][_x - 1];
                    char topRight = this.map.getMap()[_y + 1][_x + 1];
                    char botLeft = this.map.getMap()[_y - 1][_x - 1];
                    char botRight = this.map.getMap()[_y - 1][_x + 1];
                    
                    if(top == 'W' && bot == 'W' && left != 'W' && right != 'W' && left != 'D' && right != 'D') { // |                        
                        if(left == ' ' && right == ' ') {
                            
                        }
                        else if(right == ' ')
                            this.layers.get(1).addTile(new Wall(ts.sprites[4], _x, _y));
                        else if(left == ' ')
                            this.layers.get(1).addTile(new Wall(ts.sprites[6], _x, _y));
                    }
                    else if(left == 'W' && right == 'W' && bot != 'W' && top != 'W' && bot != 'D' && top != 'D') { // -
                        if(top == ' ' && bot == ' ') {
                            
                        }
                        else if(top == ' ')
                            this.layers.get(1).addTile(new Wall(ts.sprites[9], _x, _y));
                        else if(bot == ' ')
                            this.layers.get(1).addTile(new Wall(ts.sprites[1], _x, _y));
                    }
                    else if(left == 'W' && bot == 'W' && top != 'W' && right != 'W' && top != 'D' && right != 'D') {  // ╗
                        if(botLeft == ' ' && topRight == ' ') {
                            
                        }
                        else if(topRight == ' ') {
                            this.layers.get(1).addTile(new Wall(ts.sprites[13], _x, _y));
                        }
                        else if(botLeft == ' ') {
                            this.layers.get(1).addTile(new Wall(ts.sprites[2], _x, _y));
                        }
                    }
                    else if(right == 'W' && bot == 'W' && top != 'W' && left != 'W' && top != 'D' && left != 'D') { // ╔
                        if(botRight == ' ' && topLeft == ' ') {
                            
                        }
                        else if(botRight == ' ') {
                            this.layers.get(1).addTile(new Wall(ts.sprites[0], _x, _y));
                        }
                        else if(topLeft == ' ') {
                            this.layers.get(1).addTile(new Wall(ts.sprites[12], _x, _y));
                        }
                    }
                    else if(top == 'W' && right == 'W' && left != 'W' && bot != 'W' && left != 'D' && bot != 'D') { // ╚
                        if(botLeft == ' ' && topRight == ' ') {
                            
                        }
                        else if(topRight == ' ') {
                            this.layers.get(1).addTile(new Wall(ts.sprites[8], _x, _y));
                        }
                        else if(botLeft == ' ') {
                            this.layers.get(1).addTile(new Wall(ts.sprites[15], _x, _y));
                        }
                    }
                    else if(top == 'W' && left == 'W' && bot != 'W' && right != 'W' && bot != 'D' && right != 'D') { // ╝
                        if(botRight == ' ' && topLeft == ' ') {
                            
                        }
                        else if(topLeft == ' ') {
                            this.layers.get(1).addTile(new Wall(ts.sprites[10], _x, _y));
                        }
                        else if(botRight == ' ') {
                            this.layers.get(1).addTile(new Wall(ts.sprites[14], _x, _y));
                        }
                    }
                    //this.layers.get(1).addTile(new Wall("sprites/wall.png", _x, _y)); break;
                }
                else if(this.map.getMap()[_y][_x] == ' ') {
                    this.layers.get(1).addTile(new Ground(ts.sprites[5], _x, _y));
                    this.layers.get(2).addTile(new GroundSelector("sprites/ground_selector.png", _x, _y));
                }
                else if(this.map.getMap()[_y][_x] == 'D') {
                    
                }
            }
        }
        //ts.dispose();
        this.world.setContactListener(new WrapperContactListener());
        this.hud = new HUD();
        this.multiplexer = new InputMultiplexer();        
        this.debugRenderer = new Box2DDebugRenderer();
        this.camera = new OrthographicCamera();
    }

}
