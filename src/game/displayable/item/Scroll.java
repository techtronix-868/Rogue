package game.displayable.item;

public class Scroll extends Item{
    String name;
    int room;
    int serial;

    public Scroll(String _name){
        System.out.println("creating Item");
        System.out.println(_name + " created");
        this.name = _name;
    }

    public void setID(int room , int serial) {
        System.out.println("id set " +room);
        this.room = room;
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

}
