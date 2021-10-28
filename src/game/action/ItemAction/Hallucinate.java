package game.action.ItemAction;

import game.displayable.creatures.Creature;

public class Hallucinate extends ItemAction{
    Creature owner;


    public Hallucinate(Creature owner)
    {
        super(owner);
        this.owner = owner;
        System.out.println("creating item action ");
        System.out.println("creating Hallucinate item action");
    }
}
