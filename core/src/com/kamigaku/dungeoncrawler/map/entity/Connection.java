package com.kamigaku.dungeoncrawler.map.entity;

public class Connection {

    public int x;
    public int y;
    public AMapEntity e1;
    public AMapEntity e2;
    public ConnectionType connectionType;
    
    public enum ConnectionType {
        DOOR, EMPTY, LOCKED_DOOR
    }
    
    public Connection(int x, int y, AMapEntity e1, AMapEntity e2, ConnectionType c) {
        this.x = x;
        this.y = y;
        this.e1 = e1;
        this.e2 = e2;
        this.connectionType = c;
    }
    
}
