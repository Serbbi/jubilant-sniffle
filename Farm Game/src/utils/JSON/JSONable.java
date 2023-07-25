package utils.JSON;

/**
 * Interface for all objects that can have their data written out to json.
 */
public interface JSONable {

    /**
     * Converts all of the objects relevant data into a JSONObject, to be written to
     * a json file.
     * 
     * @return JSONObject to use to write data to a json file.
     */
    JSONObject toJson();
}
