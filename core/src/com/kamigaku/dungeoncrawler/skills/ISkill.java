package com.kamigaku.dungeoncrawler.skills;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import java.awt.Point;
import java.util.ArrayList;

public interface ISkill {
    
    ArrayList<Point> getRange();
    TextButton getTextButton();
    
}
