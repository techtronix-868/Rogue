package game.action.creatureAction;

import game.displayable.creatures.Creature;

public class ChangedDisplayedType extends CreatureAction{
    String name;
    Creature owner;

    public ChangedDisplayedType(String name, Creature owner)
    {
        super(owner);
        this.name = name;
        this.owner = owner;
        System.out.println("Creating action");
        System.out.println("creating ChangedDisplayedType creature action");
    }
}
