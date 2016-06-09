package com.kamigaku.dungeoncrawler.utility;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public abstract class Utility {

    public static ArrayList<Vector2> commonCoords(ArrayList<Vector2> v1, ArrayList<Vector2> v2) {
        ArrayList<Vector2> v3 = new ArrayList<Vector2>();
        for(Vector2 v : v1) {
            if(v2.contains(v))
                v3.add(v);
        }
        return v3;
    }
    
    public static boolean checkLinesSurrondings(char[][] map, int x_origin, 
                                                int y_origin, char search) {
        boolean xAxis = false;
        boolean yAxis = false;
        if(x_origin - 1 >= 0 && x_origin + 1 < map[y_origin].length)
            xAxis = (map[y_origin][x_origin + 1] == search && 
                    map[y_origin][x_origin - 1] == search);
        if(y_origin - 1 >= 0 && y_origin + 1 < map.length)
            yAxis = (map[y_origin + 1][x_origin] == search && 
                    map[y_origin - 1][x_origin] == search);
        return (xAxis || yAxis);
    }
    
}
