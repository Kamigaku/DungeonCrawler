package com.kamigaku.dungeoncrawler.dungeongenerator.generator;

import com.kamigaku.dungeoncrawler.utility.Utility;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class GeneratorRoom {
    
    public int _height;
    public int _width;
    private long seed;
    public char[][] _map;
    private int _bottomleft_x;
    private int _bottomleft_y;
    private final Random _random;
    public boolean isValid = true;
    private ArrayList<Point> _localBorders;
    private ArrayList<Point> _worldBorders;
    
    public GeneratorRoom(long seed) {
        this.seed = seed;
        String seedString = String.valueOf(seed);
        this._random = new Random(seed);
        int height = Integer.parseInt("" + seedString.charAt(seedString.length() - 1)) + 
                    (Integer.parseInt("" + seedString.charAt(seedString.length() - 2)) * 10);
        int width = Integer.parseInt("" + seedString.charAt(seedString.length() - 3)) + 
                    (Integer.parseInt("" + seedString.charAt(seedString.length() - 4)) * 10);
        if(width != 0 && height != 0) 
            this.generateRoom(width, height);
        else
            isValid = false;
    }
    
    private void generateRoom(int width, int height) {
        char[][] map = new char[height][width];
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                map[i][j] = '#';
            }
        }
        ArrayList<Point> points = this.generatePoints(map, width, height);
        this.connectAllPoints(map, points);
        this.fillEmptySpace(map);
        this.clearMap(map);
        this.connectRoom(map);
        this.reduceMap(map);
        this.generateBorders();
    }
    
    private ArrayList<Point> generatePoints(char[][] map, int width, int height) {
        System.out.println("Width : " + width + " - Height : " + height);
        ArrayList<Point> points = new ArrayList<Point>();
        for(int i = 0; i < 6; i++) {
            int cX = Utility.nextInt(this._random, 0, width);
            int cY = Utility.nextInt(this._random, 0, height);
            points.add(new Point(cX, cY));
            map[cY][cX] = 'W';
        }
        return points;
    }
    
    private void connectAllPoints(char[][] map, ArrayList<Point> points) {
        Point firstPoint = new Point(points.get(0));
        Point currentPoint = points.get(0);
        while(points.size() > 1) {
            points.remove(currentPoint);
            Point closestPoint = null;
            int distanceMin = 9999;
            for(int i = 0; i < points.size(); i++) {
                    int distance = Math.abs(points.get(i).y - currentPoint.y) +
                                   Math.abs(points.get(i).x - currentPoint.x);
                    if(distanceMin > distance) {
                        distanceMin = distance;
                        closestPoint = points.get(i);
                    }
            }
            drawBetween2Points(map, currentPoint, closestPoint);
        }
        drawBetween2Points(map, currentPoint, firstPoint);
    }
    
    private void drawBetween2Points(char[][] map, Point p1, Point p2) {
        int directionY = (p2.y - p1.y) < 0 ? -1 : ((p2.y - p1.y) == 0 ? 0 : 1);
        int directionX = (p2.x - p1.x) < 0 ? -1 : ((p2.x - p1.x) == 0 ? 0 : 1);
        map[p1.y][p1.x] = 'W';
        while(directionX != 0 && directionY != 0) {
            int xOrY = Utility.nextInt(this._random, 0, 2);
            int indicX = 0;
            int indicY = 0;
            if(xOrY == 1)
                indicX = Utility.nextInt(this._random, 0, 2) * directionX;
            else
                indicY = Utility.nextInt(this._random, 0, 2) * directionY;
            p1.x += indicX;
            p1.y += indicY;
            directionY = (p2.y - p1.y) < 0 ? -1 : ((p2.y - p1.y) == 0 ? 0 : 1);
            directionX = (p2.x - p1.x) < 0 ? -1 : ((p2.x - p1.x) == 0 ? 0 : 1);
            map[p1.y][p1.x] = 'W';
        }
        if(directionX == 0) {
            while((p2.y - p1.y) != 0) {
                    p1.y += (p2.y - p1.y) / Math.abs((p2.y - p1.y));
                    map[p1.y][p1.x] = 'W';
            }
        }
        else {
            while((p2.x - p1.x) != 0) {
                    p1.x += (p2.x - p1.x) / Math.abs((p2.x - p1.x));
                    map[p1.y][p1.x] = 'W';
            }
        }
    }
    
    private void fillEmptySpace(char[][] map) {
        for(int y = 0; y < map.length; y++) {
            boolean foundX = false;
            boolean startManuver = false;
            Point init = null;
            for(int x = 0; x < map[y].length; x++) {
                if(map[y][x] == 'W') {
                    if(foundX && startManuver) {
                        for(int i = init.x + 1; i < x; i++) {
                            map[y][i] = ' ';
                        }
                        startManuver = false;
                        init = new Point(x, y);
                    }
                    else { 
                        foundX = true;
                        init = new Point(x, y);
                    }
                }
                if(foundX && map[y][x] == '#') {
                    startManuver = true;
                }
            }
        }
    }
    
    private void clearMap(char[][] map) {
        if(seed == 8386418694265284948l) {
            Utility.displayEntity(map);
            System.out.println("========" + this.seed);
        }
        // Clear map from top to bottom
        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map[y].length; x++) {
                if(map[y][x] == ' ' && (y == 0 || y == map.length - 1 || Utility.checkXorYSurrondings(map, x, y, '#')))
                    map[y][x] = 'W';
            }
        }
        if(seed == 8386418694265284948l) {
            Utility.displayEntity(map);
            System.out.println("========" + this.seed);
        }
        // Clear map from bottom to top
        for(int y = map.length - 1; y >= 0; y--) {
            for(int x = 0; x < map[y].length; x++) {
                if(map[y][x] == ' ' && (y == 0 || y == map.length - 1 || Utility.checkXorYSurrondings(map, x, y, '#')))
                    map[y][x] = 'W';
            }
        }
        if(seed == 8386418694265284948l) {
            Utility.displayEntity(map);
            System.out.println("========" + this.seed);
        }
        // Clear walls that have no surrondings
        for(int y = map.length - 1; y >= 0; y--) {
            for(int x = 0; x < map[y].length; x++) {
                if(map[y][x] == 'W' && !Utility.checkSquareSurrondings(map, x, y, ' '))
                    map[y][x] = '#';
            }
        }
        if(seed == 8386418694265284948l) {
            Utility.displayEntity(map);
            System.out.println("========" + this.seed);
        }
    }
    
    private void connectRoom(char[][] map) {
        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map[y].length; x++) {
                if(map[y][x] == 'W') {
                    int result = Utility.checkDiagonalsSurrondings(map, x, y, ' ');
                    switch(result) {
                        case 1:
                        case -1:
                            map[y][x] = map[y][x - 1] = map[y][x + 1] = ' ';
                            map[y + 1][x + (1 * result)] = map[y + 1][x + (2 * result)] = 'W';
                            map[y - 1][x + (-1 * result)] = map[y - 1][x + (-2 * result)] = 'W';
                            break;
                        case 2:
                            //throw new UnsupportedOperationException();
                            break;
                    }
                }
            }
        }
    }
    
    private void reduceMap(char[][] map) {
        int heightMinRoom = -1;
        int heightMaxRoom = map.length;
        int widthMinRoom = -1;
        int widthMaxRoom = -1;
        
        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map[y].length; x++) {
                if(map[y][x] == 'W') {
                    if(heightMinRoom == -1)
                        heightMinRoom = y;
                    heightMaxRoom = y;
                    if(widthMinRoom == -1)
                        widthMinRoom = x;
                    if(x < widthMinRoom) widthMinRoom = x;
                    if(x > widthMaxRoom) widthMaxRoom = x;
                    
                }
            }
        }
        this._map = new char[heightMaxRoom - heightMinRoom + 1][widthMaxRoom - widthMinRoom + 1];
        for(int y = heightMinRoom; y <= heightMaxRoom; y++) {
            for(int x = widthMinRoom; x <= widthMaxRoom; x++) {
                try {
                    this._map[y - heightMinRoom][x - widthMinRoom] = map[y][x];
                } catch(ArrayIndexOutOfBoundsException aoe) {
                    this.isValid = false;
                    break;
                }
            }
        }
        this._height = heightMaxRoom - heightMinRoom + 1;
        this._width = widthMaxRoom - widthMinRoom + 1;
    }
    
    private void generateBorders() {
        this._localBorders = new ArrayList<Point>();
        for(int y = 0; y < this._map.length; y++) {
            for(int x = 0; x < this._map[y].length; x++) {
                if(this._map[y][x] == 'W')
                    this._localBorders.add(new Point(x, y));
            }
        }
    }
    
    public void setOriginXY(int x, int y) {
        this._bottomleft_x = x;
        this._bottomleft_y = y;
        this._worldBorders = new ArrayList<Point>(this._localBorders);
        for(int i = 0; i < this._worldBorders.size(); i++) {
            this._worldBorders.get(i).x += x;
            this._worldBorders.get(i).y += y;
        }
    }
    
    public Point getOriginXY() {
        return new Point(this._bottomleft_x, this._bottomleft_y);
    }
    
    public ArrayList<Point> getLocalBorders() {
        return this._localBorders;
    }
    
    public ArrayList<Point> getWorldBorders() {
        return this._worldBorders;
    }
    
}
