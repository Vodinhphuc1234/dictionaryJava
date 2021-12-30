package source;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {


    public static void main(String[] args) throws IOException {
        String filepath="slang.txt";
        Slang slang=new Slang(filepath);
        HashMap<String, ArrayList<String>> History=new HashMap<>();
        new homeGUI(slang);
    }
}
