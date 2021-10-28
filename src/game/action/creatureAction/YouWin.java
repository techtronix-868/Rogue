package game.action.creatureAction;

import game.displayable.creatures.Creature;

public class YouWin extends CreatureAction{
    String name;
    Creature owner;

    public YouWin(String name, Creature owner)
    {
        super(owner);
        this.name = name;
        this.owner = owner;
        System.out.println("Creating action");
        System.out.println("creating YouWin creature action");
    }
}
