package com.kamigaku.dungeoncrawler.dijkstra;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class Square {
  
    private Node[] _nodes;
    private char[][] _map;
    private HashMap<Integer, Integer> _possibleDestination;
    private int _currentNode;
    
    public Square(int size) {
        this._nodes = new Node[size * size];
        this._possibleDestination = new HashMap<Integer, Integer>();
    }
    
    public void applycharMap(char[][] map) {
        this._map = map;
        for(int y = 0; y < this._map.length; y++) {
            for(int x = 0; x < this._map.length; x++) {
                if(this._map[y][x] == 'W') {
                    int value = Node.XYValue(x, y, this._map.length);
                    this._nodes[value] = new Node(value);
                    if(x + 1 < this._map[y].length && this._map[y][x + 1] == 'W') // Droite
                        this._nodes[value].addNeighbors(Node.XYValue(x + 1, y, this._map.length));
                    if(x - 1 >= 0 && this._map[y][x - 1] == 'W') // Gauche
                        this._nodes[value].addNeighbors(Node.XYValue(x - 1, y, this._map.length));
                    if(y + 1 < this._map.length && this._map[y + 1][x] == 'W') // Haut
                        this._nodes[value].addNeighbors(Node.XYValue(x, y + 1, this._map.length));
                    if(y - 1 >= 0 && this._map[y - 1][x] == 'W') // Bas
                        this._nodes[value].addNeighbors(Node.XYValue(x, y - 1, this._map.length));
                }
            }
        }
        
        for(int i = 0; i < this._nodes.length; i++) {
            if(this._nodes[i] != null && !this._nodes[i].fetched) {
                this._possibleDestination.put(i, 0);
                this._currentNode = i;
                fetchNode(this._nodes[i]);
                for(int j = 0; j < this._nodes.length; j++)
                    if(this._nodes[j] != null) this._nodes[j].fetched = false;
            }
        }
    }
    
    private void fetchNode(Node origin) {
        origin.fetched = true;
        for(int i = 0; i < origin.neighbors.size(); i++) {
            if(!this._nodes[origin.neighbors.get(i)].fetched) {
                this._possibleDestination.replace(this._currentNode, 
                    this._possibleDestination.get(this._currentNode) + 1);
                fetchNode(this._nodes[origin.neighbors.get(i)]);
            }
        }
    }
    
    /***
     * 
     * @param preUpdate : l'origine du carré
     * @param postUpdate : l'après
     * @return si TRUE, la modification n'engendre aucune modification, si FALSE, la modification engendre un changement
     */
    public static boolean compareSquare(Square preUpdate, Square postUpdate) {
        for(int i = 0; i < preUpdate._nodes.length; i++) {
            if(i != 4 && preUpdate._nodes[i] != null && postUpdate._nodes[i] != null) {
                int size_pre = preUpdate._possibleDestination.get(i);
                int size_post = postUpdate._possibleDestination.get(i);
                if(Math.abs(size_post - size_pre) > 1)
                    return false;
            }
        }
        return true;
    }
    
}
