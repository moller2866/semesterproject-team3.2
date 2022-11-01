package oceanCleanup.src;

/*
*  Abstract class as a template for tools
*  Every tool must have a getName and
*  getToolInfo method
*/

public abstract class Tools {
    private String name;

    Tools (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getToolInfo();

}
