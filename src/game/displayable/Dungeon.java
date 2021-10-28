package game.displayable;

import game.displayable.creatures.Creature;
import game.displayable.creatures.Monster;
import game.displayable.item.Item;
import game.displayable.item.Sword;
import game.displayable.structure.Passage;
import game.displayable.structure.Room;

import java.util.ArrayList;

public class Dungeon {
    protected String name;
    protected int width;
    protected int topHeight;
    protected int gameHeight;
    protected int bottomHeight;
    private static Dungeon uniqueInstance = null;

    public ArrayList<Room> rooms = new ArrayList<Room>();
    public ArrayList<Creature> creatures = new ArrayList<Creature>();
    public ArrayList<Item> items = new ArrayList<Item>();
    public ArrayList<Passage> passages = new ArrayList<Passage>();


    private Dungeon(String name, int width , int topHeight , int gameHeight , int bottomHeight)
    {
        this.name = name;
        this.width = width;
        this.topHeight = topHeight;
        this.gameHeight = gameHeight;
        this.bottomHeight = bottomHeight;
    }

    public static Dungeon getDungeon(String name, int width , int topHeight , int gameHeight , int bottomHeight) {
        if(uniqueInstance == null){
            uniqueInstance = new Dungeon(name, width, topHeight, gameHeight, bottomHeight);
        }
        return uniqueInstance;
    }

    public static Dungeon getDungeon(){
        return uniqueInstance;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getTopHeight() {
        return topHeight;
    }

    public void setTopHeight(int topHeight) {
        this.topHeight = topHeight;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public void setGameHeight(int gameHeight) {
        this.gameHeight = gameHeight;
    }

    public int getBottomHeight() {
        return bottomHeight;
    }

    public void setBottomHeight(int bottomHeight) {
        this.bottomHeight = bottomHeight;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void addCreature(Creature creature ) {
        creatures.add(creature);
        System.out.println("add creature");
    }

    public void addPassage(Passage passage) {
        passages.add(passage);

    }

    public void addItems(Item item) {
        items.add(item);
    }
}
