package com.kamigaku.dungeoncrawler.map;

import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.dijkstra.Node;
import com.kamigaku.dungeoncrawler.map.entity.IMapEntity;
import com.kamigaku.dungeoncrawler.map.room.Room;
import java.util.ArrayList;

public class Floor {
    
    private ArrayList<IMapEntity> _entities;
    private char[][] _floorMap;
    private String _floorSeed;
    private int _floorNumber;
    
    private Node[] _nodes;
    
    public Floor(char[][] floorMap, String floorSeed, int floorNumber) {
        this._floorMap = floorMap;
        this._floorSeed = floorSeed;
        this._floorNumber = floorNumber;
        this._entities = new ArrayList<IMapEntity>();
        this.splitRooms();
    }
    
    private void splitRooms() {
        this._nodes = new Node[this._floorMap.length * this._floorMap[0].length];
        for (int x = 0; x < this._floorMap.length; x++) {
            for (int y = 0; y < this._floorMap[x].length; y++) {
                if (this._floorMap[x][y] == ' ') {

                    // Ajout du neoud
                    int value = XYValue(x, y);
                    this._nodes[value] = new Node(value);

                    // Ajout des voisins avec vérification
                    if (x - 1 >= 0 && this._floorMap[x - 1][y] == ' ')
                        this._nodes[value].addNeighbors(XYValue(x - 1, y));
                    if (x + 1 < this._floorMap.length && this._floorMap[x + 1][y] == ' ')
                        this._nodes[value].addNeighbors(XYValue(x + 1, y));
                    if (y - 1 >= 0 && this._floorMap[x][y - 1] == ' ')
                        this._nodes[value].addNeighbors(XYValue(x, y - 1));
                    if (y + 1 < this._floorMap[x].length && this._floorMap[x][y + 1] == ' ')
                        this._nodes[value].addNeighbors(XYValue(x, y + 1));
                }
            }
        }
        
        // Je parcours tous les noeuds, quand j'en trouve un qui n'est pas vide
        // je parcours tous ses voisins, eux-même parcours leurs voisins jusqu'a
        // ne plus rien avoir
        for(int i = 0; i < this._nodes.length; i++) {
            if(this._nodes[i] != null && !this._nodes[i].fetched) {
                this._nodes[i].fetched = true;
                ArrayList<Vector2> coordinates = new ArrayList<Vector2>();
                coordinates.add(new Vector2(XValue(i), YValue(i)));
                for(int j = 0; j < this._nodes[i].neighbors.size(); j++) {
                    fetchNode(this._nodes[this._nodes[i].neighbors.get(j)], coordinates);
                }
                int widthRoom = 0;
                int heightRoom = 0;
                int lowerHeight = (int)coordinates.get(0).y;
                int higherHeight = (int)coordinates.get(0).y;
                int lowerWidth = (int)coordinates.get(0).x;
                int higherWidth = (int)coordinates.get(0).x;
                for(int j = 1; j < coordinates.size(); j++) {
                    if(coordinates.get(j).y < lowerHeight) 
                        lowerHeight = (int)coordinates.get(j).y;
                    if(coordinates.get(j).y > higherHeight) 
                        higherHeight = (int)coordinates.get(j).y;
                    if(coordinates.get(j).x < lowerWidth) 
                        lowerWidth = (int)coordinates.get(j).x;
                    if(coordinates.get(j).x > higherWidth) 
                        higherWidth = (int)coordinates.get(j).x;                  
                }
                widthRoom = higherWidth - lowerWidth + 1;
                heightRoom = higherHeight - lowerHeight + 1;
                System.out.println("Width : " + widthRoom + " | Height : " + heightRoom);
                char[][] roomMap = new char[widthRoom][heightRoom];
                Room r = new Room(lowerWidth, lowerHeight, widthRoom, heightRoom);
                this._entities.add(r);
                System.out.println("Room created");
            }
        }
    }
    
    private void fetchNode(Node n, ArrayList<Vector2> coordinates) {
        if(!n.fetched) {
            coordinates.add(new Vector2(XValue(n.value), YValue(n.value)));
            n.fetched = true;
            for(int i = 0; i < n.neighbors.size(); i++) {
                fetchNode(this._nodes[n.neighbors.get(i)], coordinates);
            }
        }
    }
    
    private int XYValue(int x, int y) {
        return x + (y * this._floorMap.length);
    }

    private int XValue(int value) {
        return (int)(value % this._floorMap.length);
    }

    private int YValue(int value) {
        return (int)(value / this._floorMap.length);
    } 
    
}
