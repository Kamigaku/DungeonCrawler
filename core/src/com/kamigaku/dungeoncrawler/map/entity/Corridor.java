package com.kamigaku.dungeoncrawler.map.entity;

import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.map.entity.AMapEntity;
import java.util.ArrayList;

public class Corridor extends ACorridor {

    public Corridor(ArrayList<Vector2> coordinates, int x, int y, int width, int height) {
        this.widthRoom = width;
        this.heightRoom = height;
        this.x = x;
        this.y = y;
        this.neighbors = new ArrayList<AMapEntity>();
        super.extractEntity(coordinates);
    }
    
}
