package game.displayable.structure;

import game.displayable.creatures.Creature;

public class Room extends Structure{
    String name; //name for room



    int roomId; //id for room
    Creature creatureVar; //variable to set creature
    private int PosX;

    public Room(String _name) {
        this.name = _name;
        System.out.println("Creating Room:");
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
        System.out.println("Room id set "+roomId);
    }

    public void setCreatureVar(Creature creatureVar) {
        this.creatureVar = creatureVar;
    }

    public int getRoomId() {
        return roomId;
    }

}
