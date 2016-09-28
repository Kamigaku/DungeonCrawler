package com.kamigaku.dungeoncrawler.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;

public class Tileset {

    public Sprite[] sprites;
    
    /**
     * Start from top left to bottom right
     * @param filePath The inner path
     * @param widthTile Width of a single tile
     * @param heightTile Height of a single tile
     */
    public Tileset(String filePath, int widthTile, int heightTile){
        Texture t = (Texture)LevelManager.getLevelManager().getAssetManager().get(filePath, Texture.class);
        int widthTexture = t.getWidth();
        int heightTexture = t.getHeight();
        int numberLine = heightTexture / heightTile;
        int numberColumn = widthTexture / widthTile;
        this.sprites = new Sprite[numberLine * numberColumn];
        for(int i = 0; i < numberColumn; i++) {
            for(int j = 0; j < numberLine; j++) {
                TextureRegion tr = new TextureRegion(t, j * widthTile, i * heightTile, widthTile, heightTile);
                sprites[(i * numberColumn) + j] = new Sprite(tr);
            }
        }
    }
    
    public void dispose() {
        for(int i = 0; i < this.sprites.length; i++) {
            this.sprites[i].getTexture().dispose();
        }
    }
    
}
