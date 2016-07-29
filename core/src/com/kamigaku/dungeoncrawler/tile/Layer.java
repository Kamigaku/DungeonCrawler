package com.kamigaku.dungeoncrawler.tile;

import java.util.ArrayList;
import java.util.HashMap;

public class Layer {
    
    // Changer Layer, dans le constructeur ajouter la taille X et Y de la map
    
    
    public static final String WALL = "Wall";
    public static final String GROUND = "Ground";
 
    private final String _title;
    private final ArrayList<Tile> _tiles;
    private final HashMap<Integer, Tile> _ttiles;
    
    public Layer(String title) {
        this._title = title;
        this._tiles = new ArrayList<Tile>();
        this._ttiles = new HashMap<Integer, Tile>();
    }
    
    public Layer(String title, ArrayList<Tile> tiles) {
        this._title = title;
        this._tiles = tiles;
        this._ttiles = new HashMap<Integer, Tile>();
    }
    
    public void addTile(Tile t) {
        this._tiles.add(t);
        //this._ttiles.put(, t)
    }
    
    public void addTiles(ArrayList<Tile> tiles) {
        this._tiles.addAll(tiles);
    }
    
    public ArrayList<Tile> getTiles() {
        return this._tiles;
    }
    
    public Tile getTile(int index) {
        return this._tiles.get(index);
    }
    
    public String getTitle() {
        return this._title;
    }
    
    
}
