package com.kamigaku.dungeoncrawler.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HUD {
	
	private final Stage stage;
	private final Skin uiSkin;
	private final TextureAtlas atlas;
	private final BitmapFont font;
	private final NinePatch empty, full, full_food;
	private final Table table;
	
	private final static int SCALEXHP = 8;
	
	public HUD() {
		this.stage = new Stage(new ScreenViewport());
		this.uiSkin = new Skin(Gdx.files.internal("hud/uiskin.json"));
		this.atlas = new TextureAtlas(Gdx.files.internal("hud/uiskin.atlas"));
		this.font = new BitmapFont(Gdx.files.internal("hud/futura_white.fnt"));
		
		this.table = new Table(this.uiSkin);
		table.setFillParent(true);
		stage.addActor(table);
		
		Texture emptyT = new Texture(Gdx.files.internal("hud/empty2.png"));
		Texture fullT = new Texture(Gdx.files.internal("hud/full_health.png"));
                Texture fullFood = new Texture(Gdx.files.internal("hud/full_food.png"));
		
		this.empty = new NinePatch(new TextureRegion(emptyT,24,24),7,7,7,7);
		this.full = new NinePatch(new TextureRegion(fullT,24,24),7,7,7,7);
		this.full_food = new NinePatch(new TextureRegion(fullFood,24,24),7,7,7,7);
                
		this.table.bottom().left();
		
		//Hp bar
		Group hpGroup = new Group();
		Image img1 = new Image(this.empty);
		Image img2 = new Image(this.full);
		img1.setScale(SCALEXHP, 1f);
		img2.setScale(SCALEXHP, 0.98f);
		hpGroup.addActor(img1);
		hpGroup.addActor(img2);
                
                //Food bar
                Group foodGroup = new Group();
		Image food_img_1 = new Image(this.empty);
		Image food_img_2 = new Image(this.full_food);
		food_img_1.setScale(SCALEXHP, 1f);
		food_img_2.setScale(SCALEXHP, 0.98f);
		foodGroup.addActor(food_img_1);
		foodGroup.addActor(food_img_2);
		
		//Assemblage
		this.table.add(foodGroup).padLeft(20).padBottom(30);
                this.table.row();
                this.table.add(hpGroup).padLeft(20).padBottom(10);
                
                Inventory inv = new Inventory(stage, uiSkin);
		
	}
	
	public void draw() {
		this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		this.stage.draw();
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

}
