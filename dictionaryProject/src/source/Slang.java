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
    HashMap<String, ArrayList<String>> Dictionary=new HashMap<>();

    public Slang(String filepath) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
        String line = br.readLine();
        while (line != null) {
            System.out.println(line);
            String[] information = line.split("`");
            Dictionary.put(information[0], new ArrayList<String>(List.of(information[1].split(Pattern.quote("| ")))));
            line = br.readLine();
        }
    }


    public static void main(String[] args) throws IOException {
        Slang slang=new Slang("D:\\semester 1 - 3nd year\\java\\PA_01_DictionaryApp (1)\\PA_01_DictionaryApp\\slang.txt");
        System.out.println(slang.Dictionary.get("%)").get(0));
    }
}
