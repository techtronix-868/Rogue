package game.displayable.structure;

public class Passage extends Structure{
    //passage connecting room1 to room 2
    int room1;
    int room2;
    String name; //name of passage
    public int[] posXArr = new int[100];
    public int[] posYArr = new int[100];
    public int idx = 0;
    //private int PosX;

    public Passage()
    {
        System.out.println("Creating Passage");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int room1, int room2){
        this.room1 = room1;
        this.room2 = room2;
        System.out.println("id set " + room1);
    }

    public void setPosX(int x){
        posXArr[idx] = x;
        System.out.println("   posX: " + x);
    }

    public void setPosY(int y){
        posYArr[idx++] = y;
        System.out.println("   posY: " + y);
    }

//    public void setPosX(int PosX) {
//        this.PosX = PosX;
//    }
}
