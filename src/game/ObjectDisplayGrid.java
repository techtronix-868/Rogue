package game;


//import asciiPanel.asciiPanel.AsciiPanel;

import asciiPanelGame.asciiPanel.AsciiPanel;
import game.displayable.Dungeon;
import asciiPanelGame.*;
import game.displayable.creatures.Creature;
import game.displayable.creatures.Monster;
import game.displayable.creatures.Player;
import game.displayable.item.Armor;
import game.displayable.item.Item;
import game.displayable.item.Scroll;
import game.displayable.item.Sword;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ObjectDisplayGrid  extends JFrame implements KeyListener,InputSubject {
    int gameHeight;
    int width;
    int topHeight;

    private static final int DEBUG = 0;
    private static final String CLASSID = ".ObjectDisplayGrid";
    private char lastKey = 0;

    private static ObjectDisplayGrid uniqueinstance = null;
    private static Rogue rogue = null;

    private static AsciiPanel terminal;
    private Char[][] objectGrid = null;
    private List<InputObserver> inputObservers = null;
    private static Dungeon dungeon=null;
    private boolean showPack = false;
    private boolean endGame = false;
    public List<Item> pack = new ArrayList<Item>();

    private ObjectDisplayGrid(int gameHeight , int width , int topHeight)
    {
        this.gameHeight = gameHeight;
        this.width = width;
        this.topHeight = topHeight;

        dungeon = Dungeon.getDungeon();

        terminal = new AsciiPanel(width, gameHeight);

        objectGrid = new Char[width][gameHeight];

        initializeDisplay();
        super.add(terminal);
        super.setSize(width * 9, (gameHeight+6) * 16);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        super.setVisible(true);
        terminal.setVisible(true);
        super.addKeyListener(this);
        inputObservers = new ArrayList<>();
        super.repaint();

        initPack();

    }

    private void initializeDisplay() {
        Char ch = new Char( ' ' );
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < gameHeight; j++) {
                addObjectToDisplay( ch, i, j );
            }
        }
        terminal.repaint();
    }

    public void addObjectToDisplay(Char ch, int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y] = ch;
                writeToTerminal(x, y);
            }
        }
    }

    private void writeToTerminal(int x, int y) {
        char ch = objectGrid[x][y].getChar();
        terminal.write(ch, x, y);
        terminal.repaint();
    }

    public static ObjectDisplayGrid getObjectDisplayGrid(int gameHeight , int width , int topHeight){
        if(uniqueinstance == null) {
            //System.out.println(gameHeight);
            uniqueinstance = new ObjectDisplayGrid(gameHeight, width, topHeight);
        }
        return uniqueinstance;
    }

    public void setTopHeight(int topHeight) {
        this.topHeight = topHeight;
    }


    @Override
    public void keyTyped(KeyEvent e) {;
        if (DEBUG > 0) { System.out.println(CLASSID + ".keyTyped entered" + e.toString()); }
        char key = e.getKeyChar();
        int i = 0 ;
        while (i < dungeon.creatures.size()) {
            if(dungeon.creatures.get( i ).getClass() == Player.class){
                Player temp = (Player) dungeon.creatures.get( i );
                if(lastKey == 0 && endGame == false){
                        System.out.println(ifMoveable(temp.getPosX(), temp.getPosY()-1));
                        if(key == 'k' && ifMoveable(temp.getPosX(), temp.getPosY()-1)) {
                            temp.setPosY(temp.getPosY()-1);

                        }
                        else if(key == 'j' && ifMoveable(temp.getPosX(), temp.getPosY()+1)) {
                            temp.setPosY(temp.getPosY()+1);
                        }
                        else if(key == 'h' && ifMoveable(temp.getPosX()-1, temp.getPosY())) {
                            temp.setPosX(temp.getPosX()-1);
                        }
                        else if(key == 'l' && ifMoveable(temp.getPosX()+1, temp.getPosY())) {
                            temp.setPosX(temp.getPosX()+1);
                        }
                        else
                        {
                            switch(key){
                                case '?':   System.out.println("h,l,k,j,i,?,H,c,d,p,R,T,w,E,0-9. H <cmd> for more info");break;
                                case 'H':   lastKey = 'H';break;
                                case 'E':   lastKey = 'E';break;
                                case 'r':   readScroll(Character.getNumericValue(key));break;
                                case 'w':   equipArmor(Character.getNumericValue(key));break;
                                case 't':   equipWeapon(Character.getNumericValue(key));break;
                                case 'i':   showPack = !showPack;packButton();;break;
                                case 'c':   lastKey = 'c';break;
                                case 'd':   removeItem(temp.getPosX(), temp.getPosY(), Character.getNumericValue(key));break;
                                case 'p':   appendItem(temp.getPosX(), temp.getPosY());break;

                            }
                        }

                } else if(lastKey == 0) {
                    if(key == 'H') { lastKey = 'H'; }
                    else if(key == 'E') { lastKey = 'E'; }
                } else if(lastKey == 'H') {
                    if(key == 'k') { System.out.println("k: move up"); }
                    else
                    {
                        switch(key){
                            case 'j': System.out.println("j: move down");break;
                            case 'h': System.out.println("h: move left");break;
                            case 'l': System.out.println("l: move right");break;
                            case 'i': System.out.println("i: inventory -- show pack contents");break;
                            case 'c': System.out.println("c: take off/change armor");break;
                            case 'd': System.out.println("d: drop <item number> item from pack");break;
                            case 'p': System.out.println("p: pick up item under player and put in pack");break;
                            case 'r': System.out.println("r: read scroll <item number> item from pack");break;
                            case 'w': System.out.println("w: wear armor <item number> item from pack");break;
                            case 't': System.out.println("t: take out weapon from pack");break;

                        }
                        lastKey = 0;
                    }
                } else if (lastKey == 'E') {
                    if (key == 'Y' || key == 'y') {
                        System.exit( 1 );
                    }
                    lastKey = 0;
                }

            }

            i++;
        }
        Rogue rogue = Rogue.getRogue();
        rogue.display();

    }


    public void fireUp() {
        if (terminal.requestFocusInWindow()) { System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow Succeeded"); }
        else { System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow FAILED"); }
    }

    private boolean ifMoveable(int x, int y){
        char ch = objectGrid[x][y+2].getChar();
        if(ch == 'T' || ch == 'H' || ch == 'S') {
            int i=0;
            while (i<dungeon.creatures.size()) {
                if(x == dungeon.creatures.get(i).getPosX() && y == dungeon.creatures.get(i).getPosY()) {
                    Monster monster = (Monster) dungeon.creatures.get(i);
                    fight(monster, i);
                }
                i++;
            }
        } else if(ch == '.' || ch == '#' || ch == '+' || ch == '?' || ch == ']' || ch == ')') { return true; }
        return false;
    }

    private void fight(Monster monster, int j) {
        for(int i=0; i<dungeon.creatures.size(); i++) {
            if(dungeon.creatures.get(i).getClass() == Player.class) {
                Player player = (Player) dungeon.creatures.get(i);
                double val = Math.round(Math.random()*player.getMaxhit());
                monster.setHp((int)(monster.getHp() - val));
                resetRow(dungeon.getGameHeight());
                displayString(monster.getName()+": - "+(int)val+"HP", 6, dungeon.getGameHeight());
                if(monster.getHp() < 1) {
                    doActions(monster, "death", "death");
                }
                double val2 = Math.round(Math.random()*monster.getMaxhit());
                player.setHp((int)(player.getHp() - val2));
                if(player.getHp() < 1) {
                    player.setHp(0);
                    doActions(player, "death", "death");
                }
                if(endGame == false) {
                    displayString("Player: - "+(int)val2+"HP", 22, dungeon.getGameHeight());
                }
            }
        }
    }

    private void appendItem(int x, int y) {
        for (int i = dungeon.items.size() - 1; i >= 0; i--) {
            Item item = dungeon.items.get( i );
            if (item.getPosX() == x && item.getPosY() == y) {
                pack.add( item );
                dungeon.items.remove( i );
                resetRow( dungeon.getGameHeight()+4 );
                displayString( "Picked up " + item.getName(), 6, dungeon.getGameHeight()+4 );
                break;
            }

        }
    }

    private void initPack() {

        //System.out.println("asndxjaksmx "+dungeon.items.size());
        for(int i=0; i<dungeon.items.size(); i++) {
            //System.out.println(i+": "+dungeon.items.get(i).getClass());
            //if(dungeon.items.get(i).getClass() == Sword.class || dungeon.items.get(i).getClass() == Armor.class) {
            if((Player) dungeon.items.get(i).getOwner() != null){
                Player player = (Player) dungeon.items.get( i ).getOwner();
//                pack.add( dungeon.items.get( i ) );
//                dungeon.items.remove( i );
                appendItem( dungeon.items.get(i).getPosX(),dungeon.items.get(i).getPosY() );
                //System.out.println(pack.get(0).getName());
                i--;

            }
        }
    }

    private void equipArmor(int x) {
        int j;
        for( j = pack.size()-1; j >= 0 ; j--){
            if(pack.get( j ).getClass()==Armor.class){
                break;
            }
        }
        if (j<0) {
            resetRow( dungeon.getGameHeight() +4);
            displayString( "No armor to equip",6,dungeon.getGameHeight()+4 );
            return;
        }

        else if(j==0 && pack.get(j).getClass() != Armor.class){
            resetRow( dungeon.getGameHeight() +4);
            displayString( "No armor to equip",6,dungeon.getGameHeight()+4 );
            return;
        }
        for(int i=0; i<dungeon.creatures.size(); i++) {
            if(dungeon.creatures.get(i).getClass() == Player.class) {
                Player player = (Player) dungeon.creatures.get( i );
                player.setArmor( (Armor) pack.get( j ) );
                resetRow( dungeon.getGameHeight() +4);
                displayString("Wearing armor: "+pack.get(j).getName(), 6, dungeon.getGameHeight()+4);
                pack.remove(j);
            }
        }
    }

    private void equipWeapon(int x) {
        int j;
        for( j = pack.size()-1; j >= 0 ; j--){
            if(pack.get( j ).getClass()==Sword.class){
                break;
            }
        }
        if (j<0) {
            resetRow( dungeon.getGameHeight() +4);
            displayString( "No weapon to equip",6,dungeon.getGameHeight()+4 );
            return;
        }

        else if(j==0 && pack.get(j).getClass() != Sword.class){
            resetRow( dungeon.getGameHeight() +4);
            displayString( "No weapon to equip",6,dungeon.getGameHeight()+4 );
            return;
        }
        for(int i=0; i<dungeon.creatures.size(); i++) {
            if(dungeon.creatures.get(i).getClass() == Player.class) {
                Player player = (Player) dungeon.creatures.get( i );
                player.setWeapon( (Sword) pack.get( j ) );
                resetRow( dungeon.getGameHeight() +4);
                displayString( "Equiped weapon: " + pack.get( j ).getName(), 6, dungeon.getGameHeight()+4 );
                pack.remove(j);
            }
        }
    }

    private void readScroll(int x) {
        int j;
        for( j = pack.size()-1; j >= 0 ; j--){
            if(pack.get( j ).getClass()==Scroll.class){
                break;
            }
        }
        System.out.println(pack.get( 0 ).getClass());
        System.out.println(Armor.class);
        System.out.println(pack.contains(game.displayable.item.Armor.class ));
        for(int i=0; i<dungeon.creatures.size(); i++) {
            if (dungeon.creatures.get( i ).getClass() == Player.class) {
                Scroll scroll = (Scroll) pack.get(j);
                displayString(scroll.getItemAction().getMsg(), 6, dungeon.getGameHeight());
                Player player = (Player) dungeon.creatures.get( i );
                if (scroll.getItemAction().getClass().getSimpleName().equals( "BlessArmor" )) {
                    System.out.println( "LODA " + pack.contains( Armor.class ) );
                    for (int k = 0; k < pack.size(); k++) {
                        if (pack.get( k ).getClass().equals( Armor.class )) {
                            Armor armor = (Armor) pack.get( k );
                            if (scroll.getItemAction().getC() == 'a') {
                                System.out.println( "loda" );
                                armor.setItemIntValue( armor.getItemIntValue() + scroll.getItemAction().getValue() );
                            } else if (scroll.getItemAction().getC() == 's') {
                                System.out.println( "loda1" );
                                armor.setItemIntValue( armor.getItemIntValue() - scroll.getItemAction().getValue() );
                            }
                        }
                    }
                    if(player.getArmor() != null)
                    {
                        Armor armor = (Armor) player.getArmor();
                        //System.out.println( scroll.getType() );
                        if (scroll.getItemAction().getC() == 'a') {
                            System.out.println( "loda" );
                            armor.setItemIntValue( armor.getItemIntValue() + scroll.getItemAction().getValue() );
                        } else if (scroll.getItemAction().getC() == 's') {
                            System.out.println( "loda1" );
                            armor.setItemIntValue( armor.getItemIntValue() - scroll.getItemAction().getValue() );
                        }
                    }

                } else if (scroll.getItemAction().getClass().getSimpleName().equals( "BlessCurseOwner" )) {
                    Armor armor = (Armor) player.getArmor();
                    armor.setItemIntValue( armor.getItemIntValue() - scroll.getItemAction().getValue() );
                }
                break;
            }

        }
        pack.remove( j);

    }


    private void doActions(Creature creature, String type, String name) {
        if(type.equals("death")) {
            if(creature.getClass() == Player.class)
            {
                endGame = true;
                resetRow( dungeon.getGameHeight() +4);
                displayString( "Game has ended",6,dungeon.getGameHeight()+4 );
            }
            else
            {
                Monster monster = (Monster) creature;
                resetRow( dungeon.getGameHeight() +4);
                displayString( monster.getName() + " Has been slayed ",6, dungeon.getGameHeight()+4 );
                dungeon.creatures.remove(creature);
            }
        }

//        if(type.equals("hit")){
//
//        }

//        for(int i=0; i<creature.creatureActions.size(); i++) {
//            CreatureAction action = creature.creatureActions.get(i);
//            if(action.type.equals(type)) {
//                if(action.msg != null) {
//                    resetRow(dungeon.gameHeight);
//                    displayString(action.msg, 6, dungeon.gameHeight);
//                }
//                if(type.equals("hit")) {
//                    if(name.equals("DropPack")) {
//                        dropItem(creature.posX, creature.posY, pack.size());
//                    }
//                } else if(type.equals("death")) {
//                    if(action.name.equals("ChangeDisplayedType")) {
//                        Player player = (Player) creature;
//                        player.setDisplayedType(action.c);
//                    }
//                }
//            }
//        }
    }

    private void removeItem(int x , int y , int i){
        if(pack.size() >= 1 && i>0){
            Item item = pack.get( pack.size()-1 );
            item.setPosX( x );
            item.setPosY( y );
            dungeon.items.add( item );
            pack.remove( item );

        }

    }


    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void registerInputObserver(InputObserver observer) {

    }

    public void packButton(){
        resetRow(dungeon.getGameHeight() + 2);
        if (showPack){
            if(pack.size()>0){
                resetRow( dungeon.getGameHeight() +2);
                for (int i = 0; i < pack.size(); i++){
                    System.out.println(pack.get(i).getClass().getSimpleName());
                    displayString(pack.get(i).getClass().getSimpleName(), 6+i*7, dungeon.getGameHeight()+2);
                }
            }
            else{
                resetRow( dungeon.getGameHeight() +2);
                displayString("The Pack is empty", 7, dungeon.getGameHeight()+2);
            }
        }
    }

    public void displayString(String msg, int x, int y){
        for(int i=0; i<msg.length(); i++ ){ addObjectToDisplay(new Char(msg.charAt(i)), x+i, y); }
    }

    public void resetRow(int y) {
        for(int i=6; i<width; i++) {
            addObjectToDisplay(new Char(' '), i, y);
        }
    }
}
