package com.kamigaku.dungeoncrawler.map.entity;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public interface IMapEntity {
    
    public void displayEntity();
    public void extractEntity(ArrayList<Vector2> coordinates);
    public ArrayList<Vector2> getTiles();
    
}
