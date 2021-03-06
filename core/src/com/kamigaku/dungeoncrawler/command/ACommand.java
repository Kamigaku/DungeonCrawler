/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.command;

import com.kamigaku.dungeoncrawler.entity.IEntity;

/**
 *
 * @author Kamigaku
 */
public abstract class ACommand implements ICommand {

    protected ICommand _previousCommand;
    protected ICommand _nextCommand;
    protected IEntity _target;
    protected int _ap;
    
    @Override
    public void setNext(ICommand command) {
        this._nextCommand = command;
    }

    @Override
    public void setPrevious(ICommand command) {
        this._previousCommand = command;
    }
    
    @Override
    public ICommand getNext() {
        return this._nextCommand;
    }

    @Override
    public ICommand getPrevious() {
        return this._previousCommand;
    }
    
}
