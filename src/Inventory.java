package oceanCleanup.src;

import java.util.ArrayList;

public class Inventory {


    Item[] inventory;
    ArrayList<String> Items = new ArrayList<String>();


    public ArrayList<String> getInventory(){
        return Items;

    }



    public void addItem (Item item) {
            Items.add("BUCKET");

        }




    public Item remove(Item Item) {
        return Item;



        }

        public String toString(){
        return "bom";
        }






// test
    public static void main (String [] args){
        Inventory K = new Inventory();
        System.out.print(K.getInventory());

    }

}
