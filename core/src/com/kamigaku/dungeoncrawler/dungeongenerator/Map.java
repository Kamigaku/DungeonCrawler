package com.kamigaku.dungeoncrawler.dungeongenerator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import com.kamigaku.dungeoncrawler.tile.Layer;
import com.kamigaku.dungeoncrawler.tile.Tile;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Kamigaku
 */
public class Map {
    
    private char[][] _map;
    private final ArrayList<Room> _rooms;
    private final ArrayList<Layer> _layers;
    private Room _entryRoom;
    private Room _exitRoom;
    
    public Map(char[][] map, ArrayList<Room> rooms, ArrayList<Layer> layers) {
        this._rooms = rooms;
        this._layers = layers;
        this._map = map;
        for(int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).isEntry)
                _entryRoom = rooms.get(i);
            if(rooms.get(i).isExit)
                _exitRoom = rooms.get(i);
        }
    }
    
    public void render(SpriteBatch batch) {
        for(int i = 0; i < this._layers.size(); i++) {
            this._layers.get(i).render(batch);
        }
    }
    
        public void addTileLayer(int index, String layerName, HashMap<Integer, Tile> tiles, 
            int mapSizeX, int mapSizeY) {
        this._layers.add(index, new Layer(layerName, mapSizeX, mapSizeY, tiles));
    }
    
    public void addTileToLayer(int index, Tile tile) {
        this._layers.get(index).addTile(tile);
    }
    
    public void addTileToLayer(String layerName, Tile tile) {
        for(int i = 0; i < this._layers.size(); i++) {
            if(this._layers.get(i).getTitle().equals(layerName)) {
                this._layers.get(i).addTile(tile);
                return;
            }
        }
    }
    
    ////// REMOVE FUNCTION
    
    public void removeTilesAtPosition(Vector2 pos, boolean allLayer, String layerName) {
        if(allLayer) {
            for(int i = 0; i < this._layers.size(); i++) {
                removeTilesFromLayer(this._layers.get(i), pos);
            }
        }
        else {
            Layer l = this.getLayer(layerName);
            removeTilesFromLayer(l, pos);
        }
    }
    
    public void removeTilesFromLayer(Layer l, Vector2 pos) {
        HashMap<Integer, Tile> tiles = l.getTiles();
        int key = l.calculatePosition((int)pos.x, (int)pos.y);
        Tile t = tiles.get(key);
        if(t != null) {
            if(t.getPhysicsComponent() != null) {
                LevelManager.getLevelManager().getLevel().removeBody(t.getPhysicsComponent());
            }
            tiles.remove(key, t);
        }
    }
    
    ////// GET FUNCTION
    
    public Layer getLayer(String layerName) {
        for(int i = 0; i < this._layers.size(); i++) {
            if(this._layers.get(i).getTitle().equals(layerName)) {
                return this._layers.get(i);
            }
        }
        return null;
    }

    
    public Room getEntryRoom() {
        return this._entryRoom;
    }
    
    public Room getExitRoom() {
        return this._exitRoom;
    }
    
}
