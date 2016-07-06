/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.dungeongenerator;

import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.dijkstra.Node;
import com.kamigaku.dungeoncrawler.dijkstra.Square;
import com.kamigaku.dungeoncrawler.utility.Utility;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Kamigaku
 */
public class GeneratorMap {
    
    private char[][] _map;
    private ArrayList<GeneratorRoom> _rooms;
    private long _seed;
    private Random _random;
    
    private Map m;
    private ArrayList<Room> tRoom;
    
    public GeneratorMap(long seed) {
        this._seed = seed;
        this._random = new Random(seed);
        this._rooms = new ArrayList<GeneratorRoom>();
        String infoMapSeed = "" + this._random.nextLong();
        
        if(infoMapSeed.charAt(0) == '-') {
            infoMapSeed = infoMapSeed.substring(1);
        }

        // Map type
        int type = Integer.parseInt("" + infoMapSeed.charAt(infoMapSeed.length() - 1));

        // Nombre de room
        // [10 ; 99]
        int numberRoom = Utility.nextInt(_random, 30, 50);
        for(int i = 0; i < numberRoom; i++) {
            GeneratorRoom r = new GeneratorRoom(this._random.nextLong());
            if(r.isValid)
                this._rooms.add(r);
            else
                i--;
        }
        
        Point sizeMap = sumSizeRooms(_rooms);
        char[][] map = new char[sizeMap.y][sizeMap.x];
        Utility.fillArray(map, '#');
        ArrayList<Point> borders = new ArrayList<Point>();
        GeneratorRoom r1 = this._rooms.get(0);
        int x = (map[0].length / 2) - (r1._width / 2);
        int y = (map.length / 2) - (r1._height / 2);
        for(int i = 0; i < this._rooms.size(); i++) {
            this.placeRoom(map, this._rooms.get(i), x, y);
            borders.addAll(this._rooms.get(i).getWorldBorders());
            Point p = borders.get(Utility.nextInt(_random, 0, borders.size()));
            x = p.x;
            y = p.y;
        }
        borders.clear();
        this.reduceMap(map);
        Utility.displayEntity(_map);
        this.createRooms();
        this.m = new Map(this._map, this.tRoom);
    }
        
    private Point sumSizeRooms(ArrayList<GeneratorRoom> rooms) {
        int height = 0;
        int width = 0;
        for(int i = 0; i < rooms.size(); i++) {
            height += rooms.get(i)._height;
            width += rooms.get(i)._width;
        }
        return new Point(width, height);
    }
    
