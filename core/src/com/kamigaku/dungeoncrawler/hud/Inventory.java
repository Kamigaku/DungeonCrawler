package com.kamigaku.dungeoncrawler.hud;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Disposable;

public class Inventory implements Disposable {

    //Une fenêtre déplacable
    //Des cases
    //Butin
    private final Window _window;
    
    public Inventory(Stage stage, Skin uiSkin) {
        this._window = new Window("Butin", uiSkin);
        stage.addActor(this._window);
    }

    @Override
    public void dispose() {
    }
	
}
