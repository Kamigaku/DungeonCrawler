package com.kamigaku.dungeoncrawler.dungeongenerator;

import com.kamigaku.dungeoncrawler.tile.*;
import com.kamigaku.dungeoncrawler.utility.Utility;
import java.awt.Point;
import java.util.ArrayList;

public class Room {
     
    private final ArrayList<Point> _bordersWithoutAngle;
    private final ArrayList<Connection> _connections;
    private final ArrayList<Point> _ground;
    private final ArrayList<Point> _walls;
    
    public boolean isEntry = false;
    public boolean isExit = false;
    
    public Room(char[][] mainMap, ArrayList<Point> ground, ArrayList<Point> walls) {
        this._bordersWithoutAngle = new ArrayList<Point>();
        this._ground = ground;
        this._connections = new ArrayList<Connection>();
        this._walls = walls;
        for(int i = 0; i < walls.size(); i++) {
            if(!Utility.checkXandYSurrondings(mainMap, walls.get(i).x, walls.get(i).y, 'W')) {
                this._bordersWithoutAngle.add(new Point(walls.get(i).x, walls.get(i).y));
            }
        }
    }
    public ArrayList<Point> getBordersWithoutAngles() {
        return this._bordersWithoutAngle;
    }
        
    public Point getFirstFloorTilesPosition() {
        return this._ground.get(0);
    }
    
}
