package com.kamigaku.dungeoncrawler.command;

public interface ICommand {
    
    void execute();
    void reverse();
    void setPrevious(ICommand command);
    void setNext(ICommand command);
    ICommand getPrevious();
    ICommand getNext();
    
}
