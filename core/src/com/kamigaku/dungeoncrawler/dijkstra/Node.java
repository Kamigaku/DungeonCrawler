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
        public int previous;
        public int shortestDistance;
        public ArrayList<Integer> neighbors;
        public boolean fetched;

        public Node(int value) {
            this.value = value;
            this.previous = -1;
            this.shortestDistance = -1;
            this.neighbors = new ArrayList<Integer>();
            this.fetched = false;
        }

        public void addNeighbors(Integer value) {
            if(!this.neighbors.contains(value))
                this.neighbors.add(value);
        }

        public void reset() {
            this.previous = -1;
            this.shortestDistance = -1;
            this.fetched = false;
        }

    }
