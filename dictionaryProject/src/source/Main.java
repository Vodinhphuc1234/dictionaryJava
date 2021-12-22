package source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {


    public static void main(String[] args) throws IOException {
        Slang slang=new Slang("D:\\semester 1 - 3nd year\\java\\PA_01_DictionaryApp (1)\\PA_01_DictionaryApp\\slang.txt");
        HashMap<String, ArrayList<String>> History=new HashMap<>();
        new homeGUI(slang);
    }
}
