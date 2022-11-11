package oceanCleanup.src;

public class ItemNonExistingException extends Exception{


    public ItemNonExistingException(){
        super("There's no such item in the inventory");
    }

}
