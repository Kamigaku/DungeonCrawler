package com.kamigaku.dungeoncrawler.utility;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public abstract class Utility {

    public static ArrayList<Vector2> commonCoords(ArrayList<Vector2> v1, ArrayList<Vector2> v2) {
        ArrayList<Vector2> v3 = new ArrayList<Vector2>();
        for(Vector2 v : v1) {
            if(v2.contains(v))
                v3.add(v);
        }
        return v3;
    }
    
}
