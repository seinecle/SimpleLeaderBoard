/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author LEVALLOIS
 */
public class MappingCodeAwards {

    static Map<String, String> mapCategoryToFontIcon;
    static URL u;

    public static void initializeMapping() throws MalformedURLException, IOException {
        u = new URL("https://dl.dropboxusercontent.com/u/28091845/codapps_icons.txt");
        InputStream in = u.openStream();
        String myString = IOUtils.toString(in, "UTF-8");
        String[] lines = myString.split("\n");
        mapCategoryToFontIcon = new HashMap();

        for (String line : lines) {
            String[] fields = line.split(",");
            if (fields.length > 1) {
                try {
                    mapCategoryToFontIcon.put(fields[0].toLowerCase().trim(), fields[1].toLowerCase().trim());
                } catch (Exception e) {
                    System.out.println("Error when importing icons from dropbox");
                }
            }
        }
    }

    public static Map<String, String> getMapCategoryToFontIcon() {
        return mapCategoryToFontIcon;
    }
}
