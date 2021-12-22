package source;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

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
    private JTextField edt_find_delete;
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
    private String question_Slang;
    private String answer_Slang;
    private String question_Def;
    private String answer_Def;

    private Slang slang;
    HashMap<String, ArrayList<String>> History;

    DefaultListModel find_slang_model=new DefaultListModel();
    DefaultListModel find_def_model=new DefaultListModel();
    DefaultListModel history_slang_model=new DefaultListModel();
    DefaultListModel history_def_model=new DefaultListModel();
    DefaultListModel delete_model=new DefaultListModel();

    public homeGUI(Slang slang){
        super("Home");

        this.slang=slang;

        this.list_def.setModel(find_def_model);
        this.list_history_def.setModel(history_def_model);
        this.list_slang.setModel(find_slang_model);
        this.list_history_slang.setModel(history_slang_model);
        this.list_delete.setModel(delete_model);

        this.setContentPane(homePane);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setVisible(true);

        btn_findByDef.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                find_def_model.clear();
                for(Map.Entry<String,ArrayList<String>> entry: slang.Dictionary.entrySet())
                {
                    for (String def: entry.getValue())
                    {
                        if (def.indexOf(edt_def.getText())>1){
                            find_def_model.add(0,entry.getKey()+" = "+entry.getValue());
                            history_def_model.add(0,entry.getKey()+" = "+entry.getValue());
                            break;
                        }
                    }
                }
                edt_def.setText("");
            }
        });
        btn_findBySlang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                find_slang_model.clear();
                ArrayList<String> result=slang.Dictionary.get(edt_slang.getText());
                if (result==null) {
                    find_slang_model.add(0, "Not found definition for " + edt_slang.getText());
                    history_slang_model.add(0, "Not found definition for " + edt_slang.getText());
                }

                else {
                    find_slang_model.add(0,""+edt_slang.getText()+" = "+result);
                    history_slang_model.add(0,""+edt_slang.getText()+" = "+result);
                }
                edt_slang.setText("");
            }
        });

        btn_add_slang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> result=slang.Dictionary.get(edt_add_slang.getText());
                String[] lines=edt_add_def.getText().split("\n");
                if (result==null) {
                    slang.Dictionary.put(edt_add_slang.getText(),new ArrayList<String>(List.of(lines)));
                }

                else {
                    int messageType = JOptionPane.WARNING_MESSAGE;
                    String[] options = {"Overwrite", "Cancel"};
                    int code = JOptionPane.showOptionDialog(null,
                            "This slang existed. Do you want to overwrite this?",
                            "Option ", 0, messageType,
                            null, options, null);
                    if (code==0){
                        slang.Dictionary.put(edt_add_slang.getText(),new ArrayList<String>(List.of(lines)));
                    }
                }
                edt_slang.setText("");
            }
        });

        btn_find_edit_slang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> result=slang.Dictionary.get(edt_find_edit_slang.getText());
                if (result==null) {
                    JOptionPane.showMessageDialog(null, "Not found your Slang",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                }

                else {
                    t_edit_slang.setText(edt_find_edit_slang.getText());
                    edt_edit_def.setText("");
                    for (String r: result){
                        edt_edit_def.append(r+"\n");
                    }
                    edt_find_edit_slang.setText("");
                }
            }
        });

        btn_edit_slang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] lines=edt_edit_def.getText().split("\n");
                slang.Dictionary.put(t_edit_slang.getText(),new ArrayList<String>(List.of(lines)));

                JOptionPane.showMessageDialog(null, "Update successfully",
                        "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                }
        });

        btn_find_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete_model.clear();
                ArrayList<String> result=slang.Dictionary.get(edt_find_delete.getText());
                if (result==null) {
                    delete_model.add(0, "Not found definition for " + edt_find_delete.getText());
                }

                else {
                    delete_model.add(0,""+edt_find_delete.getText()+" = "+result);
                }
                edt_find_delete.setText("");
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
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\semester 1 - 3nd year\\java\\PA_01_DictionaryApp (1)\\PA_01_DictionaryApp\\slang.txt")));
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    String line = null;
                    try {
                        line = br.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    String word = null;
                    while (line != null) {
                        System.out.println(line);
                        String[] information = line.split("`");
                        if (information.length > 1) {
                            slang.Dictionary.put(information[0], new ArrayList<String>(List.of(information[1])));
                            word = information[0];
                        } else {
                            slang.Dictionary.get(word).add(information[0]);
                        }
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
                ArrayList<String> random_defs=slang.Dictionary.get(random_key);
                t_random_slang.setText("    -"+random_key);
                list_def_random.setListData(random_defs.toArray());

            }
        });

        btn_quiz_slang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> keysAsArray = new ArrayList<String>(slang.Dictionary.keySet());
                Random r = new Random();
                String random_key =keysAsArray.get(r.nextInt(keysAsArray.size()));
                ArrayList<String> random_defs=slang.Dictionary.get(random_key);
                answer_Slang=random_defs.get(0);
                random_defs.remove(0);

                for (String s:random_defs)
                    answer_Slang=answer_Slang.concat(" or "+s);

                List<ArrayList<String>> valuesAsArray = new ArrayList<ArrayList<String>>(slang.Dictionary.values());

                ArrayList<String> random_answer=valuesAsArray.get(r.nextInt(valuesAsArray.size()));
                String answer_1=random_answer.get(0);
                random_answer.remove(0);
                for (String s:random_answer)
                    answer_1=answer_1.concat(" or "+s);

                random_answer=valuesAsArray.get(r.nextInt(valuesAsArray.size()));
                String answer_2=random_answer.get(0);
                random_answer.remove(0);
                for (String s:random_answer)
                    answer_2=answer_2.concat(" or "+s);

                random_answer=valuesAsArray.get(r.nextInt(valuesAsArray.size()));
                String answer_3=random_answer.get(0);
                random_answer.remove(0);
                for (String s:random_answer)
                    answer_3=answer_3.concat(" or "+s);

                List<String> answers=new ArrayList<String>(List.of(new String[]{answer_1, answer_2, answer_3, answer_Slang}));
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
                if (result.isEmpty())
                    JOptionPane.showMessageDialog(null, "You should generate quiz to start quiz",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                else
                {
                    if (result.equals(answer_Slang)){
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
                if (result.isEmpty())
                    JOptionPane.showMessageDialog(null, "You should generate quiz to start quiz",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                else
                {
                    if (result.equals(answer_Slang)){
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
                if (result.isEmpty())
                    JOptionPane.showMessageDialog(null, "You should generate quiz to start quiz",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                else
                {
                    if (result.equals(answer_Slang)){
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
                if (result.isEmpty())
                    JOptionPane.showMessageDialog(null, "You should generate quiz to start quiz",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                else
                {
                    if (result.equals(answer_Slang)){
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

                ArrayList<String> random_defs=slang.Dictionary.get(answer_Def);
                String question=random_defs.get(0);
                random_defs.remove(0);

                for (String s:random_defs)
                    question=question.concat(" or "+s);


                String answer_1=keysAsArray.get(r.nextInt(keysAsArray.size()));
                String answer_2=keysAsArray.get(r.nextInt(keysAsArray.size()));
                String answer_3=keysAsArray.get(r.nextInt(keysAsArray.size()));

                List<String> answers=new ArrayList<String>(List.of(new String[]{answer_1, answer_2, answer_3, answer_Def}));
                t_qt_def.setText("Choose slang word for definition: "+question);
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
                if (result.isEmpty())
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
                if (result.isEmpty())
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
                if (result.isEmpty())
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
                if (result.isEmpty())
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

    }

}