/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.dungeongenerator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

/**
 *
 * @author Kamigaku
 */
public class Map {
    
    private char[][] _map;
    private ArrayList<Room> _rooms;
    private Room _entryRoom;
    private Room _exitRoom;
    
    public Map(char[][] map, ArrayList<Room> rooms) {
        this._rooms = rooms;
        this._map = map;
        for(int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).isEntry)
                _entryRoom = rooms.get(i);
            if(rooms.get(i).isExit)
                _exitRoom = rooms.get(i);
        }
    }
    
    public void render(SpriteBatch batch) {
        for(int i = 0; i < this._rooms.size(); i++) {
            this._rooms.get(i).render(batch);
        }
    }
    
    public Room getEntryRoom() {
        return this._entryRoom;
    }
    
    public Room getExitRoom() {
        return this._exitRoom;
    }
    
}
