package game.action.creatureAction;
import game.action.Action;
import game.displayable.creatures.Creature;

public class CreatureAction extends Action {
    Creature owner;

    public CreatureAction(Creature owner)
    {
        this.owner = owner;
    }

}
