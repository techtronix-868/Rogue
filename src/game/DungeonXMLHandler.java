package game;

import game.action.Action;
import game.action.ItemAction.BlessArmor;
import game.action.ItemAction.BlessCurseOwner;
import game.action.ItemAction.Hallucinate;
import game.action.ItemAction.ItemAction;
import game.action.creatureAction.*;
import game.action.creatureAction.PlayerAction.DropPack;
import game.action.creatureAction.PlayerAction.EndGame;
import game.displayable.Dungeon;
import game.displayable.creatures.Creature;
import game.displayable.creatures.Monster;
import game.displayable.creatures.Player;
import game.displayable.item.Armor;
import game.displayable.item.Item;
import game.displayable.item.Scroll;
import game.displayable.item.Sword;
import game.displayable.structure.Passage;
import game.displayable.structure.Room;

import org.w3c.dom.ls.LSOutput;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;

public class DungeonXMLHandler extends DefaultHandler {
    //useful for stating caused errors in this class
    private static final int DEBUG = 1;
    private static final String CLASSID = "game.DungeonXMLHandler";

    //the variable that contains information found while parsing the xml file
    private StringBuilder data = null;

    // When the parser parses the file it will add references to
    // Student objects to this array so that it has a list of
    // all specified students.  Had we covered containers at the
    // time I put this file on the web page I would have made this
    // an ArrayList of Students (ArrayList<Student>) and not needed
    // to keep tract of the length and maxStudents.  You should use
    // an ArrayList in your project.
    //private static Dungeon[] dungeons;
    private static ArrayList<Dungeon> dungeons = new ArrayList<Dungeon>(1);

//    private ArrayList<Passage> passages;
//    //private ArrayList<Item> items;
//    private ArrayList<Creature> creatures;
//    //private ArrayList<Container> containers;

    private static Room roomBeingParsed = null;
    private static Creature creatureBeingParsed = null;
    private static Passage passageBeingParsed = null;
    private static Item itemBeingParsed = null;
    private static Action actionBeingParsed = null;
    private static Dungeon dungeonBeingParsed = null;
    //private Displayable displayableBeingParsed = null;




    public ArrayList<Dungeon> getDungeon() {
        return dungeons;
    }

