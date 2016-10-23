package com.kamigaku.dungeoncrawler.utility;

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
    
}