    private void placeRoom(char[][] map, GeneratorRoom r, int x_ori, int y_ori) {
        for(int y = 0; y < r._map.length; y++) {
            for(int x = 0; x < r._map[y].length; x++) {
                if(r._map[y][x] != '#') {
                    map[y + y_ori][x + x_ori] = r._map[y][x];
                }
            }
        }
        r.setOriginXY(x_ori, y_ori);
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
                this._map[y - heightMinRoom][x - widthMinRoom] = map[y][x];
            }
        }
        
        // Second part
        for(int y = 1; y < this._map.length - 1; y++) {
            for(int x = 1; x < this._map[y].length - 1; x++) {
                if(this._map[y][x] == 'W') {
                    char[][] square = new char[3][3];
                    for(int i = 0; i < 3; i++)
                        for(int j = 0; j < 3; j++)
                            square[i][j] = this._map[y + (-1 + i)][x + (-1 + j)];
                    Square preUpdate = new Square(square.length);
                    preUpdate.applycharMap(square);
                    square[1][1] = ' ';
                    Square postUpdate = new Square(square.length);
                    postUpdate.applycharMap(square);
                    if(Square.compareSquare(preUpdate, postUpdate)) {
                        if(Utility.checkLineSurrondings(this._map, x, y, ' '))
                            this._map[y][x] = ' ';
                        else
                            this._map[y][x] = '#';
                    }
                }
            }
        }
    }
    
    private void createRooms() {
        Node[] nodes = new Node[this._map.length * this._map[0].length];
        for(int y = 0; y < this._map.length; y++) {
            for(int x = 0; x < this._map[y].length; x++) {
                if(this._map[y][x] == ' ') {
                    int value = XYValue(x, y);
                    nodes[value] = new Node(value);
                    // Droite
                    if(x + 1 < this._map[y].length && (this._map[y][x + 1] == ' ' || this._map[y][x + 1] == 'W'))
                        nodes[value].addNeighbors(XYValue(x + 1, y));
                    // Gauche
                    if(x - 1 >= 0 && (this._map[y][x - 1] == ' ' || this._map[y][x - 1] == 'W'))
                        nodes[value].addNeighbors(XYValue(x - 1, y));
                    // Haut
                    if(y + 1 < this._map.length && (this._map[y + 1][x] == ' '  || this._map[y + 1][x] == 'W'))
                        nodes[value].addNeighbors(XYValue(x, y + 1));
                    // Bas
                    if(y - 1 >= 0 && (this._map[y - 1][x] == ' ' || this._map[y - 1][x] == 'W'))
                        nodes[value].addNeighbors(XYValue(x, y - 1));

                    // Haut gauche
                    if(y + 1 < this._map.length &&
                       x - 1 >= 0 &&
                       this._map[y + 1][x - 1] == 'W')
                        nodes[value].addNeighbors(XYValue(x - 1, y + 1));

                    // Haut droite
                    if(y + 1 < this._map.length &&
                       x + 1 < this._map[y].length &&
                       this._map[y + 1][x + 1] == 'W')
                        nodes[value].addNeighbors(XYValue(x + 1, y + 1));

                    // Bas gauche
                    if(y - 1 >= 0 &&
                       x - 1 >= 0 &&
                       this._map[y - 1][x - 1] == 'W')
                        nodes[value].addNeighbors(XYValue(x - 1, y - 1));

                    // Bas droite
                    if(y - 1 >= 0 &&
                       x + 1 < this._map[y].length &&
                       this._map[y - 1][x + 1] == 'W')
                        nodes[value].addNeighbors(XYValue(x + 1, y - 1));
                }
            }
        }
        this.tRoom = new ArrayList<Room>();
        for(int i = 0; i < nodes.length; i++) {
                if(nodes[i] != null && !nodes[i].fetched) {
                    ArrayList<Point> ground = new ArrayList<Point>();
                    ArrayList<Point> wall = new ArrayList<Point>();
                    ground.add(new Point(XValue(i), YValue(i)));
                    fetchNode(nodes[i], ground, wall, nodes);
                    this.tRoom.add(new Room(ground, wall));
                }
            }
        System.out.println(this.tRoom.size());
    }
    
    public void createEntrance() {
        Point indexRooms = new Point();
        int valueDistance = 0;
        for(int i = 0; i < this.tRoom.size(); i++) {
            for(int j = i + 1; j < this.tRoom.size(); j++) {
                Vector2 p1 = this.tRoom.get(i).getRandomFloorTiles().getPosition();
                Vector2 p2 = this.tRoom.get(j).getRandomFloorTiles().getPosition();
                if(Point.distance(p1.x, p1.y, p2.x, p2.y) > valueDistance) {
                    indexRooms.x = i;
                    indexRooms.y = j;
                }
            }
        }
        this.tRoom.get(indexRooms.x).isEntry = true;
        this.tRoom.get(indexRooms.x).isExit = true;
    }
    
    private void fetchNode(Node origin, ArrayList<Point> ground, ArrayList<Point> wall, Node[] nodes) {
        origin.fetched = true;
        for(int i = 0; i < origin.neighbors.size(); i++) {
            Point p = new Point(XValue(origin.neighbors.get(i)), YValue(origin.neighbors.get(i)));
            if(this._map[p.y][p.x] == 'W') {
                if(!wall.contains(p))
                    wall.add(p);
            }
            else if(this._map[p.y][p.x] == ' ' && !nodes[origin.neighbors.get(i)].fetched) {
                ground.add(p);
                fetchNode(nodes[origin.neighbors.get(i)], ground, wall, nodes);
            }
        }
    }
    
    private int XYValue(int x, int y) {
        return x + (y * this._map[y].length);
    }

    private int XValue(int value) {
        return (int)(value % this._map[0].length);
    }

    private int YValue(int value) {
        return (int)(value / this._map[0].length);
    }    
    
    public Map getMap() {
        return this.m;
    }
}
