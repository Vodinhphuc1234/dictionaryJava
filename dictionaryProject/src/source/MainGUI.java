package source;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * source
 * Created by ACER
 * Date 12/22/2021 - 4:13 PM
 * Description: ...
 */
public class MainGUI extends JFrame{
    private JTextField edt_slang;
    private JButton btn_findBySlang;
    private JTextField edt_definition;
    private JButton btn_findByDefinition;
    private JPanel homePane;
    private JList list_result;
    private JButton addSlangButton;
    private JButton historyButton;
    private JButton editSlangButton;
    private JButton deleteSlangButton;
    private JButton resetSlangListButton;
    private JButton slangWordTodayButton;
    private JButton quizWithSlangButton;
    private JButton quizWithDefinitionButton;
    private DefaultListModel model=new DefaultListModel();
    private Slang slang;
    HashMap<String, ArrayList<String>> History;

    public MainGUI(Slang s, HashMap<String, ArrayList<String>> History){
        super("Home");
         setContentPane(homePane);
         this.slang=s;
         this.History=History;
         list_result.setModel(model);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(600,600));
        this.pack();
        this.setVisible(true);

        btn_findByDefinition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edt_slang.setText("");
                model.clear();
                for(Map.Entry<String,ArrayList<String>> entry: slang.Dictionary.entrySet())
                {
                    for (String def: entry.getValue())
                    {

                        if (def.indexOf(edt_definition.getText())>1){
                            System.out.println(entry.getValue());
                            model.add(0,entry.getKey()+" = "+entry.getValue());
                        }
                    }
                }
            }
        });
        btn_findBySlang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edt_definition.setText("");
                model.clear();
                ArrayList<String> result=slang.Dictionary.get(edt_slang.getText());
                if (result==null) {
                    model.add(0, "Not found definition for " + edt_slang.getText());
                }

                else {
                    model.add(0,""+edt_slang.getText()+" = "+result);
                }
            }
        });
    }
}
