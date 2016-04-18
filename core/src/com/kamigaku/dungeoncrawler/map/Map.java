/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.map;

import com.kamigaku.dungeoncrawler.map.entity.IMapEntity;
import java.util.ArrayList;

/**
 *
 * @author Kamigaku
 */
public class Map {
    
    public int seed;
    public ArrayList<IMapEntity> mapEntities;
    public int[][][] map;
    public int width;
    public int height;
    public int numberLevel;
    
    public void Map(int width, int height, int numberLevel) {
        this.width = width;
        this.height = height;
        this.numberLevel = numberLevel;
        this.map = new int[this.numberLevel][this.width][this.height];
    }
    
    public void addAnEntity(IMapEntity entity) {
        this.mapEntities.add(entity);
        // fetch the schema of the map and add it to the current map
    }
    
    //Display the map
    public void displayMap() {
        
    }
    
}
