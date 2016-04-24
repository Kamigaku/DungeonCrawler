package com.kamigaku.dungeoncrawler.map;

import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.constants.Constants.MapType;
import com.kamigaku.dungeoncrawler.map.entity.IMapEntity;
import java.util.ArrayList;
import java.util.Random;

public class Map {
    
    public ArrayList<IMapEntity> mapEntities;
    public int[][][] map;
    public MapType mapType;
    public int width;
    public int height;
    
    private final Random randomizer;
    
    private String numberLevel = "0";
    private String numberRoom = "";
    private String infoMapSeed;
    private final int seed;
    
    public Map(int x, int y) {
        this.width = Constants.MAP_WIDTH;
        this.height = Constants.MAP_HEIGHT;
        this.seed = x * 1000 + y;
        this.randomizer = new Random(this.seed);
        this.infoMapSeed = "" + this.randomizer.nextLong();
        
        if(this.infoMapSeed.charAt(0) == '-') {
            this.infoMapSeed = this.infoMapSeed.substring(1);
        }
        
        // Map type
        this.mapType = Constants.MAP_POSSIBILITY[Integer.parseInt("" + this.infoMapSeed.charAt(this.infoMapSeed.length() - 1))];
        
        // Coordonnées première room
        // x
        int xFirstRoom = (Integer.parseInt("" + this.infoMapSeed.charAt(this.infoMapSeed.length() - 2)) + 
                         (Integer.parseInt("" + this.infoMapSeed.charAt(this.infoMapSeed.length() - 3)) * 10) +
                         (Integer.parseInt("" + this.infoMapSeed.charAt(this.infoMapSeed.length() - 4)) * 100)) %
                         Constants.MAP_WIDTH;
        
        // y
        int yFirstRoom = (Integer.parseInt("" + this.infoMapSeed.charAt(this.infoMapSeed.length() - 5)) + 
                         (Integer.parseInt("" + this.infoMapSeed.charAt(this.infoMapSeed.length() - 6)) * 10) +
                         (Integer.parseInt("" + this.infoMapSeed.charAt(this.infoMapSeed.length() - 7)) * 100)) %
                        Constants.MAP_HEIGHT;
        
        int bit_decalage = 0; // bit de décalage
        
        // Nombre d'étages
        // [2 ; 9]
        do {
            this.numberLevel = "" + this.infoMapSeed.charAt(this.infoMapSeed.length() - 8 - bit_decalage);
            bit_decalage += 1;
        } while(Integer.parseInt(this.numberLevel) < 2);
        
        // Nombre de room
        // [10 ; 99]
        do {
            if(this.infoMapSeed.charAt(this.infoMapSeed.length() - 8 - bit_decalage) != '0')
                this.numberRoom = this.infoMapSeed.charAt(this.infoMapSeed.length() - 8 - bit_decalage) + this.numberRoom;
            bit_decalage += 1;
        } while(this.numberRoom.compareTo("") == 0 || Integer.parseInt(this.numberRoom) < 10);
        
        
        if(this.numberLevel == "0" || this.numberLevel == "" || this.numberRoom == "0" || this.numberRoom == "") {
            System.out.println("Erreur lors du générateur : " + this.numberLevel + " | " + this.numberRoom);
        }
        
        this.map = new int[Integer.parseInt(this.numberLevel)][this.width][this.height];
    }
    
    public void addAnEntity(IMapEntity entity) {
        this.mapEntities.add(entity);
        // fetch the schema of the map and add it to the current map
    }
    
    //Display the map
    public void displayMap() {
        
    }
    
    @Override
    public String toString() {
        String s = "Map seed : " + this.seed + " - " + this.infoMapSeed + " | " + 
                    "Type : " + this.mapType.toString() + " | Nbr level : " + this.numberLevel +
                    " | Nbr room : " + this.numberRoom;
        return s;
    }
    
}
