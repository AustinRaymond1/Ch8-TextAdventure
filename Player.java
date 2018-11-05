import java.util.Random;
/**
 * This class will hold the player's inventory and all the actions assocated with it
 *
 * @author Austin Raymond
 * @version 2018.11.04
 */
public class Player
{
    // setting the vairable for the one slot inventory of the player
    private String inventory;
    Random r = new Random();
    private static Boolean item_bool;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        // default inventory of the player is empty
        inventory = "";
    }
    /**
     * This is how the player checks their inventory
     */
    public String checkInventory(){
        //prints what is currently in the players inventory
        return inventory;
    }
    /**
     * This is the method for looking around a room
     */
    public boolean playerLook(){
        //returns a boolean of if there is an item nearby or not
        int item = r.nextInt(5);
        if (item%5 == 0 || item%3==0){
            item_bool = true;
        }
        else{
            item_bool = false;
        }
        return item_bool;
    }
    /**
     * Method for picking up items
     */
    public void playerPickup(){
        if (item_bool){
            System.out.println("You picked up the item");
            inventory = "item";
        }
        else{
            System.out.println("There is no item to pick up");
        }
    }
    /**
     * Method for dropping items
     */
    public void playerDrop(){
        if(inventory == ""){
            System.out.println("You do not have any items to drop");
        }
        else{
            System.out.println("You dropped the item");
        }
    }
}
