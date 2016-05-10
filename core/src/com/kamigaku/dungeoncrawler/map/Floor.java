package com.kamigaku.dungeoncrawler.map;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.dijkstra.Node;
import com.kamigaku.dungeoncrawler.map.corridor.Corridor;
import com.kamigaku.dungeoncrawler.map.entity.AMapEntity;
import com.kamigaku.dungeoncrawler.map.entity.IMapEntity;
import com.kamigaku.dungeoncrawler.map.room.Room;
import java.util.ArrayList;

public class Floor {
    
    private final ArrayList<IMapEntity> _entities;
    private final String _floorSeed;
    private final int _floorNumber;
    private final char[][] _floorMap;
    
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

        for (int y = 0; y < this._floorMap.length; y++) {
            for (int x = 0; x < this._floorMap[y].length; x++) {
                if (this._floorMap[y][x] == ' ') {

                    // Ajout du neoud
                    int value = XYValue(x, y);
                    this._nodes[value] = new Node(value);

                    // Ajout des voisins avec vérification
                    if (x - 1 >= 0 && this._floorMap[y][x - 1] == ' ')
                        this._nodes[value].addNeighbors(XYValue(x - 1, y));
                    if (x + 1 < this._floorMap.length && this._floorMap[y][x + 1] == ' ')
                        this._nodes[value].addNeighbors(XYValue(x + 1, y));
                    if (y - 1 >= 0 && this._floorMap[y - 1][x] == ' ')
                        this._nodes[value].addNeighbors(XYValue(x, y - 1));
                    if (y + 1 < this._floorMap[x].length && this._floorMap[y + 1][x] == ' ')
                        this._nodes[value].addNeighbors(XYValue(x, y + 1));
                }
            }
        }
        
        // Création des rooms
        for(int i = 0; i < this._nodes.length; i++) {
            if(this._nodes[i] != null && !this._nodes[i].fetched) {
                this._nodes[i].fetched = true;
                ArrayList<Vector2> coordinates = new ArrayList<Vector2>();
                coordinates.add(new Vector2(XValue(i), YValue(i)));
                for(int j = 0; j < this._nodes[i].neighbors.size(); j++) {
                    fetchNode(this._nodes[this._nodes[i].neighbors.get(j)], coordinates);
                }
                
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
                int widthRoom = higherWidth - lowerWidth + 1;
                int heightRoom = higherHeight - lowerHeight + 1;
                
                if(widthRoom <= 3 || heightRoom <= 3) {
                    this._entities.add(new Corridor(coordinates, lowerWidth - 1, lowerHeight - 1, widthRoom, heightRoom));
                    System.out.println("Added a corridor");
                }
                else {
                    this._entities.add(new Room(coordinates, lowerWidth - 1, lowerHeight - 1, widthRoom, heightRoom));
                }
            }
        }
        
        // Liaison des rooms
        Rectangle intersect = new Rectangle();
        for(IMapEntity current : this._entities) {
            AMapEntity aCurrent = (AMapEntity) current;
            for(IMapEntity other : this._entities) {
                AMapEntity aOther   = (AMapEntity) other;
                if(other != current && !aCurrent.neighbors.contains(aOther)) {
                    Rectangle r1 = new Rectangle(aCurrent.x, aCurrent.y, aCurrent.widthRoom + 2, aCurrent.heightRoom + 2);
                    Rectangle r2 = new Rectangle(aOther.x, aOther.y, aOther.widthRoom + 2, aOther.heightRoom + 2);
                    if(Intersector.intersectRectangles(r1, r2, intersect) && (intersect.width > 1 || intersect.height > 1)) {
                        aCurrent.neighbors.add(aOther);
                        aOther.neighbors.add(aCurrent);
                    }
                }
            }
            System.out.println("This room [" + aCurrent.y + "/" + aCurrent.x + "] (" + aCurrent.heightRoom + "/" + aCurrent.widthRoom + ") have : " + aCurrent.neighbors.size() + " connection(s).");
        }
        //displayFloor();
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
    
    public void displayFloor() {
        for (char[] _floorMap1 : this._floorMap) {
            for (int x = 0; x < _floorMap1.length; x++) {
                System.out.print(_floorMap1[x]);
            }
            System.out.println("");
        }
    }
    
}
