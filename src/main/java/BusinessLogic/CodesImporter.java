/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic;

import Model.CodeAward;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author LEVALLOIS
 */
public class CodesImporter {

    static URL u;

    public static Set<CodeAward> importCodes() throws MalformedURLException, IOException {
        u = new URL("https://dl.dropboxusercontent.com/u/28091845/codapps_codes.txt");
        InputStream in = u.openStream();
        String myString = IOUtils.toString(in, "UTF-8");
        String[] lines = myString.split("\n");

        Set<CodeAward> codeAwards = new HashSet();
        for (String line : lines) {
            String[] fields = line.split(",");
            if (fields.length > 2) {
                codeAwards.add(new CodeAward(fields[0].toLowerCase().trim(), fields[1].trim(), Integer.parseInt(fields[2].trim())));
            }
        }
        return codeAwards;

    }
}
