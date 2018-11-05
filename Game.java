/**
 *  Welcome to Acadia, a land of journey and peril.  Acadia is the 
 *  convergence of multiple periods of time, leading you to find many
 *  percular landmarks from famous time periods.  Who knows what you will find
 *  on your journey through Acaida.
 * 
 * @author  Austin Raymond
 * @version 2018.11.03
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Player player = new Player();
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.  Old Rooms deleted
     * and 12 new ones were added
     */
    private void createRooms()
    {
        Room pyramid, forest, lake, temple, parthanon, cave, wasteland, 
        mountain, shibuya, airport, town, desert, temple_exit;
      
        // create the rooms
        pyramid = new Room("An ancient Egyptian pyramid from 49 BC");
        forest = new Room("A dark forest with minimal sunlight where creatures lurk");
        lake = new Room("A small body of water where something mysterious lurks below the surface");
        temple = new Room("An ancient temple where dark rituals were performed");
        parthanon = new Room("An ancient Greek place of worship");
        cave = new Room("a dark cave, the cave colapses behind you and you can only move forward");
        wasteland = new Room("a barren wasteland with nothing but bones");
        mountain = new Room("a lonely mountain");
        shibuya = new Room("The bustling center of modern day Tokyo");
        airport = new Room("An airport for people to come and go from Acadia");
        town = new Room("The main hub of Acaida");
        desert = new Room("A very dry place with dangerous creatures");
        temple_exit = new Room("you emerge at the temple near the town");
        
        // initialise room exits
        airport.setExit("north", town);
        airport.setExit("northeast", temple);
        airport.setExit("northwest", lake);
        
        town.setExit("south", airport);
        town.setExit("east", temple);
        town.setExit("west", lake);
        town.setExit("north", forest);
        
        lake.setExit("east", town);
        lake.setExit("southeast", airport);
        lake.setExit("northeast", forest);
        
        temple.setExit("southwest", airport);
        temple.setExit("west", town);
        
        forest.setExit("north", wasteland);
        forest.setExit("northeast", mountain);
        forest.setExit("southwest", lake);
        forest.setExit("south", town);
        
        wasteland.setExit("north", shibuya);
        wasteland.setExit("east", mountain);
        wasteland.setExit("west", desert);
        wasteland.setExit("south", forest);
        
        mountain.setExit("east", cave);
        mountain.setExit("west", wasteland);
        mountain.setExit("southwest", forest);
        
        cave.setExit("forward", temple_exit);
        
        temple_exit.setExit("southwest", airport);
        temple_exit.setExit("west", town);
        
        desert.setExit("east", wasteland);
        desert.setExit("west", pyramid);
        
        pyramid.setExit("east", desert);
        
        shibuya.setExit("south", wasteland);
        shibuya.setExit("north", parthanon);
        
        parthanon.setExit("south", shibuya);
        
        currentRoom = airport;  // start game at the airport
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Acaida");
        System.out.println("Acaida is a land filled with peril and adventure");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case EAT:
                System.out.println("You ate a piece of food");
                break;
                
            case LOOK:
                if (player.playerLook()){
                    System.out.println("There is an item nearby");
                }
                else{
                    System.out.println("There is not item nearby");
                }
                break;
                
            case PICKUP:
                player.playerPickup();
                break;
                
            case DROP:
                player.playerDrop();
                break;
                
            case CHECK:
                System.out.println("You have " + player.checkInventory() + " in your inventory");
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You have flown to the mysterious land of Acadia");
        System.out.println("looking for adventure");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
