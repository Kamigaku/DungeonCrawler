package com.kamigaku.dungeoncrawler.dungeongenerator;

import com.kamigaku.dungeoncrawler.tile.Ground;
import com.kamigaku.dungeoncrawler.tile.Tile;
import com.kamigaku.dungeoncrawler.tile.Wall;

public class Connection {
    
    public int x;
    public int y;
    public Room r1;
    public Room r2;
    public ConnectionType connectionType;
    
    public enum ConnectionType {
        DOOR, EMPTY, LOCKED_DOOR
    }
    
    public Connection(int x, int y, Room r1, Room r2, ConnectionType c) {
        this.x = x;
        this.y = y;
        this.r1 = r1;
        this.r2 = r2;
        this.connectionType = c;
    }
    
    public Tile getConnectionTile() {
        switch(this.connectionType) {
            case DOOR:
                return new Wall("sprites/door.png", x, y);
            case EMPTY:
                return new Ground("sprites/ground.png", x, y);
            case LOCKED_DOOR:
                return new Ground("sprites/ground.png", x, y);
            default:
                return new Ground("sprites/ground.png", x, y);
        }
    }
    
}
