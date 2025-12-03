package in.co.rays.proj4.util;

import java.util.ResourceBundle;

/**
 * PropertyReader is a utility class used to fetch values
 * from the system.properties file inside the resource bundle.
 * <p>
 * It supports:
 * - Simple key lookup
 * - Single-parameter message formatting
 * - Multiple-parameter message formatting
 */
public class PropertyReader {

    /** Loads system.properties from the bundle */
    private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

    /**
     * Returns the property value for the given key.
     * If key is not found, the key itself is returned.
     *
     * @param key property key
     * @return property value
     */
    public static String getValue(String key) {
        String val = null;
        try {
            val = rb.getString(key);
        } catch (Exception e) {
            val = key;
        }
        return val;
    }

    /**
     * Returns the property value with a single parameter replaced.
     * Replaces "{0}" in the message with provided parameter.
     *
     * @param key   property key
     * @param param value replacing {0}
     * @return formatted message
     */
    public static String getValue(String key, String param) {
        String msg = getValue(key);
        msg = msg.replace("{0}", param);
        return msg;
    }

    /**
     * Returns the property value with multiple parameters replaced.
     * Replaces "{0}", "{1}", "{2}" ... as per the params provided.
     *
     * @param key    property key
     * @param params array of parameters to replace placeholders
     * @return formatted message
     */
    public static String getValue(String key, String[] params) {
        String msg = getValue(key);
        for (int i = 0; i < params.length; i++) {
            msg = msg.replace("{" + i + "}", params[i]);
        }
        return msg;
    }

    /**
     * Test main method to check PropertyReader behavior.
     */
    public static void main(String[] args) {

        System.out.println("Single key example:");
        System.out.println(PropertyReader.getValue("error.require"));

        System.out.println("\nSingle parameter replacement example:");
        System.out.println(PropertyReader.getValue("error.require", "loginId"));

        System.out.println("\nMultiple parameter replacement example:");
        String[] params = { "Roll No", "Student Name" };
        System.out.println(PropertyReader.getValue("error.multipleFields", params));
    }
}
