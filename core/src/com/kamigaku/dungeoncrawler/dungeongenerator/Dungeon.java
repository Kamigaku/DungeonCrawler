package com.kamigaku.dungeoncrawler.dungeongenerator;

import com.kamigaku.dungeoncrawler.dungeongenerator.generator.GeneratorMap;

public class Dungeon {

    public Map map;
    public int seed;
    public int xDungeon;
    public int yDungeon;
    
    public Dungeon(int xDungeon, int yDungeon) {
        this.xDungeon = xDungeon;
        this.yDungeon = yDungeon;
        this.map = new GeneratorMap(this.xDungeon * 1000 + this.yDungeon).getMap();
    }
    
}
