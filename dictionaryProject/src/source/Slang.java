package source;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * source
 * Created by ACER
 * Date 12/22/2021 - 4:13 PM
 * Description: ...
 */
public class Slang {
    String filepath;
    HashMap<String, ArrayList<ArrayList<String>>> Dictionary=new HashMap<>();

    public Slang(String filepath) throws IOException {
        this.filepath=filepath;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
        String line = br.readLine();
        while (line != null) {
            String[] information = line.split("`");
            ArrayList<String> arrayList=new ArrayList<String>(List.of(information[1].split(Pattern.quote("| "))));
            if (!Dictionary.containsKey(information[0]))
            {
                Dictionary.put(information[0], new ArrayList<ArrayList<String>>(Collections.singleton(arrayList)));
            }
            else
                Dictionary.get(information[0]).add(arrayList);
            line = br.readLine();
        }
    }


}
