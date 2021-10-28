package game;

import asciiPanelGame.Char;
import game.displayable.Dungeon;
import game.displayable.creatures.Creature;
import game.displayable.creatures.Player;
import game.displayable.structure.Passage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Rogue {

    private static ObjectDisplayGrid displayGrid = null;
    private static Rogue uniqueinstance = null;
    private static Dungeon dungeon;


    private Rogue(int width, int height , int topHeight) {
        uniqueinstance = this;
        displayGrid = ObjectDisplayGrid.getObjectDisplayGrid(height,width,topHeight);
        display();

    }

    public Rogue getRogue(int width, int height , int topHeight){
        if(uniqueinstance == null) {
            uniqueinstance = new Rogue( width, height, topHeight );
        }
        return uniqueinstance;
    }

    public static Rogue getRogue(){
        return uniqueinstance;
    }


    public void display() {

        int hp = 0;

        // Display Walls
        for (int i = dungeon.rooms.size() - 1; i >= 0; i--) {
            for (int j = 0; j < dungeon.rooms.get(i).getWidth(); j++) {
                displayGrid.addObjectToDisplay(new Char('X'), dungeon.rooms.get(i).getPosX() + j, dungeon.rooms.get(i).getPosY()+dungeon.getTopHeight());
                displayGrid.addObjectToDisplay(new Char('X'), dungeon.rooms.get(i).getPosX() + j, dungeon.rooms.get(i).getPosY() + dungeon.getTopHeight() + dungeon.rooms.get(i).getHeight() - 1);
            }
            for (int j = 1; j < dungeon.rooms.get(i).getHeight() - 1; j++) {
                displayGrid.addObjectToDisplay(new Char('X'), dungeon.rooms.get(i).getPosX(), dungeon.rooms.get(i).getPosY()+dungeon.getTopHeight()+ j);
                displayGrid.addObjectToDisplay(new Char('X'), dungeon.rooms.get(i).getPosX() + dungeon.rooms.get(i).getWidth() - 1, dungeon.rooms.get(i).getPosY()+dungeon.getTopHeight() + j);
                for (int k = 1; k < dungeon.rooms.get(i).getWidth() - 1; k++) {
                    displayGrid.addObjectToDisplay(new Char('.'), dungeon.rooms.get(i).getPosX() + k, dungeon.rooms.get(i).getPosY()+dungeon.getTopHeight() + j);
                }
            }
        }

        // Display Passages
        {
            int i = 0;
            while (i < dungeon.passages.size()) {
                Passage passage = dungeon.passages.get(i);
                int x = passage.posXArr[0];
                int y = passage.posYArr[0];
                int j;
                displayGrid.addObjectToDisplay(new Char('+'), x, y+dungeon.getTopHeight());
                for (j = 0; j < passage.idx; j++) {
                    if (x == passage.posXArr[j + 1]) {
                        int k = 1;
                        if (k < passage.posYArr[j + 1] - y) {
                            while (k <= passage.posYArr[j + 1]- y) { displayGrid.addObjectToDisplay(new Char('#'), x, y + k++ + dungeon.getTopHeight()); }
                        } else {
                            while (k > passage.posYArr[j + 1]  - y) { displayGrid.addObjectToDisplay(new Char('#'), x, y - 1 + k-- +dungeon.getTopHeight()); }
                        }
                    } else {
                        int k = 1;
                        while (k <= passage.posXArr[j + 1] - x) { displayGrid.addObjectToDisplay(new Char('#'), x + k++, y + dungeon.getTopHeight()); }
                    }
                    x = passage.posXArr[j + 1];
                    y = passage.posYArr[j + 1];
                }
                displayGrid.addObjectToDisplay(new Char('+'), passage.posXArr[j - 1], passage.posYArr[j - 1]+dungeon.getTopHeight());
                i++;
            }
        }


        // Display Items
        for (int i = dungeon.items.size() - 1; i >= 0; i--) {
            int room = dungeon.items.get(i).getRoom()-1 ;
            int x = dungeon.rooms.get(room).getPosX() + dungeon.items.get(i).getPosX();
            int y = dungeon.rooms.get(room).getPosY() + dungeon.items.get(i).getPosY();
            if (dungeon.items.get(i).getClass() == game.displayable.item.Scroll.class) { displayGrid.addObjectToDisplay(new Char('?'), x, y+dungeon.getTopHeight()); }
            else if (dungeon.items.get(i).getClass() == game.displayable.item.Armor.class) { displayGrid.addObjectToDisplay(new Char(']'), x, y+dungeon.getTopHeight()); }
            else if (dungeon.items.get(i).getClass() == game.displayable.item.Sword.class) { displayGrid.addObjectToDisplay(new Char(')'), x, y+dungeon.getTopHeight()); }
        }

        for (int i = dungeon.creatures.size() - 1; i >= 0; i--) {
            int x = dungeon.creatures.get(i).getPosX();
            int y = dungeon.creatures.get(i).getPosY();
            if (dungeon.creatures.get(i).getClass() == game.displayable.creatures.Player.class) {
                Player player = (Player) dungeon.creatures.get(i);
                hp = player.getHp();
                displayGrid.addObjectToDisplay(new Char(player.getDisplayedType()), x, y+dungeon.getTopHeight());
            }
            else {
                displayGrid.addObjectToDisplay(new Char(dungeon.creatures.get(i).getType()), x, (y+dungeon.getTopHeight()));
            }
        }

        displayGrid.resetRow(0);
        displayGrid.addObjectToDisplay(new Char(' '), 5, 0);
        displayGrid.displayString("HP: "+hp, 0, 0);
        displayGrid.displayString("core:  0", 8, 0);
        displayGrid.displayString("Pack: ", 0, dungeon.getGameHeight()-4+dungeon.getTopHeight()+ dungeon.getBottomHeight());
        displayGrid.displayString("Info: ", 0, dungeon.getGameHeight()-2+dungeon.getTopHeight()+ dungeon.getBottomHeight());

    }

    public static void main(String[] args) {

        // check if a filename is passed in.  If not, print a usage message.
        // If it is, open the file
        String fileName = null;
        switch (args.length) {
            case 1:
                /******************************************************************
                 * note that the relative file path may depend on what IDE you are
                 * using.  You might needs to add to the beginning of the filename,
                 * e.g., filename = "src/xmlfiles/" + args[0]; worked for me in
                 * netbeans, what is here works for me from the command line in
                 * linux.
                 ******************************************************************/
                fileName = "src/xmlFiles/" + args[0];
                break;
            default:
                System.out.println("java Test <xmlfilename>");
                return;
        }

        // Create a saxParserFactory, that will allow use to create a parser
        // Use this line unchanged
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        // We haven't covered exceptions, so just copy the try { } catch {...}
        // exactly, // filling in what needs to be changed between the open and
        // closed braces.
        try {
            // just copy this
            SAXParser saxParser = saxParserFactory.newSAXParser();
            // just copy this
            DungeonXMLHandler handler = new DungeonXMLHandler();
            // just copy this.  This will parse the xml file given by fileName
            saxParser.parse(new File (fileName), handler);
            // This will change depending on what kind of XML we are parsing
            ArrayList<Dungeon> dungeons = handler.getDungeon();
            //Dungeon dungeon1 =

            Dungeon dungeonBeingParsed= Dungeon.getDungeon();
            System.out.println("Dungeon var: "+dungeonBeingParsed.getWidth()+" "+dungeonBeingParsed.getTopHeight());
            displayGrid = ObjectDisplayGrid.getObjectDisplayGrid(dungeonBeingParsed.getGameHeight()+dungeonBeingParsed.getTopHeight()+ dungeonBeingParsed.getBottomHeight(),dungeonBeingParsed.getWidth(),dungeonBeingParsed.getTopHeight());

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace(System.out);
        }
        dungeon = Dungeon.getDungeon();

        for (int i = 0; i < dungeon.creatures.size(); i++) {
            int room = dungeon.creatures.get(i).getRoom()-1;
            int x =  dungeon.rooms.get(room).getPosX()+dungeon.creatures.get(i).getPosX();
            int y = dungeon.rooms.get(room).getPosY()+dungeon.creatures.get(i).getPosY();
            Creature creature = dungeon.creatures.get(i);
            creature.setPosX(x);
            creature.setPosY(y);
        }
        new Rogue(dungeon.getWidth(), dungeon.getGameHeight(), dungeon.getTopHeight() );
    }
}