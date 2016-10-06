package com.kamigaku.dungeoncrawler.entity.implementation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.kamigaku.dungeoncrawler.command.ICommand;
import com.kamigaku.dungeoncrawler.component.InputComponent;
import com.kamigaku.dungeoncrawler.component.SensorComponent;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.entity.AEntity;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import java.util.ArrayList;

public class Player extends AEntity {
    
    // A MODIFIER PLUS TARD ICI, test voir si cela fonctionne
    public final ArrayList<ICommand> _commands;
    
    private final InputComponent _input;
    
    private final int ACTIONPOINT = 5;
    private final int HEALTHPOINT = 10;

    public Player(Sprite sprite, float x, float y) {
        super.baseLoading(sprite, 0, -0.25f, BodyType.DynamicBody, Constants.CATEGORY_PLAYER,
                (short) (Constants.CATEGORY_SCENERY | Constants.CATEGORY_MONSTER),
                x, y, 8, 8);
        super.initStatistic(ACTIONPOINT, HEALTHPOINT);
        this._input = new InputComponent(this);
        this._sensors.add(new SensorComponent(this, BodyType.DynamicBody, Constants.CATEGORY_PLAYER, 
                                                Constants.CATEGORY_SCENERY, 20f));
        this._commands = new ArrayList<ICommand>();
    }
    
    public Player(String sprite, float x, float y) {
        super.baseLoading(sprite, 0, 0, BodyType.DynamicBody, Constants.CATEGORY_PLAYER,
                (short) (Constants.CATEGORY_SCENERY | Constants.CATEGORY_MONSTER),
                x, y, 8, 8);
        super.initStatistic(ACTIONPOINT, HEALTHPOINT);
        this._input = new InputComponent(this);
        this._sensors.add(new SensorComponent(this, BodyType.DynamicBody, Constants.CATEGORY_PLAYER, 
                                                Constants.CATEGORY_SCENERY, 20f));
        this._commands = new ArrayList<ICommand>();
    }
    
    public void addCommand(ICommand command) {
        this._commands.add(command);
        int index = this._commands.size() - 1;
        LevelManager.getLevelManager().getLevel().getHUD().addCommand(command, index);
        if(index > 0) {
            command.setPrevious(this._commands.get(index - 1));
            command.getPrevious().setNext(command);
        }
        command.execute();
    }
    
    public void removeCommand(int index) {
        for(int i = this._commands.size() - 1; i >= index; i--) {
            this._commands.get(i).reverse();
            this._commands.remove(i);
        }
        if(index > 0) {
            this._commands.get(index - 1).setNext(null);
        }
        LevelManager.getLevelManager().getLevel().getHUD().removeCommand(index);
    }

    @Override
    public void beginContact(Contact contact) {
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }
    
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        
    }
    
    public InputComponent getInputComponent() {
        return this._input;
    }

}
