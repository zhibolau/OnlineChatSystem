/**
 * Created by zhiboliu on 16-7-4.
 */

//建造出一个小窗口

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//need test field and area
//0.4 deal with msg of text field sent to text area

public class ChatClient extends Frame{
	
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	
	
    public static  void main (String[] args){
        new ChatClient().launchFrame();
    }
    
    

    public void launchFrame(){
        setLocation(400, 300);
        this.setSize(900, 900);
        add(tfTxt,BorderLayout.SOUTH);
        add(taContent,BorderLayout.NORTH);
        pack();//remove the black space between tf and ta
        //close window with  "x"
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        tfTxt.addActionListener(new TextfieldListener());
        setVisible(true);
    }

    //internal class
    private class TextfieldListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = tfTxt.getText().trim();// trim delete space on two sides of string
            taContent.setText(s);
            tfTxt.setText("");//after msg send, text field should be blank
        }
    }
}
