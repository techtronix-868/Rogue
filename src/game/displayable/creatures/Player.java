package game.displayable.creatures;

import game.displayable.item.Item;

public class Player extends Creature{


    Item weapon;
    Item armor;
    int room;
    int serial;
    int MaxHit;


    char displayedType = '@';

    public Player(){
        System.out.println("creating Creature");
        System.out.println("player Created");
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
        this.setMaxHit( this.getMaxhit()+weapon.getItemIntValue() );
    }

    public Item getWeapon() {
        return weapon;
    }

    public Item getArmor() {
        return armor;
    }


    public void setArmor(Item armor) {
        this.armor = armor;
        this.setHp((this.getHp()+armor.getItemIntValue()));
    }

    public void setID(int room,int serial) {
        this.room = room;
        this.serial = serial;
        System.out.println("id set "+room);
    }

    public char getDisplayedType() {
        return displayedType;
    }

    public void setDisplayedType(char displayedType) {
        this.displayedType = displayedType;
    }
}
