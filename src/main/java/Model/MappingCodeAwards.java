/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LEVALLOIS
 */
public class MappingCodeAwards {

    static Map<String, String> mapCategoryToFontIcon;

    public static void initializeMapping() {
        mapCategoryToFontIcon = new HashMap();
        mapCategoryToFontIcon.put("bus", "fa-bus");
        mapCategoryToFontIcon.put("twitter", "fa-twitter");
        mapCategoryToFontIcon.put("academic", "fa-graduation-cap");
    }

    public static Map<String, String> getMapCategoryToFontIcon() {
        return mapCategoryToFontIcon;
    }
}
