package com.kamigaku.dungeoncrawler.constants;

public final class Constants {
    
    public static final boolean DEBUG = false;
        
    public static final int VIRTUAL_HEIGHT = 20;
    
    public static final float PLAYER_SPEED = 10;
    
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
        
    public static final short CATEGORY_PLAYER  = 0x0001;  // 0000000000000001 in binary
    public static final short CATEGORY_MONSTER = 0x0002;  // 0000000000000010 in binary
    public static final short CATEGORY_SCENERY = 0x0004;  // 0000000000000100 in binary
    public static final short CATEGORY_ITEM    = 0x0008;  // 0000000000001000 in binary
    public static final short CATEGORY_SENSOR  = 0x0016;  // 0000000000010000 in binary
            
}
