/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.map.room;

import com.kamigaku.dungeoncrawler.map.entity.IMapEntity;

/**
 *
 * @author Kamigaku
 */
public interface IRoom extends IMapEntity {
    
    public void generate();
    public void displayMap();
    
}
