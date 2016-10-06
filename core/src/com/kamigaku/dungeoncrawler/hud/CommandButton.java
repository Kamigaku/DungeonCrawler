package com.kamigaku.dungeoncrawler.hud;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;

public class CommandButton extends TextButton {
    
    private final int _index;
    
    public CommandButton(String text, Skin skin, int index) {
        super(text, skin);
        this._index = index;
        this.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(button == Input.Buttons.LEFT) {
                    LevelManager.getLevelManager().getLevel().getMainPlayer().removeCommand(_index);
                }
                return true;
            }
        });
    }
    
}
