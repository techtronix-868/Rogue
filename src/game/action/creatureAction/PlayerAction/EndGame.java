package game.action.creatureAction.PlayerAction;

import game.action.creatureAction.CreatureAction;
import game.displayable.creatures.Creature;

public class EndGame extends CreatureAction {
    String name;
    Creature owner;

    public EndGame(String name, Creature owner)
    {
        super(owner);
        this.owner = owner;
        this.name = name;
        System.out.println("Creating action");
        System.out.println("creating endgame player action");
    }

}
