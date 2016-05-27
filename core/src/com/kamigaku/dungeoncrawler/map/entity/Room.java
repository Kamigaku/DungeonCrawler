/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.map.entity;

import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.map.entity.AMapEntity;
import java.util.ArrayList;

/**
 *
 * @author Kamigaku
 */
public class Room extends ARoom {
    
    public Room(ArrayList<Vector2> coordinates, int x, int y, int width, int height) {
        this.widthRoom = width;
        this.heightRoom = height;
        this.x = x;
        this.y = y;
        this.neighbors = new ArrayList<Connection>();
        super.extractEntity(coordinates);
    }
    
}
