package com.kamigaku.dungeoncrawler.constants;

import com.kamigaku.dungeoncrawler.map.entity.Connection.ConnectionType;

public final class Constants {

    public enum MapType {
        RUINES, SHIP, HOUSE, LAB, TODEFINE
    }
    
    public static final int[][] ROOM_SIZE = new int[][] { { 10, 30 }, { 10, 30 }, { 10, 30 },
                                                          { 20, 50 }, { 20, 50 }, { 20, 50 },
                                                          { 30, 50 }, { 30, 50 },
                                                          { 30, 60 },
                                                          { 30, 60 } };
    
    public static final MapType[] MAP_POSSIBILITY = new MapType[] { 
                                                MapType.RUINES, MapType.RUINES, MapType.RUINES,
                                                MapType.HOUSE, MapType.HOUSE, MapType.HOUSE, 
                                                MapType.SHIP, MapType.SHIP,
                                                MapType.LAB,
                                                MapType.TODEFINE };
    
    public static final ConnectionType[] CONNECTION_POSSIBILITY = new ConnectionType[] {
                                            ConnectionType.DOOR, ConnectionType.EMPTY, ConnectionType.LOCKED_DOOR
                                        };
    
    
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 480;
    
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    
    public static final int MAP_WIDTH = 400;
    public static final int MAP_HEIGHT = 400;
    
    public static final short CATEGORY_PLAYER = 0x0001;  // 0000000000000001 in binary
    public static final short CATEGORY_MONSTER = 0x0002; // 0000000000000010 in binary
    public static final short CATEGORY_SCENERY = 0x0004; // 0000000000000100 in binary
        
}
