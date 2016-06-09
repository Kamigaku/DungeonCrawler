package com.kamigaku.dungeoncrawler.map.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.tile.Tile;
import java.util.ArrayList;

public interface IMapEntity {
    
    public void displayEntity();
    public void extractEntity(ArrayList<Vector2> coordinates);
    public Tile[][] getTiles();
    public ArrayList<Vector2> getTilesPosition();
    public ArrayList<Vector2> getBorders();
    public void render(SpriteBatch batch);
    
}
