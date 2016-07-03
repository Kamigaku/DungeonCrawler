package com.kamigaku.dungeoncrawler.utility;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Random;

public abstract class Utility {
    
    public static void fillArray(char[][] obj, char filler) {
        for(int i = 0; i < obj.length; i++) {
            for(int j = 0; j < obj[i].length; j++) {
                obj[i][j] = filler;
            }
        }
    }

    public static ArrayList<Vector2> commonCoords(ArrayList<Vector2> v1, ArrayList<Vector2> v2) {
        ArrayList<Vector2> v3 = new ArrayList<Vector2>();
        for(Vector2 v : v1) {
            if(v2.contains(v))
                v3.add(v);
        }
        return v3;
    }
    
        public static boolean checkLineSurrondings(char[][] map, int x_origin, 
                                                int y_origin, char search) {
        boolean xAxis = false;
        boolean yAxis = false;
        if(x_origin - 1 >= 0 && x_origin + 1 < map[y_origin].length)
            xAxis = (map[y_origin][x_origin + 1] == search || 
                    map[y_origin][x_origin - 1] == search);
        if(y_origin - 1 >= 0 && y_origin + 1 < map.length)
            yAxis = (map[y_origin + 1][x_origin] == search || 
                    map[y_origin - 1][x_origin] == search);
        return (xAxis || yAxis);
    }
    
    public static boolean checkSquareSurrondings(char[][] map, int x_origin,
                                                 int y_origin, char search) {
        boolean lines = Utility.checkLineSurrondings(map, x_origin, y_origin, search);
        if(x_origin - 1 >= 0) {
            if(y_origin - 1 >= 0 && map[y_origin - 1][x_origin - 1] == search)
                return true;
            if(y_origin + 1 < map.length && map[y_origin + 1][x_origin - 1] == search)
                return true;
        }
        if(x_origin + 1 < map[y_origin].length) {
            if(y_origin - 1 >= 0 && map[y_origin - 1][x_origin + 1] == search)
                return true;
            else if(y_origin + 1 < map.length && map[y_origin + 1][x_origin + 1] == search)
                return true;
        }
        return lines;
    }
    
    public static int checkDiagonalsSurrondings(char[][] map, int x_origin,
                                                int y_origin, char search) {
        boolean left_top_2_right_bot = false;
        boolean right_top_2_left_bot = false;
        
        if(x_origin - 1 >= 0 && y_origin + 1 < map.length &&
           y_origin - 1 >= 0 && x_origin + 1 < map[y_origin - 1].length) { // top left 2 bottom right
            if(map[y_origin + 1][x_origin - 1] == search && 
               map[y_origin - 1][x_origin + 1] == search &&
               !Utility.checkLineSurrondings(map, x_origin, y_origin, ' '))
                left_top_2_right_bot = true;
            if(map[y_origin + 1][x_origin + 1] == search && 
               map[y_origin - 1][x_origin - 1] == search &&
               !Utility.checkLineSurrondings(map, x_origin, y_origin, ' '))
                right_top_2_left_bot = true;
        }
        if(left_top_2_right_bot && right_top_2_left_bot) return 2;
        else if(left_top_2_right_bot) return 1;
        else if(right_top_2_left_bot) return -1;
        return 0;
    }
        
    public static int nextInt(Random random, int min, int max) {
        return random.nextInt(max-min) + min;
    }
    
    public static void displayEntity(char[][] map) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
    
    public static void displayEntityReverseY(char[][] map) {
        for(int i = map.length - 1; i >= 0; i--) {
            for(int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
    
}
