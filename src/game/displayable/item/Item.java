package game.displayable.item;
import game.action.ItemAction.ItemAction;
import game.displayable.Displayable;
import game.displayable.creatures.Creature;

public class Item extends Displayable {

    private Creature owner;
    String name;
    private int room;
    int ItemIntValue;


    ItemAction itemAction;

    public Item() {
    }

    public void setOwner(Creature owner) {
        this.owner = owner;
    }

    public Creature getOwner() {
        return owner;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setItemIntValue(int itemIntValue) {
        this.ItemIntValue = itemIntValue;
        System.out.println("Item Int Value set " + itemIntValue);
    }

    public ItemAction getItemAction() {
        return itemAction;
    }

    public void setItemAction(ItemAction itemAction) {
        this.itemAction = itemAction;
    }

    public int getItemIntValue() {
        return ItemIntValue;
    }



}
