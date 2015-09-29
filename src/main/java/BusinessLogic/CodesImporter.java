/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic;

import Model.CodeAward;
import Private.Code;
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
        u = new URL(Code.codeUrl);
        InputStream in = u.openStream();
        String myString = IOUtils.toString(in, "UTF-8");
        String[] lines = myString.split("\n");

        Set<CodeAward> codeAwards = new HashSet();
        for (String line : lines) {
            String[] fields = line.split(",");
            if (fields.length > 2) {
                try {
                    String code = fields[0].toLowerCase().trim();
                    code = !code.startsWith("#") ? "#" + code : code;
                    codeAwards.add(new CodeAward(code, fields[1].trim(), Integer.parseInt(fields[2].trim())));
                } catch (Exception e) {
                    System.out.println("Error when importing codes from dropbox");
                }
            }
        }
        return codeAwards;

    }
}
