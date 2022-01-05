package source;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {


    public static void main(String[] args) throws IOException {
        String filepath="slang.txt";
        JOptionPane.showMessageDialog(null, "Please, choose a file slang.txt for dictionary",
                "WARNING", JOptionPane.WARNING_MESSAGE);
        JFileChooser jFileChooser = new JFileChooser();
        int a = jFileChooser.showOpenDialog(null);
        if (a == jFileChooser.APPROVE_OPTION) {
            File file = new File(jFileChooser.getSelectedFile().getAbsolutePath());
            filepath=String.valueOf(file);
            Slang slang=new Slang(filepath);
            new homeGUI(slang);
        }
        else System.exit(0);

    }
}
