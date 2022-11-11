package oceanCleanup.src;

public class Captain extends NPC {

    private String dialouge;

    Captain(String name) {
        super(name, "Captain");
    }


    public void setDialouge(String dialouge) {
        dialougeText.add(dialouge);
    }

    public String getDialouge() {
        return dialouge;
    }

    @Override
    public void startDialouge() {
        System.out.println(getDialouge());
    }


}
