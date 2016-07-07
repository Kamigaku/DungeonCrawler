package com.kamigaku.dungeoncrawler.dijkstra;

import java.awt.Point;
import java.util.ArrayList;

public class Dijkstra {

    private final char[][] _map;
    private final ArrayList<Rule> _rules; 
    private final Node[] _nodes;
    
    public Dijkstra(char[][] theMap) {
        this._map = theMap;
        this._rules = new ArrayList<Rule>();
        this._nodes = new Node[this._map[0].length * this._map.length];
    }
    
    /**
     * Créer les noeuds pour le tableau passé
     * @param doAngles (boolean) : si TRUE, l'algorithme passera aussi dans les angles haut gauche, haut droite, bas gauche et bas droite.
     */
    public void createNodes(boolean doAngles) {
        for(int y = 0; y < this._map.length; y++) {
            for(int x = 0; x < this._map[y].length; x++) {
                if(isValidObject(this._map[y][x])) {
                    int value = XYValue(x, y, this._map[y].length);
                    _nodes[value] = new Node(value);

                    if(x + 1 < this._map[y].length && this.validateARule(this._map[y][x], this._map[y][x + 1], true)) // Droite
                        _nodes[value].addNeighbors(XYValue(x + 1, y, this._map[y].length));

                    if(x - 1 >= 0 && this.validateARule(this._map[y][x], this._map[y][x - 1], true)) // Gauche
                        _nodes[value].addNeighbors(XYValue(x - 1, y, this._map[y].length));

                    if(y + 1 < this._map.length && this.validateARule(this._map[y][x], this._map[y + 1][x], true)) // Haut
                        _nodes[value].addNeighbors(XYValue(x, y + 1, this._map[y].length));

                    if(y - 1 >= 0 && this.validateARule(this._map[y][x], this._map[y - 1][x], true)) // Bas
                        _nodes[value].addNeighbors(XYValue(x, y - 1, this._map[y].length));

                    if(doAngles) {
                        if(y + 1 < this._map.length && x - 1 >= 0 && 
                                this.validateARule(this._map[y][x], this._map[y + 1][x - 1], false)) // Haut gauche
                            _nodes[value].addNeighbors(XYValue(x - 1, y + 1, this._map[y].length));

                        if(y + 1 < this._map.length && x + 1 < this._map[y].length && 
                                this.validateARule(this._map[y][x], this._map[y + 1][x + 1], false)) // Haut droite
                            _nodes[value].addNeighbors(XYValue(x + 1, y + 1, this._map[y].length));

                        if(y - 1 >= 0 && x - 1 >= 0 && 
                                this.validateARule(this._map[y][x], this._map[y - 1][x - 1], false)) // Bas gauche
                            _nodes[value].addNeighbors(XYValue(x - 1, y - 1, this._map[y].length));

                        if(y - 1 >= 0 && x + 1 < this._map[y].length && 
                                this.validateARule(this._map[y][x], this._map[y - 1][x + 1], false)) // Bas droite
                            _nodes[value].addNeighbors(XYValue(x + 1, y - 1, this._map[y].length));
                    }
                }
            }
        }
    }
    
    public ArrayList<Point> shortestPathFromTo(Point from, Point to) {
        ArrayList<Point> path = new ArrayList<Point>();
        return path;
    }
    
    /**
     * Retourne le nombre de points accessibles à partir d'un noeud d'origine
     * @param origin (Point) Les coordonnées du point d'origine
     * @return Le nombre de point accessible
     */
    public int numberOfAccessiblePoint(Point origin) {
        return allPossiblePath(origin).size();
    }
    
    /** 
     * Retourne tous les points accessibles à partir d'un noeud d'origine.
     * @param origin (Point) Le noeud d'origine
     * @return Tous les points accessibles
     */
    public ArrayList<Point> allPossiblePath(Point origin) {
        ArrayList<Point> allPath = new ArrayList<Point>();
        Node originNode = this._nodes[XYValue(origin.x, origin.y, this._map[origin.y].length)];
        allPossiblePath_fetch(originNode, allPath);
        return allPath;
    }
    
    private void allPossiblePath_fetch(Node origin, ArrayList<Point> path) {
        origin.fetched = true;
        for(int i = 0; i < origin.neighbors.size(); i++) {
            if(!this._nodes[origin.neighbors.get(i)].fetched) {
                path.add(new Point(XValue(origin.neighbors.get(i), this._map[0].length),
                        YValue(origin.neighbors.get(i), this._map[0].length)));
                allPossiblePath_fetch(this._nodes[origin.neighbors.get(i)], path);
            }
        }
    }
    
    public void unfetchNodes() {
        for(int j = 0; j < this._nodes.length; j++)
            if(this._nodes[j] != null) this._nodes[j].fetched = false;
    }
    
    public void unfetchNode(int x, int y) {
        if(this._nodes[XYValue(x, y, this._map[y].length)] != null)
            this._nodes[XYValue(x, y, this._map[y].length)].fetched = false;
    }
    
    public boolean validateARule(Object o1, Object o2, boolean line) {
        for(int i = 0; i < this._rules.size(); i++) {
            if(this._rules.get(i).isValid(o1, o2, line))
                return true;
        }
        return false;
    }
    
    public boolean isValidObject(Object o1) {
        for(int i = 0; i < this._rules.size(); i++) {
            if(this._rules.get(i).getSource() == o1)
                return true;
        }
        return false;
    }
    
    public void addRule(Rule r) {
        this._rules.add(r);
    }
    
    public Node[] getNodes() {
        return this._nodes;
    }
    
    public static int XYValue(int x, int y, int size) {
        return x + (y * size);
    }

    public static int XValue(int value, int size) {
        return (int)(value % size);
    }

    public static int YValue(int value, int size) {
        return (int)(value / size);
    } 
    
}