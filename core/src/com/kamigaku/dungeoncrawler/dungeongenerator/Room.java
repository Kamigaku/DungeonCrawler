/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.dungeongenerator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import com.kamigaku.dungeoncrawler.tile.*;
import com.kamigaku.dungeoncrawler.utility.Utility;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Kamigaku
 */
public class Room {
     
    private final ArrayList<Vector2> _bordersWithoutAngle;
    private ArrayList<Connection> _connections;
    private final ArrayList<Layer> _layers;
    
    public boolean isEntry = false;
    public boolean isExit = false;
    
    public Room(ArrayList<Point> floor, ArrayList<Point> walls, char[][] mainMap, HashMap<Integer, Tile> wallsMap) {
        this._layers = new ArrayList<Layer>();
        this._bordersWithoutAngle = new ArrayList<Vector2>();
        
        this._layers.add(0, new Layer(Layer.GROUND));
        for(int i = 0; i < floor.size(); i++) {
            this._layers.get(0).addTile(new Ground("sprites/ground.png", floor.get(i).x, floor.get(i).y));
        }
        
        //sthis._layers.add(1, new Layer(Layer.WALL));
        for(int i = 0; i < walls.size(); i++) {
            //this._layers.get(1).addTile(new Wall("sprites/wall.png", walls.get(i).x, walls.get(i).y));
            if(!wallsMap.containsKey(walls.get(i).x + (walls.get(i).y * mainMap[0].length))) {
                wallsMap.put(walls.get(i).x + (walls.get(i).y * mainMap[0].length), 
                        new Wall("sprites/wall.png", walls.get(i).x, walls.get(i).y));
            }
            if(!Utility.checkXandYSurrondings(mainMap, walls.get(i).x, walls.get(i).y, 'W')) {
                this._bordersWithoutAngle.add(new Vector2(walls.get(i).x, walls.get(i).y));
            }
        }
    }
    
    public void render(SpriteBatch batch) {
        for(int i = 0; i < this._layers.size(); i++) {
            ArrayList<Tile> layerTiles = this._layers.get(i).getTiles();
            for(int j = 0; j < layerTiles.size(); j++) {
                layerTiles.get(j).getGraphicsComponent().update(batch, 
                    layerTiles.get(j).x, layerTiles.get(j).y);
            }
        }
    }
    
    ////// ADD FUNCTION
    
    public void addTileLayer(int index, String layerName, ArrayList<Tile> tiles) {
        this._layers.add(index, new Layer(layerName, tiles));
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
        ArrayList<Tile> tiles = l.getTiles();
        for(int i = tiles.size() - 1; i >= 0; i--) {
            Vector2 curPos = tiles.get(i).getPosition();
            if(curPos.x == pos.x && curPos.y == pos.y) {
                Tile t = tiles.get(i);
                if(t.getPhysicsComponent() != null)
                    LevelManager.getLevelManager().getLevel().removeBody(t.getPhysicsComponent());
                tiles.remove(i);
            }
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
    
    public ArrayList<Vector2> getBordersWithoutAngles() {
        return this._bordersWithoutAngle;
    }
        
    public Tile getRandomFloorTiles() {
        Layer ground = this.getLayer(Layer.GROUND);
        return ground.getTile(Utility.nextInt(new Random(
                ground.getTiles().size()), 0, ground.getTiles().size()));
    }
    
}
