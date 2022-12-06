package oceanCleanup.src.data;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *This class is responsible for parsing the JSON files and creating the objects
 *to be used in Room.java
 * @author Kasper
 */
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

    public HashMap<String, ArrayList<Double>> getExits() {
        return (HashMap<String, ArrayList<Double>>) getJsonContent().get("exits");
    }

    public String getBackground() {
        return getClass().getResource("roombackgrounds/" + getJsonContent().get("background")).toExternalForm();
    }

    public String getDescription() {
        return (String) getJsonContent().get("description");
    }

}
