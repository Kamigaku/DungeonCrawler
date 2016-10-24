package com.kamigaku.dungeoncrawler.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Utility {
            
    public static boolean isInRange(Point origin, Point destination, ArrayList<Point> points) {
        for(int i = 0; i < points.size(); i++) {
            if(origin.x + points.get(i).x == destination.x && 
               origin.y + points.get(i).y == destination.y) 
                return true;
        }
        return false;
    }
    
    public static Point getMousePosition() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        Vector3 unprojected = LevelManager.getLevelManager().getCamera().unproject(new Vector3(mouseX, mouseY, 0));
        return new Point(Math.round(unprojected.x), Math.round(unprojected.y));
    }
    
    public static Point getMousePosition(int x, int y) {
        Vector3 unprojected = LevelManager.getLevelManager().getCamera().unproject(new Vector3(x, y, 0));
        return new Point(Math.round(unprojected.x), Math.round(unprojected.y));
    }
    
}
