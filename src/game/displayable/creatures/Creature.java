package game.displayable.creatures;

import game.action.creatureAction.CreatureAction;
import game.displayable.Displayable;
import game.displayable.structure.Room;

public class Creature extends Displayable {
    int Hp;  // Hp according do the parserXml.pdf
    int healthmoves; // Hpm
    CreatureAction DeathAction; // Death action
    CreatureAction HitAction;
    int room;
    int serial;



//    public void setHealth(int health) {
//        this.health = health;
//    }
//
//    public void setHealthmoves(int healthmoves) {
//        this.healthmoves = healthmoves;
//    }

    public void setDeathAction(CreatureAction deathAction) {
        DeathAction = deathAction;
        System.out.println("set death Action " +deathAction.getClass().getSimpleName());
    }

    public void setHitAction(CreatureAction hitAction) { //what is the Ha type?? not mentioned anywhere in documentation
        HitAction = hitAction;
        System.out.println("set Hit Action " +hitAction.getClass().getSimpleName());
    }

    public CreatureAction getDeathAction() {
        return DeathAction;
    }

    public CreatureAction getHitAction() {
        return HitAction;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }




}
