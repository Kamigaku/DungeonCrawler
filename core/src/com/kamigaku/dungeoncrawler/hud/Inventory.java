package com.kamigaku.dungeoncrawler.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Disposable;

public class Inventory extends Actor implements Disposable {
	
    private Window journalWindow;
    private TextArea journalContent;
    private boolean isDisplaying = false;

    public Inventory(String name, Skin uiSkin, boolean isDisabled) {
            this.journalWindow = new Window("Journal", uiSkin);
            this.journalWindow.setWidth(600);
            this.journalWindow.setHeight(400);
            this.journalContent = new TextArea("", uiSkin);
            this.journalContent.setDisabled(isDisabled);
            TextButton closeWindow = new TextButton("X", uiSkin);
            closeWindow.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            journalWindow.remove();
                            isDisplaying = false;
                            return true;
                    }
            });
            this.journalWindow.getTitleTable().add(closeWindow).height(this.journalWindow.getPadTop() - 4);
            this.journalWindow.setMovable(false);
            this.journalWindow.add(this.journalContent).size(300, 400);
            this.journalWindow.setPosition((Gdx.graphics.getWidth() * 0.5f) - 150, (Gdx.graphics.getHeight() * 0.5f) - 200);
            this.journalWindow.row();
            this.journalWindow.pack();
    }

    public void display(Stage stage) {
            isDisplaying = !isDisplaying;
            this.journalContent.setDisabled(false);
            if(isDisplaying) {
                    stage.addActor(this.journalWindow);
            }
            else {
                    this.journalContent.setText(this.journalContent.getText().replace('|', ' '));
                    this.journalWindow.remove();
            }
    }

    public void setContent(String text) {
            this.journalContent.setText(text);
    }

    public void close() {
            this.journalWindow.remove();
            this.journalContent.setText(this.journalContent.getText().replace('|', ' '));
            this.isDisplaying = false;
    }

    @Override
    public void dispose() {
    }

	
}
