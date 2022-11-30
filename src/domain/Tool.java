package oceanCleanup.src.domain;

/*
*  Abstract class as a template for tools
*  Every tool must have a getName and
*  getInfo method
*/

public class Tool extends Item {

    Tool (String name) {
        super(name,0,0);
    }

    Tool (String name, double x, double y) {
        super(name, x, y);
    }

    public String getInfo() {
        return null;
    }


}
