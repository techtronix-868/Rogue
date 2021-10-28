package game.action.ItemAction;

import game.displayable.creatures.Creature;

public class BlessArmor extends ItemAction{
    Creature owner;

    public BlessArmor(Creature owner)
    {
        super(owner);
        this.owner = owner;
        System.out.println("creating item action ");
        System.out.println("creating BlessArmor item action");
    }
}
