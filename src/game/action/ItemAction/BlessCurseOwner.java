package game.action.ItemAction;

import game.displayable.creatures.Creature;

public class BlessCurseOwner extends ItemAction{
    Creature owner;

     public BlessCurseOwner(Creature owner)
    {
        super(owner);
        this.owner = owner;
        System.out.println("creating item action ");
        System.out.println("creating BlessCurseOwner item action");
    }

}
