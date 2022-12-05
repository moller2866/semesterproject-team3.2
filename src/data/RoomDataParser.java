package oceanCleanup.src.data;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kasper Møller
 **/
public class RoomDataParser {
    String filename;
    Map<String, ?> jsonContent;

    /**
     * @param filename name of the json file
     */
    public RoomDataParser(String filename) {
        this.filename = filename;
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, ?> map = mapper.readValue(Paths.get(filename).toFile(), Map.class);
            this.jsonContent = map;
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean verifyJsonKeys() {
        ArrayList<String> listOfKeys = new ArrayList<>();

        for (Map.Entry<String, ?> entry : this.getJsonContent().entrySet()) {
            listOfKeys.add(entry.getKey());
        }

        if (!listOfKeys.contains("description")) {
            return false;
        }
        if (!listOfKeys.contains("NPCs")) {
            return false;
        }
        if (!listOfKeys.contains("items")) {
            return false;
        }
        return true;
    }

    public Map<String, ?> getJsonContent() {
        return this.jsonContent;
    }

    public ArrayList<HashMap<String, ?>> getNPCs() {
        return (ArrayList<HashMap<String, ?>>) getJsonContent().get("NPCs");
    }

    public ArrayList<HashMap<String, ?>> getItems() {
        return (ArrayList<HashMap<String, ?>>) getJsonContent().get("items");
    }

    public ArrayList<ArrayList<Double>> getBorders() {
        return (ArrayList<ArrayList<Double>>) getJsonContent().get("borders");
    }

    public String getDescription() {
        return (String) getJsonContent().get("description");
    }

    public static void main(String[] args) {
        RoomDataParser parser = new RoomDataParser( "src/data/roomdescriptions/dock.json");

        System.out.println(parser.getDescription());
        System.out.println();
        System.out.println(parser.getNPCs());
        System.out.println();
        System.out.println(parser.getItems());
        System.out.println(parser.getBorders());

    }
}
