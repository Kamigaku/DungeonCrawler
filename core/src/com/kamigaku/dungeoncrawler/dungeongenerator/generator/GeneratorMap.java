package com.kamigaku.dungeoncrawler.dungeongenerator.generator;

import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.dijkstra.*;
import com.kamigaku.dungeoncrawler.dungeongenerator.Map;
import com.kamigaku.dungeoncrawler.dungeongenerator.Room;
import com.kamigaku.dungeoncrawler.tile.Ground;
import com.kamigaku.dungeoncrawler.tile.Layer;
import com.kamigaku.dungeoncrawler.tile.Tile;
import com.kamigaku.dungeoncrawler.tile.Wall;
import com.kamigaku.dungeoncrawler.utility.Utility;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GeneratorMap {
  
    private char[][] _map;
    private final ArrayList<GeneratorRoom> _rooms;
    private final long _seed;
    private final Random _random;
    
    private final Map m;
    private ArrayList<Room> tRoom;
    private HashMap<Integer, Tile> _wallsMap;
    
    private final ArrayList<Layer> _layers;
    
    public GeneratorMap(long seed) {
        this._layers = new ArrayList<Layer>();
        
        
        this._seed = seed;
        this._random = new Random(seed);
        this._rooms = new ArrayList<GeneratorRoom>();
        this._wallsMap = new HashMap();
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
            if(r.isValid) {
                this._rooms.add(r);
            }
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
            for(int j = 0; j < borders.size(); j++) {
                if(Utility.numberAllAroundSurrondings(map, borders.get(i).x, borders.get(i).y, '#') < 2) {
                    borders.remove(j);
                    j--;
                }
                
            }
            Point p = borders.get(Utility.nextInt(_random, 0, borders.size()));
            x = p.x;
            y = p.y;
        }
        borders.clear();
        
        this.reduceMap(map);
        this.removeUnnecessaryWalls();
        Utility.displayEntity(_map);
        this.createRooms();
        this.connectRooms();
        this.createEntrance();
        //this.boundRooms();
        this.m = new Map(this._map, this.tRoom, this._layers);
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
    }
    
    private void removeUnnecessaryWalls() {
        for(int y = 1; y < this._map.length - 1; y++) {
            for(int x = 1; x < this._map[y].length - 1; x++) {
                if(this._map[y][x] == 'W') {
                    char[][] square = new char[3][3];
                    for(int i = 0; i < 3; i++)
                        for(int j = 0; j < 3; j++)
                            square[i][j] = this._map[y + (-1 + i)][x + (-1 + j)];
                    Square preUpdate = new Square(square);
                    square[1][1] = ' ';
                    Square postUpdate = new Square(square);
                    if(Square.compareSquare(preUpdate, postUpdate)) {
                        if(Utility.checkXorYSurrondings(this._map, x, y, ' '))
                            this._map[y][x] = ' ';
                        else
                            this._map[y][x] = '#';
                    }
                }
            }
        }
    }
    
    private void createRooms() {        
        Dijkstra d = new Dijkstra(this._map);
        d.addRule(new Rule(' ', 'W', true, true));
        d.addRule(new Rule(' ', ' ', true, false));
        d.createNodes(true);
        Node[] nodes = d.getNodes();
        this.tRoom = new ArrayList<Room>();
        
        this._layers.add(0, new Layer(Layer.GROUND, this._map[0].length, this._map.length));
        this._layers.add(1, new Layer(Layer.WALL, this._map[0].length, this._map.length));
        for(int i = 0; i < nodes.length; i++) {
            if(nodes[i] != null && !nodes[i].fetched) {
                int xPos = Dijkstra.XValue(i, this._map[0].length);
                int yPos = Dijkstra.YValue(i, this._map[0].length);
                ArrayList<Point> wall = new ArrayList<Point>();
                ArrayList<Point> ground = new ArrayList<Point>();
                ground.add(new Point(xPos, yPos));
                if(!this._layers.get(0).containsTile(this._layers.get(0).calculatePosition(xPos, yPos)))
                    this._layers.get(0).addTile(new Ground("sprites/ground.png", xPos, yPos));
                fetchNode(nodes[i], nodes, ground, wall);

                this.tRoom.add(new Room(this._map, ground, wall));
            }
        }
    }
    
    private void connectRooms() {
        for(int y = 0; y < this._map.length; y++) {
            for(int x = 0; x < this._map[y].length; x++) {
                if(this._map[y][x] == 'W') {
                    int result = Utility.checkDiagonalsSurrondings(this._map, x, y, ' ');
                    switch(result) {
                        case 1:
                        case -1:
                            this._map[y][x] = this._map[y][x - 1] = this._map[y][x + 1] = ' ';
                            this._map[y + 1][x + (1 * result)] = this._map[y + 1][x + (2 * result)] = 'W';
                            this._map[y - 1][x + (-1 * result)] = this._map[y - 1][x + (-2 * result)] = 'W';
                            break;
                        case 2:
                            throw new UnsupportedOperationException();
                    }
                }
            }
        }
    }
    
    private void createEntrance() {
        Point indexRooms = new Point();
        int valueDistance = 0;
        for(int i = 0; i < this.tRoom.size(); i++) {
            for(int j = i + 1; j < this.tRoom.size(); j++) {
                Point p1 = this.tRoom.get(i).getFirstFloorTilesPosition();
                Point p2 = this.tRoom.get(j).getFirstFloorTilesPosition();
                if(p1.distance(p2.x, p2.y) > valueDistance) {
                    indexRooms.x = i;
                    indexRooms.y = j;
                }
            }
        }
        this.tRoom.get(indexRooms.x).isEntry = true;
        this.tRoom.get(indexRooms.x).isExit = true;
    }
    
    private void fetchNode(Node origin, Node[] nodes, ArrayList<Point> ground, ArrayList<Point> wall) {
        origin.fetched = true;
        for(int i = 0; i < origin.neighbors.size(); i++) {
            Point p = new Point(Dijkstra.XValue(origin.neighbors.get(i), this._map[0].length), 
                                Dijkstra.YValue(origin.neighbors.get(i), this._map[0].length));
            if(this._map[p.y][p.x] == 'W') {
                if(!this._layers.get(1).containsTile(this._layers.get(1).calculatePosition(p.x, p.y)))
                    this._layers.get(1).addTile(new Wall("sprites/wall.png", p.x, p.y));
                wall.add(p);                
            }
            else if(this._map[p.y][p.x] == ' ' && !nodes[origin.neighbors.get(i)].fetched) {
                if(!this._layers.get(0).containsTile(this._layers.get(0).calculatePosition(p.x, p.y)))
                    this._layers.get(0).addTile(new Ground("sprites/ground.png", p.x, p.y));
                ground.add(p);
            }
        }
    }
    
    // BUG ICI
    private void boundRooms() {
        for(int i = 0; i < this.tRoom.size(); i++) {
            Room r1 = this.tRoom.get(i);
            for(int j = i + 1; j < this.tRoom.size(); j++) {
                Room r2 = this.tRoom.get(j);
                ArrayList<Point> commonCoords = Utility.commonCoords(r1.getBordersWithoutAngles(),
                                                                       r2.getBordersWithoutAngles());
                if(commonCoords.size() > 1) {
                    int wall = Utility.nextInt(new Random(commonCoords.size()), 0, commonCoords.size());
                    this._layers.get(1).removeTilesAtPosition(commonCoords.get(wall));
                    this._layers.get(0).addTile(new Ground("sprites/ground.png", 
                            (int)commonCoords.get(wall).x, (int)commonCoords.get(wall).y));
                }
            }
        }
    }
    
    public Map getMap() {
        return this.m;
    }
}
