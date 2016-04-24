package com.kamigaku.dungeoncrawler.constants;

public final class Constants {

    public enum MapType {
        RUINES, SHIP, HOUSE, LAB, TODEFINE
    }
    
    public static final int[][] ROOM_SIZE = new int[][] { { 10, 30 }, { 20, 60 },
                                            { 10, 30 }, { 20, 60 }, { 30, 50 } };
    
    
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 480;
    
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    
    public static final int MAP_WIDTH = 200;
    public static final int MAP_HEIGHT = 200;
    
    public static final MapType[] MAP_POSSIBILITY = new MapType[] { MapType.RUINES, MapType.HOUSE, MapType.SHIP,
                                        MapType.LAB, MapType.HOUSE, MapType.HOUSE,
                                        MapType.SHIP, MapType.SHIP, MapType.RUINES, MapType.TODEFINE };
}
