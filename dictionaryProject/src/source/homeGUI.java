package source;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * source
 * Created by ACER
 * Date 12/22/2021 - 8:34 PM
 * Description: ...
 */
public class homeGUI  extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel homePane;
    private JTabbedPane tabbedPane2;
    private JTextField edt_slang;
    private JButton btn_findBySlang;
    private JList list_slang;
    private JTextField edt_def;
    private JButton btn_findByDef;
    private JList list_def;
    private JTabbedPane JTabbedPane;
    private JList list_history_slang;
    private JList list_history_def;
    private JTabbedPane tabbedPane3;
    private JTextField edt_add_slang;
    private JTextArea edt_add_def;
    private JButton btn_add_slang;
    private JTextField edt_find_edit_slang;
    private JButton btn_find_edit_slang;
    private JTextField edt_edit_slang;
    private JTextArea edt_edit_def;
    private JButton btn_edit_slang;
    private JLabel t_edit_slang;
    private JTextField edt_delete;
    private JButton btn_find_delete;
    private JList list_delete;
    private JButton btn_delete;
    private JButton btn_reset;
    private JButton btn_random;
    private JLabel t_random_slang;
    private JList list_def_random;
    private JTabbedPane tabbedPane4;
    private JButton btn_quiz_slang;
    private JButton btn_answer_slang_A;
    private JButton btn_answer_slang_B;
    private JButton btn_answer_slang_C;
    private JButton btn_answer_slang_D;
    private JButton btn_quiz_def;
    private JButton btn_answer_def_A;
    private JButton btn_answer_def_B;
    private JButton btn_answer_def_C;
    private JButton btn_answer_def_D;
    private JLabel t_qt_slang;
    private JLabel t_qt_def;
    private JButton btn_del;
    private JList list_edit;
    private ArrayList<String> answer_Slang;
    private String answer_Def;

    private Slang slang;
    HashMap<String, ArrayList<String>> History;

    DefaultListModel find_slang_model=new DefaultListModel();
    DefaultListModel find_def_model=new DefaultListModel();
    DefaultListModel history_slang_model=new DefaultListModel();
    DefaultListModel history_def_model=new DefaultListModel();
    DefaultListModel delete_model=new DefaultListModel();
    DefaultListModel edit_model=new DefaultListModel();

    public homeGUI(Slang slang){
        super("Home");

        this.slang=slang;

        this.list_def.setModel(find_def_model);
        this.list_history_def.setModel(history_def_model);
        this.list_slang.setModel(find_slang_model);
        this.list_history_slang.setModel(history_slang_model);
        this.list_delete.setModel(delete_model);
        this.list_edit.setModel(edit_model);



        JFrame.setDefaultLookAndFeelDecorated(true);

        this.setContentPane(homePane);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800,600);

        this.setVisible(true);

        btn_findByDef.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String foundDef = edt_def.getText();
                foundDef = foundDef.trim();

                find_def_model.clear();
                for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : slang.Dictionary.entrySet()) {
                    for (ArrayList<String> defs : entry.getValue()) {
                        for (String def : defs) {
                            int pos = def.indexOf(foundDef);
                            int prev = pos - 1;
                            int next = pos + foundDef.length();
                            if (pos >= 0 && (next == def.length() || !Character.isAlphabetic(def.charAt(next)))
                                    && (prev < 0 || !Character.isAlphabetic(def.charAt(prev)))) {
                                find_def_model.add(0, "Slang word: " + entry.getKey());
                                find_def_model.add(1, "Definitions: ");
                                for (int i = 0; i < defs.size(); i++) {
                                    find_def_model.add(i + 2, "      -" + defs.get(i));
                                }
                                find_def_model.add(0, "-----------------------");
                                break;
                            }


                        }
                    }
                }
                if (find_def_model.size() == 0)
                    JOptionPane.showMessageDialog(null, "Not found this Slang in Dictionary",
                            "WARNING ", JOptionPane.WARNING_MESSAGE);
                history_def_model.add(0, edt_def.getText());
                edt_def.setText("");
            }
        });
        btn_findBySlang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String foundSlang=edt_slang.getText();
                foundSlang=foundSlang.trim();

                find_slang_model.clear();
                ArrayList<ArrayList<String>> results=slang.Dictionary.get(foundSlang);
                if (results==null) {
                    JOptionPane.showMessageDialog(null, "Not found this Slang in Dictionary",
                            "WARNING ", JOptionPane.WARNING_MESSAGE);
                }

                else {
                    for (ArrayList<String> result : results) {
                        find_slang_model.add(0, "Slang word: " + foundSlang);
                        find_slang_model.add(1, "Definitions: ");
                        for (int i = 0; i < result.size(); i++) {
                            find_slang_model.add(i + 2, "      -" + result.get(i));
                        }
                        find_slang_model.add(0, "-----------------------");
                    }
                }
                history_slang_model.add(0,foundSlang);
                edt_slang.setText("");
            }
        });

        btn_add_slang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<ArrayList<String>> results=slang.Dictionary.get(edt_add_slang.getText());
                String[] lines=edt_add_def.getText().split("\n");
                if (results==null) {
                    slang.Dictionary.put(edt_add_slang.getText(),new ArrayList<ArrayList<String>>(Collections.singleton(new ArrayList<String>(List.of(lines)))));
                    JOptionPane.showMessageDialog(null, "Add successfully",
                            "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                    edt_add_slang.setText("");
                    edt_add_def.setText("");
                }
                else {
                    int messageType = JOptionPane.WARNING_MESSAGE;
                    String[] options = {"Overwrite", "Duplicate", "Cancel"};
                    int code = JOptionPane.showOptionDialog(null,
                            "This slang existed. Do you want to overwrite this?",
                            "Option ", 0, messageType,
                            null, options, null);
                    if (code==0){
                        slang.Dictionary.get(edt_add_slang.getText()).remove(0);
                        slang.Dictionary.get(edt_add_slang.getText()).add(0,new ArrayList<String>(List.of(lines)));
                        edt_add_slang.setText("");
                        edt_add_def.setText("");
                        JOptionPane.showMessageDialog(null, "Add successfully",
                                "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(code==1){
                        slang.Dictionary.get(edt_add_slang.getText()).add(new ArrayList<String>(List.of(lines)));
                        edt_add_slang.setText("");
                        edt_add_def.setText("");
                        JOptionPane.showMessageDialog(null, "Add successfully",
                                "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                edt_slang.setText("");
            }
        });

        final String[] oldSlangKey = new String[1];
        btn_find_edit_slang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edt_edit_slang.setText("");
                edt_edit_def.setText("");
                ArrayList<ArrayList<String>> results=slang.Dictionary.get(edt_find_edit_slang.getText());
                if (results==null) {
                    JOptionPane.showMessageDialog(null, "Not found your Slang",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                }

                else {

                    edit_model.clear();
                    for (int i=0; i<results.size();i++) {
                        edit_model.add(i,edt_find_edit_slang.getText()+" = "+results.get(i));
                    }
                    edt_find_edit_slang.setText("");
                }
            }
        });
        final int[] index = {-1};
        final String[] oldSlang = {""};
        list_edit.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !list_edit.isSelectionEmpty()) {
                    String editslang = list_edit.getSelectedValue().toString().split(" = ")[0];
                    edt_edit_slang.setText(editslang);
                    edt_edit_def.setText("");

                    ArrayList<String> editdefs = slang.Dictionary.get(editslang).get(list_edit.getSelectedIndex());
                    for (String def : editdefs) {
                        edt_edit_def.append(def + "\n");
                    }
                    index[0] =list_edit.getSelectedIndex();
                    oldSlang[0] =edt_edit_slang.getText();
                    list_edit.clearSelection();
                }
            }
        });

        btn_edit_slang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (oldSlang[0].equals("") || index[0]==-1){
                    JOptionPane.showMessageDialog(null, "Operation is invalid",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                }
                else {

                    String[] parts = edt_edit_def.getText().split("\n\n");
                    ArrayList<ArrayList<String>> defs = new ArrayList<>();
                    for (String part : parts) {
                        defs.add(new ArrayList<String>(List.of(part.split("\n"))));
                    }
                    System.out.println(oldSlangKey[0]);
                    System.out.println(index[0]);

                    slang.Dictionary.get(oldSlang[0]).remove(index[0]);

                    if (!slang.Dictionary.containsKey(edt_edit_slang.getText())) {
                        slang.Dictionary.put(edt_edit_slang.getText(), defs);

                        JOptionPane.showMessageDialog(null, "Update successfully",
                                "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        for (ArrayList<String> def : defs)
                            slang.Dictionary.get(edt_edit_slang.getText()).add(def);
                        JOptionPane.showMessageDialog(null, "Update successfully",
                                "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                    }
                    edit_model.remove(index[0]);
                    edit_model.add(index[0],edt_edit_slang.getText()+" = "+defs.get(0));
                    list_edit.setModel(edit_model);

                    oldSlangKey[0] = "";
                    index[0] = -1;
                }

                edt_edit_slang.setText("");
                edt_edit_def.setText("");

            }
        });




        btn_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int messageType = JOptionPane.WARNING_MESSAGE;
                String[] options = {"Yes", "No"};

                int code = JOptionPane.showOptionDialog(null,
                        "Do you want to reset Dictionary. Your changes will be deleted?",
                        "Option ", 0, messageType,
                        null, options, null);
                if (code==0){
                    slang.Dictionary=new HashMap<>();
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(slang.filepath)));
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    String line = null;
                    try {
                        line = br.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    while (line != null) {
                        String[] information = line.split("`");
                        ArrayList<String> arrayList=new ArrayList<String>(List.of(information[1].split(Pattern.quote("| "))));
                        if (!slang.Dictionary.containsKey(information[0]))
                        {
                            slang.Dictionary.put(information[0], new ArrayList<ArrayList<String>>(Collections.singleton(arrayList)));
                        }
                        else
                            slang.Dictionary.get(information[0]).add(arrayList);
                        try {
                            line = br.readLine();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        btn_random.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> keysAsArray = new ArrayList<String>(slang.Dictionary.keySet());
                Random r = new Random();
                String random_key=keysAsArray.get(r.nextInt(keysAsArray.size()));
                ArrayList<ArrayList<String>> random_defs=slang.Dictionary.get(random_key);

                t_random_slang.setText("    -"+random_key);

                DefaultListModel model=new DefaultListModel();

                for (ArrayList<String> random_def: random_defs){
                    for (String def:random_def)
                        model.add(0,"   -"+def);
                    model.add(0, "Definition: ");
                }
                list_def_random.setModel(model);
            }
        });

        btn_quiz_slang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> keysAsArray = new ArrayList<String>(slang.Dictionary.keySet());
                Random r = new Random();
                String random_key =keysAsArray.get(r.nextInt(keysAsArray.size()));
                ArrayList<ArrayList<String>> random_defs=slang.Dictionary.get(random_key);

                answer_Slang=new ArrayList<>();

                for (ArrayList<String> random_def : random_defs) answer_Slang.add(String.valueOf(random_def));

                ArrayList<ArrayList<ArrayList<String>>> valuesAsArray = new ArrayList<>(slang.Dictionary.values());

                ArrayList<ArrayList<String>> random_answer=valuesAsArray.get(r.nextInt(valuesAsArray.size()));
                ArrayList<String> answer_1=new ArrayList<>();


                for (ArrayList<String> strings : random_answer) answer_1.add(String.valueOf(strings));

                random_answer=valuesAsArray.get(r.nextInt(valuesAsArray.size()));
                ArrayList<String> answer_2=new ArrayList<>();
                for (ArrayList<String> strings : random_answer) answer_2.add(String.valueOf(strings));

                random_answer=valuesAsArray.get(r.nextInt(valuesAsArray.size()));
                ArrayList<String> answer_3=new ArrayList<>();
                for (ArrayList<String> strings : random_answer) answer_3.add(String.valueOf(strings));

                List<String> answers=new ArrayList<String>(List.of(new String[]{
                        String.valueOf(answer_1).replace("[","").replace("]",""),
                        String.valueOf(answer_2).replace("[","").replace("]",""),
                        String.valueOf(answer_3).replace("[","").replace("]",""),
                        String.valueOf(answer_Slang).replace("[","").replace("]","")}));
                t_qt_slang.setText("Choose definition for "+random_key);
                Collections.shuffle(answers);

                btn_answer_slang_A.setText(answers.get(0));
                btn_answer_slang_B.setText(answers.get(1));
                btn_answer_slang_C.setText(answers.get(2));
                btn_answer_slang_D.setText(answers.get(3));
            }
        });

        btn_answer_slang_A.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result=btn_answer_slang_A.getText();
                if (result.equals(" "))
                    JOptionPane.showMessageDialog(null, "You should generate quiz to start quiz",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                else
                {
                    if (result.equals(String.valueOf(answer_Slang).replace("[","").replace("]",""))){
                        JOptionPane.showMessageDialog(null, "Your answer is right !!!",
                                "Congratulation", JOptionPane.INFORMATION_MESSAGE);
                        btn_quiz_slang.doClick();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Your answer is wrong. Choose another one",
                                "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btn_answer_slang_B.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result=btn_answer_slang_B.getText();
                if (result.equals(" "))
                    JOptionPane.showMessageDialog(null, "You should generate quiz to start quiz",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                else
                {
                    if (result.equals(String.valueOf(answer_Slang).replace("[","").replace("]",""))){
                        JOptionPane.showMessageDialog(null, "Your answer is right !!!",
                                "Congratulation", JOptionPane.INFORMATION_MESSAGE);
                        btn_quiz_slang.doClick();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Your answer is wrong. Choose another one",
                                "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btn_answer_slang_C.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result=btn_answer_slang_C.getText();
                if (result.equals(" "))
                    JOptionPane.showMessageDialog(null, "You should generate quiz to start quiz",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                else
                {
                    if (result.equals(String.valueOf(answer_Slang).replace("[","").replace("]",""))){
                        JOptionPane.showMessageDialog(null, "Your answer is right !!!",
                                "Congratulation", JOptionPane.INFORMATION_MESSAGE);
                        btn_quiz_slang.doClick();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Your answer is wrong. Choose another one",
                                "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btn_answer_slang_D.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result=btn_answer_slang_D.getText();
                if (result.equals(" "))
                    JOptionPane.showMessageDialog(null, "You should generate quiz to start quiz",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                else
                {
                    if (result.equals(String.valueOf(answer_Slang).replace("[","").replace("]",""))){
                        JOptionPane.showMessageDialog(null, "Your answer is right !!!",
                                "Congratulation", JOptionPane.INFORMATION_MESSAGE);
                        btn_quiz_slang.doClick();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Your answer is wrong. Choose another one",
                                "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btn_quiz_def.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> keysAsArray = new ArrayList<String>(slang.Dictionary.keySet());
                Random r = new Random();
                answer_Def =keysAsArray.get(r.nextInt(keysAsArray.size()));

                ArrayList<ArrayList<String>> random_defs=slang.Dictionary.get(answer_Def);
                ArrayList<String> question=new ArrayList<>();

                for (ArrayList<String> random_def : random_defs) question.add(String.valueOf(random_def));


                String answer_1=keysAsArray.get(r.nextInt(keysAsArray.size()));
                String answer_2=keysAsArray.get(r.nextInt(keysAsArray.size()));
                String answer_3=keysAsArray.get(r.nextInt(keysAsArray.size()));

                List<String> answers=new ArrayList<String>(List.of(new String[]{answer_1, answer_2, answer_3, answer_Def}));
                t_qt_def.setText("Choose slang word for definition: "+String.valueOf(question).replace("]","")
                        .replace("[",""));
                Collections.shuffle(answers);

                btn_answer_def_A.setText(answers.get(0));
                btn_answer_def_B.setText(answers.get(1));
                btn_answer_def_C.setText(answers.get(2));
                btn_answer_def_D.setText(answers.get(3));
            }
        });

        btn_answer_def_A.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result=btn_answer_def_A.getText();
                if (result.equals(" "))
                    JOptionPane.showMessageDialog(null, "You should generate quiz to start quiz",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                else
                {
                    if (result.equals(answer_Def)){
                        JOptionPane.showMessageDialog(null, "Your answer is right !!!",
                                "Congratulation", JOptionPane.INFORMATION_MESSAGE);
                        btn_quiz_def.doClick();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Your answer is wrong. Choose another one",
                                "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btn_answer_def_B.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result=btn_answer_def_B.getText();
                if (result.equals(" "))
                    JOptionPane.showMessageDialog(null, "You should generate quiz to start quiz",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                else
                {
                    if (result.equals(answer_Def)){
                        JOptionPane.showMessageDialog(null, "Your answer is right !!!",
                                "Congratulation", JOptionPane.INFORMATION_MESSAGE);
                        btn_quiz_def.doClick();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Your answer is wrong. Choose another one",
                                "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btn_answer_def_C.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result=btn_answer_def_C.getText();
                if (result.equals(" "))
                    JOptionPane.showMessageDialog(null, "You should generate quiz to start quiz",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                else
                {
                    if (result.equals(answer_Def)){
                        JOptionPane.showMessageDialog(null, "Your answer is right !!!",
                                "Congratulation", JOptionPane.INFORMATION_MESSAGE);
                        btn_quiz_def.doClick();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Your answer is wrong. Choose another one",
                                "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btn_answer_def_D.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result=btn_answer_def_D.getText();
                if (result.equals(" "))
                    JOptionPane.showMessageDialog(null, "You should generate quiz to start quiz",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                else
                {
                    if (result.equals(answer_Def)){
                        JOptionPane.showMessageDialog(null, "Your answer is right !!!",
                                "Congratulation", JOptionPane.INFORMATION_MESSAGE);
                        btn_quiz_def.doClick();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Your answer is wrong. Choose another one",
                                "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                int messageType = JOptionPane.INFORMATION_MESSAGE;
                String[] options = {"Yes", "No"};
                int code = JOptionPane.showOptionDialog(null,
                        "Do you want to save all changes to your file ?",
                        "Option ", 0, messageType,
                        null, options, null);
                if (code==0){
                    try {
                        BufferedWriter bw=new BufferedWriter(new FileWriter(slang.filepath));
                        for (Map.Entry<String, ArrayList<ArrayList<String>>> entries: slang.Dictionary.entrySet()) {
                            for (ArrayList<String> entry : entries.getValue()) {
                                String line = entries.getKey() + "`" + entry.get(0);
                                for (int i = 1; i < entry.size(); i++) {
                                    line = line + "| " + entry.get(i);
                                }

                                bw.write(line);
                                bw.newLine();
                                bw.flush();
                            }
                        }
                        bw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        btn_find_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String foundSlang=edt_delete.getText();
                foundSlang=foundSlang.trim();

                delete_model.clear();
                ArrayList<ArrayList<String>> results=slang.Dictionary.get(foundSlang);
                if (results==null) {
                    JOptionPane.showMessageDialog(null, edt_delete.getText()+" is not found in Dictionary",
                           "WARNING", JOptionPane.WARNING_MESSAGE);
                }

                else {
                    for (int i=0; i<results.size();i++) {
                        delete_model.add(i, foundSlang+" = "+results.get(i));
                    }
                }
                edt_delete.setText("");
            }
        });

        btn_del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=-1;
                i=list_delete.getSelectedIndex();

                if (i==-1){
                    JOptionPane.showMessageDialog(null, "Please, choose a Slag word to delete.",
                           "WARNING", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    int messageType = JOptionPane.WARNING_MESSAGE;
                    String[] options = {"Confirm", "Cancel"};
                    int code = JOptionPane.showOptionDialog(null,
                            "Are you sure for deleting this slang word ?",
                            "Option ", 0, messageType,
                            null, options, null);

                    if (code==0)
                    {
                        String deleteSlang=list_delete.getSelectedValue().toString().split(" = ")[0];
                        delete_model.remove(i);
                        list_delete.setModel(delete_model);

                        slang.Dictionary.get(deleteSlang).remove(i);
                        if (slang.Dictionary.get(deleteSlang).size()==0)
                            slang.Dictionary.remove(deleteSlang);
                        JOptionPane.showMessageDialog(null, "Delete Successfully",
                                "Information ", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }
        });
    btn_quiz_slang.doClick();
    btn_quiz_def.doClick();
    btn_random.doClick();

    }

}


