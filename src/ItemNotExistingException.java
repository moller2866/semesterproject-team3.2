package oceanCleanup.src;

public class ItemNotExistingException extends Exception{


    public ItemNotExistingException(){
        super("There's no such item in the inventory");
    }

}
