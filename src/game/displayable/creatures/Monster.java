package game.displayable.creatures;

public class Monster extends Creature{


    String name;
    int room;
    int serial;
    int Hp;
    int MaxHit;

    public Monster(){
        System.out.println("creating Creature");
        System.out.println("monster Created");
    }


    public void setName(String name) {
        this.name = name;
        System.out.println("name set "+name);
    }

    public String getName() {
        return name;
    }

    public void setID(int room,int serial) {
        this.room = room;
        this.serial = serial;
        System.out.println("id set "+room);
    }
}
