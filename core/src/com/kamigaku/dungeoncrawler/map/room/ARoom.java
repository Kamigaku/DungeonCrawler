/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.map.room;

/**
 *
 * @author Kamigaku
 */
public abstract class ARoom implements IRoom {
     
    public int x;
    public int y;
    public int width;
    public int height;
    public char[][] schema;

    
}
