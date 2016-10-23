package com.kamigaku.dungeoncrawler.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kamigaku.dungeoncrawler.command.ICommand;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import com.kamigaku.dungeoncrawler.skills.Skill;

public class HUD {
	
    private final Stage stage;
    private final Skin uiSkin;
    private final TextureAtlas atlas;
    private final BitmapFont font;
    private final NinePatch empty, full, full_food;
    private final Table tbl_commandHistory;
    private final Table tbl_actionButtons;
    private final Table tbl_entityStats;

    public HUD() {
            this.stage = new Stage(new ScreenViewport());
            LevelManager.getLevelManager().getLevel().addInputProcessor(stage, true);
            this.uiSkin = new Skin(Gdx.files.internal("hud/uiskin.json"));
                        
            this.atlas = new TextureAtlas(Gdx.files.internal("hud/uiskin.atlas"));
            this.font = new BitmapFont(Gdx.files.internal("hud/futura_white.fnt"));

            this.tbl_commandHistory = new Table();
            tbl_commandHistory.setWidth(stage.getWidth());
            this.tbl_commandHistory.align(Align.left | Align.bottom);
            this.tbl_commandHistory.setPosition(10, 10);
            
            this.tbl_actionButtons = new Table();
            this.tbl_actionButtons.setWidth(60);
            this.tbl_actionButtons.align(Align.center | Align.bottom);
            this.tbl_actionButtons.setPosition(stage.getWidth() - 70, 10);
            
            this.tbl_entityStats = new Table();
            this.tbl_entityStats.setWidth(stage.getWidth());
            this.tbl_entityStats.align(Align.top | Align.left);
            this.tbl_entityStats.setPosition(10, stage.getHeight() - 10);            
            
            EndTurnButton etb = new EndTurnButton("End Turn", this.uiSkin);
            this.tbl_actionButtons.add(etb);
            this.tbl_actionButtons.row();

            Texture emptyT = new Texture(Gdx.files.internal("hud/empty2.png"));
            Texture fullT = new Texture(Gdx.files.internal("hud/full_health.png"));
            Texture fullFood = new Texture(Gdx.files.internal("hud/full_food.png"));

            this.empty = new NinePatch(new TextureRegion(emptyT,24,24),7,7,7,7);
            this.full = new NinePatch(new TextureRegion(fullT,24,24),7,7,7,7);
            this.full_food = new NinePatch(new TextureRegion(fullFood,24,24),7,7,7,7);
            
            this.stage.addActor(tbl_commandHistory);
            this.stage.addActor(tbl_actionButtons);
            this.stage.addActor(this.tbl_entityStats);
            
            //Inventory inv = new Inventory("coucou", uiSkin, false);
            this.displayFightTables(false);
    }

    public void draw() {
        this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        this.stage.draw();
    }
    
    public void displayFightTables(boolean visibility) {
        this.tbl_actionButtons.setVisible(visibility);
        this.tbl_commandHistory.setVisible(visibility);
    }

    public void dispose() {
        this.atlas.dispose();
        this.empty.getTexture().dispose();
        this.font.dispose();
        this.full.getTexture().dispose();
        this.full_food.getTexture().dispose();
        this.stage.dispose();
        this.uiSkin.dispose();
    }

    public Stage getStage() {
        return this.stage;
    }
    
    public Skin getSkin() {
        return this.uiSkin;
    }

    public void addCommand(ICommand command, int index) {
        CommandButton cb = new CommandButton(command.toString() + " x ", uiSkin, index);
        this.tbl_commandHistory.add(cb);
        this.tbl_commandHistory.row();
    }
    
    public void removeCommand(int index) {
        for(int i = this.tbl_commandHistory.getCells().size - 1; i >= index; i--) {
            this.tbl_commandHistory.removeActor(this.tbl_commandHistory.getCells().get(i).getActor());
            this.tbl_commandHistory.getCells().removeIndex(i);
        }
    }
    
    public void addActionCommand(Skill actionButton) {
        this.tbl_actionButtons.add(actionButton.getTextButton());
        this.tbl_actionButtons.row();
    }
    
    public void removeActionCommand(Skill textButton) {
    }

    public void displayEntityStats(IEntity currentOverlaps) {
        this.tbl_entityStats.clear();
        if(currentOverlaps != null) {
            Label entityName = new Label(currentOverlaps.getName() + " - (x: " + currentOverlaps.getPhysicsComponent().getPosition().x + "; y: " + currentOverlaps.getPhysicsComponent().getPosition().y + ")", this.uiSkin);
            Label stats = new Label(currentOverlaps.getStatistic().toString(), this.uiSkin);
            this.tbl_entityStats.add(entityName).left();
            this.tbl_entityStats.row();
            this.tbl_entityStats.add(stats).left();
        }
    }

}
