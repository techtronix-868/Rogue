package game.displayable.item;

public class Sword extends Item {
    String name;
    int room;
    int serial;

    public Sword(String _name){
        name = _name;
        System.out.println("creating Item");
        System.out.println(_name + " created");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int room, int serial){
        System.out.println("id set " +room);
        this.room = room;
        this.serial = serial;
    }

    public String getName() {
        return name;
    }
}
