package oceanCleanup.src;

/*
*  Abstract class as a template for tools
*  Every tool must have a getName and
*  getInfo method
*/

public abstract class Tools extends Item {

    Tools (String name) {
        super(name);
    }

    public String getInfo() {
        return null;
    }


}
