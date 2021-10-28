package game.displayable;

public class Displayable {
    int visible = 0;



    int maxhit;
    int hpMoves;
    int Hp;
    char type;
    int val;
    int posX;
    int posY;
    int width;
    int height;

    public Displayable() {

    }

    public void setVisible() {
            System.out.println("Visible set 1");
            this.visible = 1;
    }

    public void setMaxHit(int max){
        maxhit = max;
        System.out.println("max hits set "+max);
    }

    public int getMaxhit() {
        return maxhit;
    }

    public void setHpMoves(int hpMoves) {
        this.hpMoves = hpMoves;
        System.out.println("Set Hp Moves "+hpMoves);
    }


    public void setHp(int hp) {
        this.Hp = hp;
        System.out.println("setHP "+hp);
    }

    public int getHp() {
        return Hp;
    }

    public void setType(char type) {
        this.type = type;
        System.out.println("Type set "+type);
    }

    public char getType() {
        return type;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public void setPosX(int posX) {
        this.posX = posX;
        System.out.println("x pos set "+posX);
    }

    public void setPosY(int posY) {
        this.posY = posY;
        System.out.println("y pos set "+posY);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setWidth(int width) {
        this.width = width;
        System.out.println("set width "+width);
    }

    public void setHeight(int height) {
        this.height = height;
        System.out.println("set height "+height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
