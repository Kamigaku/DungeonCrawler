package com.kamigaku.dungeoncrawler.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.constants.Constants.MapType;
import java.util.ArrayList;
import java.util.Random;

public class Map {
    
    public ArrayList<Floor> _floors;
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
        this._floors = new ArrayList<Floor>();
        this.seed = x * 1000 + y;
        this.randomizer = new Random(this.seed);
        this.infoMapSeed = "" + this.randomizer.nextLong();
        
        if(this.infoMapSeed.charAt(0) == '-') {
            this.infoMapSeed = this.infoMapSeed.substring(1);
        }
        
        // Map type
        int type = Integer.parseInt("" + this.infoMapSeed.charAt(this.infoMapSeed.length() - 1));
        this.mapType = Constants.MAP_POSSIBILITY[Integer.parseInt("" + this.infoMapSeed.charAt(this.infoMapSeed.length() - 1))];
        
        int bit_decalage = 0; // bit de décalage
        
        // Nombre de room
        // [10 ; 99]
        this.numberRoom = "" + this.infoMapSeed.charAt(this.infoMapSeed.length() - 8 - bit_decalage - 1) +
                            this.infoMapSeed.charAt(this.infoMapSeed.length() - 8 - bit_decalage);
        this.numberRoom = "" + ((Integer.parseInt(numberRoom) % (Constants.ROOM_SIZE[type][1] - Constants.ROOM_SIZE[type][0])) + Constants.ROOM_SIZE[type][0]);
                
        char[][] floor = new char[Constants.MAP_HEIGHT + 200][Constants.MAP_WIDTH + 200];
        for(int i_height = 0; i_height < floor.length; i_height++) {
            for(int j_width = 0; j_width < floor[i_height].length; j_width++) {
                floor[i_height][j_width] = '#';
            }
        }
        
        // Place room
        for(int i = 0; i < Integer.parseInt(this.numberRoom); i++) {
            addStringRoom(floor, infoMapSeed);
            this.infoMapSeed = "" + this.randomizer.nextLong();
        }
        
        for(int yR = 0; yR < floor.length; yR++) {
            for(int xR = 0; xR < floor[yR].length; xR++) {
                if(floor[yR][xR] == 'W') {
                    if(floor[yR - 1][xR] != ' ' && floor[yR + 1][xR] != ' ' &&
                        floor[yR][xR - 1] != ' ' && floor[yR][xR + 1] != ' ' &&
                        floor[yR - 1][xR + 1] != ' ' && floor[yR + 1][xR + 1] != ' ' &&
                        floor[yR - 1][xR - 1] != ' ' && floor[yR + 1][xR - 1] != ' ') {
                        floor[yR][xR] = '#';
                    }
                }
            }
        }
        
        this._floors.add(new Floor(floor, "", 0));
            
    }
    
    public void addStringRoom(char[][] floor, String seed) {
        int xRoom = (Integer.parseInt("" + seed.charAt(seed.length() - 2)) + 
                    (Integer.parseInt("" + seed.charAt(seed.length() - 3)) * 10) +
                    (Integer.parseInt("" + seed.charAt(seed.length() - 4)) * 100)) %
                    Constants.MAP_WIDTH;
        
        // y
        int yRoom = (Integer.parseInt("" + seed.charAt(seed.length() - 5)) + 
                    (Integer.parseInt("" + seed.charAt(seed.length() - 6)) * 10) +
                    (Integer.parseInt("" + seed.charAt(seed.length() - 7)) * 100)) %
                    Constants.MAP_HEIGHT;
        
        // width
        int widthRoom = Integer.parseInt("" + seed.charAt(seed.length() - 8)) + 
                    (Integer.parseInt("" + seed.charAt(seed.length() - 9)) * 10);
        
        // height
        int heightRoom = Integer.parseInt("" + seed.charAt(seed.length() - 10)) + 
                    (Integer.parseInt("" + seed.charAt(seed.length() - 11)) * 10);
                
        for(int y = 0; y < heightRoom; y++) { // Colonne
            if((y + yRoom) == Constants.MAP_HEIGHT - 1) { // Si le x courant dépasse la taille maximum de la salle, 
                                                                              // je fais un pas en arrière et je rajoute des murs
                for(int x = 0; x < widthRoom; x++) { // Je reparcours tous les y précédents de la colonne
                    if(xRoom + x >= Constants.MAP_WIDTH)
                        break;
                    else
                        floor[yRoom + y][x + xRoom] = 'W'; // Je pose un mur
                }
                break;
            }
            for(int x = 0; x < widthRoom; x++) { // Colonne
                if(x + xRoom == Constants.MAP_WIDTH - 1) { // Si le y dépasse le haut, j'arrête et je reviens un cran en arrière
                                                // pour y déposer des murs
                    for(int ySub = 0; ySub < heightRoom; ySub++) { // Je reparcours les x précédents
                        if(yRoom + ySub >= Constants.MAP_WIDTH)
                            break;
                        else
                            floor[ySub + yRoom][x + xRoom] = 'W'; // Je pose un mur
                    }
                    break;
                }
                else {
                    if(x == 0 || x == (widthRoom - 1) || y == 0 || y == (heightRoom - 1)) // Je positionne un mur aux extrémités
                        floor[y + yRoom][x + xRoom] = 'W'; // Je pose un mur
                    else {
                        if(floor[y + yRoom][x + xRoom] != 'W') // Si la case courante n'est pas un mur
                            floor[y + yRoom][x + xRoom] = ' ';
                    }
                }
            }
        }
    }
    
    public void render(SpriteBatch batch) {
        for(int i = 0; i < this._floors.size(); i++) {
            this._floors.get(i).render(batch);
        }
    }
    
    //Display the map
    public void displayMap() {
        for(int i = 0; i < this._floors.size(); i++) {
            this._floors.get(i).displayFloor();
        }
    }
    
    @Override
    public String toString() {
        String s = "Map seed : " + this.seed + " - " + this.infoMapSeed + " | " + 
                    "Type : " + this.mapType.toString() + " | Nbr level : " + this.numberLevel +
                    " | Nbr room : " + this.numberRoom;
        return s;
    }
    
}
