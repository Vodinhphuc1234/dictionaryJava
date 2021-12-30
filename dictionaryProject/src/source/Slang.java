package source;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * source
 * Created by ACER
 * Date 12/22/2021 - 4:13 PM
 * Description: ...
 */
public class Slang {
    String filepath;
    HashMap<String, ArrayList<String>> Dictionary=new HashMap<>();

    public Slang(String filepath) throws IOException {
        this.filepath=filepath;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
        String line = br.readLine();
        while (line != null) {
            String[] information = line.split("`");
            Dictionary.put(information[0], new ArrayList<String>(List.of(information[1].split(Pattern.quote("| ")))));
            line = br.readLine();
        }
    }


}
