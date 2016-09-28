package com.kamigaku.dungeoncrawler.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class Layer {
    
    private int _sizeX;
    private int _sizeY;
    
    public static final String WALL = "Wall";
    public static final String GROUND = "Ground";
    public static final String GROUND_SELECTOR = "GroundSelector";
 
    private String _title;
    private final HashMap<Integer, Tile> _tiles;
    public boolean render = true;
    
    public Layer(String title, int sizeX, int sizeY) {
        commonInit(title, sizeX, sizeY);
        this._tiles = new HashMap<Integer, Tile>();
    }
    
    public Layer(String title, int sizeX, int sizeY, boolean render) {
        commonInit(title, sizeX, sizeY);
        this._tiles = new HashMap<Integer, Tile>();
        this.render = render;
    }
    
    public Layer(String title, int sizeX, int sizeY, HashMap<Integer, Tile> tiles) {
        commonInit(title, sizeX, sizeY);
        this._tiles = tiles;
    }
    
    private void commonInit(String title, int sizeX, int sizeY) {
        this._title = title;
        this._sizeX = sizeX;
        this._sizeY = sizeY;
    }
    
    public void render(SpriteBatch batch) {
        if(this.render) {
            for(Integer key : this._tiles.keySet()) {
                this._tiles.get(key).getGraphicsComponent().update(batch, 
                    this._tiles.get(key).x, this._tiles.get(key).y);
            }
        }
    }
    
    public void addTile(Tile t) {
        this._tiles.put(calculatePosition(t.x, t.y), t);
    }
    
    public void addTiles(ArrayList<Tile> tiles) {
        for(int i = 0; i < tiles.size(); i++)
            this._tiles.put(calculatePosition(tiles.get(i).x, tiles.get(i).y), 
                    tiles.get(i));
    }
    
    public void removeTileAtPosition(Point pos) {
        int key = this.calculatePosition(pos.x, pos.y);
        Tile t = this._tiles.get(key);
        if(t != null) {
            if(t.getPhysicsComponent() != null) {
                LevelManager.getLevelManager().getLevel().removeBody(t.getPhysicsComponent());
            }
            this._tiles.remove(key);
        }
    }
    
    public HashMap<Integer, Tile> getTiles() {
        return this._tiles;
    }
    
    public Tile getTile(int index) {
        ArrayList<Integer> keys = new ArrayList<Integer>(this._tiles.keySet());
        return this._tiles.get(keys.get(index));
    }
    
    public String getTitle() {
        return this._title;
    }
    
    public boolean containsTile(int key) {
        return this._tiles.containsKey(key);
    }
    
    public int calculatePosition(int posX, int posY) {
        return (posY * this._sizeX) + posX;
    }
    
    
}
