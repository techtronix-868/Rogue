package game.action;

public class Action {

    String msg; // Message
    int value; // value



    char c; // Character Value


    String type;


    public void setMsg(String msg) {
        System.out.println("set action message " +msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setValue(int value) {
        System.out.println("action Int Value set " +value);
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public void setC(char c) {
        System.out.println("action Char Value set "+c);
        this.c = c;
    }

    public char getC() {
        return c;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
