package com.kamigaku.dungeoncrawler.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.dijkstra.Node;
import com.kamigaku.dungeoncrawler.map.entity.Corridor;
import com.kamigaku.dungeoncrawler.map.entity.AMapEntity;
import com.kamigaku.dungeoncrawler.map.entity.Connection;
import com.kamigaku.dungeoncrawler.map.entity.IMapEntity;
import com.kamigaku.dungeoncrawler.map.entity.Room;
import com.kamigaku.dungeoncrawler.utility.Utility;
import java.util.ArrayList;
import java.util.Random;

public class Floor {
    
    public final ArrayList<IMapEntity> _entities;
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
        createRooms();
        
        // Liaison des rooms
        linkRooms();
        //displayFloor();
        
        
    }
    
    private void createRooms() {
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
                }
                else {
                    this._entities.add(new Room(coordinates, lowerWidth - 1, lowerHeight - 1, widthRoom, heightRoom));
                }
            }
        }
    }
    
    private void linkRooms() {
        for(int i = 0; i < this._entities.size(); i++) {
            AMapEntity aCurrent = (AMapEntity) this._entities.get(i);
            for(int j = i + 1; j < this._entities.size(); j++) {
                AMapEntity aOther = (AMapEntity) this._entities.get(j);
                ArrayList<Vector2> commonCoords = Utility.commonCoords(aCurrent.getTilesPosition(), aOther.getTilesPosition());
                if(commonCoords.size() > 3) {
                    Random r = new Random(commonCoords.size());
                    int seed = Math.abs(r.nextInt());
                    Connection c = new Connection((int)commonCoords.get(seed % commonCoords.size()).x, 
                                                  (int)commonCoords.get(seed % commonCoords.size()).y, 
                                                   aCurrent, aOther, 
                                                   Constants.CONNECTION_POSSIBILITY[seed % Constants.CONNECTION_POSSIBILITY.length]);
                    aCurrent.addNeighbors(c);
                    aOther.addNeighbors(c);
                }
            }
            System.out.println("This room [" + aCurrent.y + "/" + aCurrent.x + "] (" + aCurrent.heightRoom + "/" + aCurrent.widthRoom + ") have : " + aCurrent.neighbors.size() + " connection(s).");
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
    
    public void render(SpriteBatch batch) {
        for(int i = 0; i < this._entities.size(); i++) {
            this._entities.get(i).render(batch);
        }
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
