/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.dungeongenerator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.tile.*;
import com.kamigaku.dungeoncrawler.utility.Utility;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Kamigaku
 */
public class Room {
 
    private ArrayList<Tile> _floorTiles;
    private ArrayList<Tile> _wallTiles;
    public boolean isEntry = false;
    public boolean isExit = false;
    
    public Room(ArrayList<Point> floor, ArrayList<Point> walls) {
        this._floorTiles = new ArrayList<Tile>();
        this._wallTiles = new ArrayList<Tile>();
        for(int i = 0; i < floor.size(); i++) {
            this._floorTiles.add(new Ground("sprites/ground.png", floor.get(i).x, floor.get(i).y));
        }
        for(int i = 0; i < walls.size(); i++) {
            this._wallTiles.add(new Wall("sprites/wall.png", walls.get(i).x, walls.get(i).y));
        }
    }
    
    public void render(SpriteBatch batch) {
        for(int i = 0; i < this._floorTiles.size(); i++) {
            this._floorTiles.get(i).getGraphicsComponent().update(batch, 
                    this._floorTiles.get(i).x, this._floorTiles.get(i).y);
        }
        for(int i = 0; i < this._wallTiles.size(); i++) {
            this._wallTiles.get(i).getGraphicsComponent().update(batch, 
                    this._wallTiles.get(i).x, this._wallTiles.get(i).y);
        }
    }
    
    public ArrayList<Tile> getFloorTiles() {
        return this._floorTiles;
    }
    
    public ArrayList<Tile> getWallTiles() {
        return this._wallTiles;
    }
    
    public Tile getRandomFloorTiles() {
        return this._floorTiles.get(Utility.nextInt(new Random(this._floorTiles.size()), 0, this._floorTiles.size()));
    }
    
}
