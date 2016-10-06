package com.kamigaku.dungeoncrawler.constants;

public final class Constants {
    
    public static final boolean DEBUG = true;
        
    public static final int VIRTUAL_HEIGHT = 20;
    
    public static final float PLAYER_SPEED = 10;
    
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
        
    public static final short CATEGORY_PLAYER = 0x0001;  // 0000000000000001 in binary
    public static final short CATEGORY_MONSTER = 0x0002; // 0000000000000010 in binary
    public static final short CATEGORY_SCENERY = 0x0004; // 0000000000000100 in binary
            
}
