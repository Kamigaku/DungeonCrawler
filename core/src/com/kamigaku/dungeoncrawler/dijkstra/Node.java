/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.dijkstra;

import java.util.ArrayList;

/**
 *
 * @author Kamigaku
 */
    public class Node {

        public int value;
        public ArrayList<Integer> neighbors;
        public boolean fetched;
        public boolean isCrossable;

        public Node(int value) {
            this.value = value;
            this.neighbors = new ArrayList<Integer>();
            this.fetched = false;
        }

        public void addNeighbors(Integer value) {
            if(!this.neighbors.contains(value))
                this.neighbors.add(value);
        }
        
        public void addNeighbors(int value) {
            if(!this.neighbors.contains(value))
                this.neighbors.add(value);
        }

    }
