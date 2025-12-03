package in.co.rays.proj4.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import in.co.rays.proj4.bean.DropdownListBean;
import in.co.rays.proj4.model.RoleModel;

/**
 * Utility class for generating HTML dropdown elements.
 * 
 * Provides overloaded methods to create <select> tags from either
 * a HashMap or a List of DropdownListBean objects.
 */
public class HTMLUtility {

    /**
     * Builds an HTML <select> dropdown using key-value pairs from a HashMap.
     *
     * @param name         the name attribute of the select element
     * @param selectedVal  the key that should appear selected
     * @param map          the map containing options
     * @return             generated HTML select tag as String
     */
    public static String getList(String name, String selectedVal, HashMap<String, String> map) {

        StringBuffer sb = new StringBuffer(
                "<select style=\"width: 169px;text-align-last: center;\"; class='form-control' name='" + name + "'>");

        sb.append("\n<option selected value=''>-------------Select-------------</option>");

        Set<String> keys = map.keySet();
        String val = null;

        for (String key : keys) {
            val = map.get(key);
            if (key.trim().equals(selectedVal)) {
                sb.append("\n<option selected value='" + key + "'>" + val + "</option>");
            } else {
                sb.append("\n<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("\n</select>");
        return sb.toString();
    }

    /**
     * Builds an HTML <select> dropdown from a List of DropdownListBean objects.
     *
     * @param name         the name attribute of the select element
     * @param selectedVal  the key which should be highlighted as selected
     * @param list         list of objects implementing DropdownListBean
     * @return             generated HTML select tag as String
     */
    public static String getList(String name, String selectedVal, List list) {

        List<DropdownListBean> dd = (List<DropdownListBean>) list;

        StringBuffer sb = new StringBuffer("<select style=\"width: 169px;text-align-last: center;\"; "
                + "class='form-control' name='" + name + "'>");

        sb.append("\n<option selected value=''>-------------Select-------------</option>");

        String key = null;
        String val = null;

        for (DropdownListBean obj : dd) {
            key = obj.getKey();
            val = obj.getValue();

            if (key.trim().equals(selectedVal)) {
                sb.append("\n<option selected value='" + key + "'>" + val + "</option>");
            } else {
                sb.append("\n<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("\n</select>");
        return sb.toString();
    }

    /**
     * Test method for generating a dropdown using a HashMap.
     */
    public static void testGetListByMap() {

        HashMap<String, String> map = new HashMap<>();
        map.put("male", "male");
        map.put("female", "female");

        String selectedValue = "male";
        String htmlSelectFromMap = HTMLUtility.getList("gender", selectedValue, map);

        System.out.println(htmlSelectFromMap);
    }

    /**
     * Test method for generating a dropdown using a List from RoleModel.
     *
     * @throws Exception if model.list() fails
     */
    public static void testGetListByList() throws Exception {

        RoleModel model = new RoleModel();
        List list = model.list();

        String selectedValue = "1";

        String htmlSelectFromList = HTMLUtility.getList("role", selectedValue, list);

        System.out.println(htmlSelectFromList);
    }

    /**
     * Main method to test dropdown creation.
     */
    public static void main(String[] args) throws Exception {

        // testGetListByMap();
        testGetListByList();
    }
}