    // A constructor for this class.  It makes an implicit call to the
    // DefaultHandler zero arg constructor, which does the real work
    // DefaultHandler is defined in org.xml.sax.helpers.DefaultHandler;
    // imported above, and we don't need to write it.  We get its
    // functionality by deriving from it!
    public DungeonXMLHandler() {
        System.out.println("run:");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (DEBUG > 1) {
            System.out.println(CLASSID + ".startElement qName: " + qName);
        }
        //System.out.println(qName.equalsIgnoreCase( "hp" ));
        if (qName.equalsIgnoreCase("Dungeon")) {
            String name = attributes.getValue("name");
            int width = Integer.parseInt(attributes.getValue("width"));
            int topHeight = Integer.parseInt(attributes.getValue("topHeight"));
            int gameHeight = Integer.parseInt(attributes.getValue("gameHeight"));
            int bottomHeight = Integer.parseInt(attributes.getValue("bottomHeight"));
            dungeonBeingParsed = Dungeon.getDungeon(name, width, topHeight, gameHeight, bottomHeight);
        } else if (qName.equalsIgnoreCase("Rooms")) {


        } else if (qName.equalsIgnoreCase("Room")) {
            int roomId = Integer.parseInt(attributes.getValue("room"));
            Room room = new Room("Room " + String.valueOf(roomId));
            room.setRoomId(roomId);
            dungeonBeingParsed.addRoom(room); // Adding room in Dungeon
            roomBeingParsed = room;
        } else if (qName.equalsIgnoreCase(("Monster"))) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt((attributes.getValue("room")));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Monster monster = new Monster();
            //dungeonBeingParsed.addCreature(monster);
            monster.setName(name);
            monster.setID(room, serial);
            monster.setRoom( roomBeingParsed.getRoomId() );
            dungeonBeingParsed.addCreature(monster); // make add creature
            creatureBeingParsed = monster;
            //displayableBeingParsed = (Displayable)monster;
            //System.out.println(displayableBeingParsed.toString());



        } else if (qName.equalsIgnoreCase(("Player"))) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt((attributes.getValue("room")));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Player player = new Player();
            player.setID( room,serial );
            player.setRoom( roomBeingParsed.getRoomId() );
            dungeonBeingParsed.addCreature(player);
            creatureBeingParsed = player;

        } else if (qName.equalsIgnoreCase("Passage")) {
            int passageID1 = Integer.parseInt(attributes.getValue("room1"));
            int passageID2 = Integer.parseInt(attributes.getValue("room2"));
            Passage passage = new Passage();
            passage.setID(passageID1, passageID2);
            dungeonBeingParsed.addPassage(passage);
            passageBeingParsed = passage;

        } else if (qName.equalsIgnoreCase(("CreatureAction"))) {
            String creatureAName = attributes.getValue("name");
            CreatureAction creatureAction = null;
            switch (creatureAName) {
                case "Remove":
                    creatureAction = new Remove(creatureAName, creatureBeingParsed);
                    break;
                case "ChangeDisplayedType":
                    creatureAction = new ChangedDisplayedType(creatureAName, creatureBeingParsed);
                    break;
                case "DropPack":
                    creatureAction = new DropPack(creatureAName, creatureBeingParsed);
                    break;
                case "EndGame":
                    creatureAction = new EndGame(creatureAName, creatureBeingParsed);
                    break;
                case "Teleport":
                    creatureAction = new Teleport(creatureAName, creatureBeingParsed);
                    break;
                case "UpdateDisplay":
                    creatureAction = new UpdateDisplay(creatureAName, creatureBeingParsed);
                    break;
                case "YouWin":
                    creatureAction = new YouWin(creatureAName, creatureBeingParsed);
                    break;

                default:
                    System.out.println("Creature Action name is Unknown : " + creatureAName);
                    break;

            }
            actionBeingParsed = creatureAction;
            String type = attributes.getValue("type");
            switch(type) {
                case "death":creatureBeingParsed.setDeathAction((CreatureAction) actionBeingParsed);
                creatureBeingParsed.getDeathAction().setType( "death" );
                break;

                case "hit":creatureBeingParsed.setHitAction((CreatureAction) actionBeingParsed);
                creatureBeingParsed.getHitAction().setType( "hit" );
                break;

                default:System.out.println("Creature Action type is Unknown : " + type);
                break;

            }

        } else if (qName.equalsIgnoreCase("ItemAction")) {
            String ItemActionName = attributes.getValue("name");
            ItemAction itemAction = null;
            switch (ItemActionName) {
                case "BlessCurseOwner":
                    itemAction = new BlessCurseOwner(creatureBeingParsed);
                    break;
                case "Hallucinate":
                    itemAction = new Hallucinate(creatureBeingParsed);
                    break;
                case "BlessArmor":
                    itemAction = new BlessArmor(creatureBeingParsed);
                    break;
                default:
                    System.out.println("The following ItemAction is Unknown: " + ItemActionName);
                    break;
            }
            itemBeingParsed.setItemAction( itemAction );
            actionBeingParsed = itemAction;
        } else if (qName.equalsIgnoreCase(("Sword"))) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt((attributes.getValue("room")));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Sword item = new Sword(name);
            item.setRoom( roomBeingParsed.getRoomId() );
            if (creatureBeingParsed != null){
                item.setOwner( creatureBeingParsed );
            }
            item.setID(room, serial);
            dungeonBeingParsed.addItems(item);
            itemBeingParsed = item;
        } else if (qName.equalsIgnoreCase(("Scroll"))) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt((attributes.getValue("room")));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Scroll item = new Scroll(name);
            item.setRoom( roomBeingParsed.getRoomId() );
            item.setID(room, serial);
            if (creatureBeingParsed != null){
                item.setOwner( creatureBeingParsed );
            }
            dungeonBeingParsed.addItems(item);
            itemBeingParsed = item;
        } else if (qName.equalsIgnoreCase(("Armor"))) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt((attributes.getValue("room")));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Armor item = new Armor(name);
            item.setRoom( roomBeingParsed.getRoomId() );
            item.setID(room, serial);
            if (creatureBeingParsed != null){
                item.setOwner( creatureBeingParsed );
            }
            dungeonBeingParsed.addItems(item);
            itemBeingParsed = item;
        }
        //dungeons.add(dungeonBeingParsed);
        data = new StringBuilder();
    }
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase( "posX" )) {
                if (itemBeingParsed != null) {
                    itemBeingParsed.setPosX( Integer.parseInt( data.toString() ) );
                    //System.out.println( itemBeingParsed.getClass().getSimpleName() + itemBeingParsed.getPosX() );
                } else if (creatureBeingParsed != null) {
                    creatureBeingParsed.setPosX( Integer.parseInt( data.toString() ) );
                    //System.out.println(creatureBeingParsed.getClass().getSimpleName() + itemBeingParsed.getPosX());
                } else if (passageBeingParsed != null) {
                    passageBeingParsed.setPosX( Integer.parseInt( data.toString() ) );
                } else if (roomBeingParsed != null) {
                    roomBeingParsed.setPosX( Integer.parseInt( data.toString() ) );
                    //System.out.println(roomBeingParsed.getPosX());
                }
            } else if (qName.equalsIgnoreCase( "posY" )) {
                if (itemBeingParsed != null) {
                    itemBeingParsed.setPosY( Integer.parseInt( data.toString() ) );
                } else if (creatureBeingParsed != null) {
                    creatureBeingParsed.setPosY( Integer.parseInt( data.toString() ) );
                } else if (passageBeingParsed != null) {
                    passageBeingParsed.setPosY( Integer.parseInt( data.toString() ) );
                } else if (roomBeingParsed != null) {
                    roomBeingParsed.setPosY( Integer.parseInt( data.toString() ) );
                    //System.out.println(roomBeingParsed.getPosY());
                }
            } else if (qName.equalsIgnoreCase( "visible" )) {
                if (Integer.parseInt( data.toString() ) == 1) {
                    if (itemBeingParsed != null) {
                        itemBeingParsed.setVisible();
                    } else if (creatureBeingParsed != null) {
                        creatureBeingParsed.setVisible();
                    } else if (passageBeingParsed != null) {
                        passageBeingParsed.setVisible();
                    } else if (roomBeingParsed != null) {
                        roomBeingParsed.setVisible();
                    }
                }
            } else if (qName.equalsIgnoreCase( "width" )) {
                if (passageBeingParsed != null) {
                    passageBeingParsed.setWidth( Integer.parseInt( data.toString() ) );
                } else if (roomBeingParsed != null) {
                    roomBeingParsed.setWidth( Integer.parseInt( data.toString() ) );
                }
            } else if (qName.equalsIgnoreCase( "height" )) {
                if (passageBeingParsed != null) {
                    passageBeingParsed.setHeight( Integer.parseInt( data.toString() ) );
                } else if (roomBeingParsed != null) {
                    roomBeingParsed.setHeight( Integer.parseInt( data.toString() ) );
                }
            } else if (qName.equalsIgnoreCase( "hp" )) {
                if (creatureBeingParsed != null) {
                    creatureBeingParsed.setHp( Integer.parseInt( data.toString() ) );
                }
            } else if (qName.equalsIgnoreCase( "maxhit" )) {
                if (creatureBeingParsed != null) {
                    creatureBeingParsed.setMaxHit( Integer.parseInt( data.toString() ) );
                }
            } else if (qName.equalsIgnoreCase( "hpMoves" )) {
                if (creatureBeingParsed != null) {
                    creatureBeingParsed.setHpMoves( Integer.parseInt( data.toString() ) );
                }
            } else if (qName.equalsIgnoreCase( "type" )) {
                if (creatureBeingParsed != null) {
                    creatureBeingParsed.setType( (data.toString().charAt( 0 )) );
                }
//                else if (itemBeingParsed != null){
//                    System.out.println(data.toString().charAt( 0 ));
//                    itemBeingParsed.setType( data.toString().charAt( 0 ) );
//                }
            } else if (qName.equalsIgnoreCase( "actionMessage" )) {
                actionBeingParsed.setMsg( data.toString() );
            } else if (qName.equalsIgnoreCase( "actionIntValue" )) {
                actionBeingParsed.setValue( Integer.parseInt( data.toString() ) );
            } else if (qName.equalsIgnoreCase( "actionCharValue" )) {
                actionBeingParsed.setC( data.toString().charAt( 0 ) );
            } else if (qName.equalsIgnoreCase( "actionMessage" )) {
                actionBeingParsed.setMsg( data.toString() );
            } else if (qName.equalsIgnoreCase( "ItemIntValue" )) {
                itemBeingParsed.setItemIntValue( Integer.parseInt( data.toString() ) );
            } else if (qName.equalsIgnoreCase( "Dungeon" )) {
                dungeonBeingParsed = null;
            } else if (qName.equalsIgnoreCase( "Monster" )) {
                creatureBeingParsed = null;
            } else if (qName.equalsIgnoreCase( "Player" )) {
                creatureBeingParsed = null;
            } else if (qName.equalsIgnoreCase( "Room" )) {
                //System.out.println("lllllllllllllllllllllllllllllllllll"+roomBeingParsed.getClass().getSimpleName());
                roomBeingParsed = null;
            } else if (qName.equalsIgnoreCase( "Passage" )) {
                passageBeingParsed = null;
            } else if (qName.equalsIgnoreCase( "Scroll" )) {

                //System.out.println("hahahahahhahahahhaha"+itemBeingParsed.getClass().getSimpleName());
                itemBeingParsed = null;
            } else if (qName.equalsIgnoreCase( "Armor" )) {
                //System.out.println("hahahahahhahahahhaha"+itemBeingParsed.getClass().getSimpleName());
                itemBeingParsed = null;
            } else if (qName.equalsIgnoreCase( "Sword" )) {
                //System.out.println("hahahahahhahahahhaha"+itemBeingParsed.getClass().getSimpleName());
                itemBeingParsed = null;
            }
        }
        @Override
        public void characters ( char ch[], int start, int length) throws SAXException {
            data.append(new String(ch, start, length));
            if (DEBUG > 1) {
                System.out.println(CLASSID + ".characters: " + new String(ch, start, length));
                System.out.flush();
            }
        }

    }

