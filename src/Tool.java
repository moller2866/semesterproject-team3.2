package oceanCleanup.src;

/*
*  Abstract class as a template for tools
*  Every tool must have a getName and
*  getInfo method
*/

public class Tool extends Item {

    Tool (String name) {
        super(name);
    }

    public String getInfo() {
        return null;
    }


}
