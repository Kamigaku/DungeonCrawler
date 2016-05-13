package com.kamigaku.dungeoncrawler.map.entity;

import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.tile.*;
import java.util.ArrayList;

public abstract class AMapEntity implements IMapEntity {

    public int x;
    public int y;
    public int widthRoom;
    public int heightRoom;
    
    public ArrayList<AMapEntity> neighbors;
    private char[][] schema;
    
    private ArrayList<Tile> _tiles;
    
    @Override
    public void displayEntity() {
        for (char[] schema1 : this.schema) {
            for (int yR = 0; yR < schema1.length; yR++) {
                System.out.print(schema1[yR]);
            }
            System.out.println("");
        }
    }

    @Override
    public void extractEntity(ArrayList<Vector2> coordinates) {
        char[][] roomMap = new char[heightRoom + 2][widthRoom + 2]; // +2 pour ajouter les murs
        for (char[] roomMap1 : roomMap) {
            for (int yR = 0; yR < roomMap1.length; yR++) {
                roomMap1[yR] = '#';
            }
        }
        for(int i = 0; i < coordinates.size(); i++) {
            roomMap[(int)coordinates.get(i).y - this.y][(int)coordinates.get(i).x - this.x] = ' ';
        }
        for(int xR = 0; xR < roomMap.length; xR++) {
            for(int yR = 0; yR < roomMap[xR].length; yR++) {
                if(roomMap[xR][yR] == ' ') {
                    if((xR - 1 >= 0) && roomMap[xR - 1][yR] == '#') roomMap[xR - 1][yR] = 'W'; // Gauche
                    if((xR + 1 < roomMap.length) && roomMap[xR + 1][yR] == '#') roomMap[xR + 1][yR] = 'W'; // Droite
                    if((yR - 1 >= 0) && roomMap[xR][yR - 1] == '#') roomMap[xR][yR - 1] = 'W'; // Bas
                    if((yR + 1 < roomMap[xR].length) && roomMap[xR][yR + 1] == '#') roomMap[xR][yR + 1] = 'W'; // Haut
                    if((yR + 1 < roomMap[xR].length) && (xR - 1 >= 0) && roomMap[xR - 1][yR + 1] == '#') roomMap[xR - 1][yR + 1] = 'W'; // Haut Gauche
                    if((yR + 1 < roomMap[xR].length) && (xR + 1 < roomMap.length) && roomMap[xR + 1][yR + 1] == '#') roomMap[xR + 1][yR + 1] = 'W'; // Haut Droite
                    if((xR - 1 >= 0) && (yR - 1 >= 0) &&  roomMap[xR - 1][yR - 1] == '#') roomMap[xR - 1][yR - 1] = 'W'; // Bas Gauche
                    if((xR + 1 < roomMap.length) && (yR - 1 >= 0) &&  roomMap[xR + 1][yR - 1] == '#') roomMap[xR + 1][yR - 1] = 'W'; // Bas Droite
                }
            }
        }
        this.schema = roomMap;
        
        this._tiles = new ArrayList<Tile>();
        for(int yPos = 0; yPos < this.schema.length; yPos++) {
            for(int xPos = 0; xPos < this.schema[yPos].length; xPos++) {
                if(this.schema[yPos][xPos] != '#') {
                    if(this.schema[yPos][xPos] == ' ')
                        this._tiles.add(new Ground("sprites/ground.png", xPos + this.x * Constants.TILE_WIDTH, yPos + this.y * Constants.TILE_HEIGHT));
                    else if(this.schema[yPos][xPos] == 'W')
                        this._tiles.add(new Wall("sprites/wall.png", xPos + this.x * Constants.TILE_WIDTH, yPos + this.y * Constants.TILE_HEIGHT));
                }   
            }
        }
    }
    
    @Override
    public ArrayList<Tile> getTiles() {
        return this._tiles;
    }
    
    @Override
    public ArrayList<Vector2> getTilesPosition() {
        ArrayList<Vector2> v = new ArrayList<Vector2>();
        for(Tile t : this._tiles) {
            v.add(t.getPosition());
        }
        return v;
    }
    
    
}
