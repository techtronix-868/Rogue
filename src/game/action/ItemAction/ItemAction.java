package game.action.ItemAction;

import game.action.Action;
import game.displayable.creatures.Creature;

public class ItemAction extends Action {
    Creature owner;

    public ItemAction(Creature owner)
    {
        this.owner = owner;
    }

}
