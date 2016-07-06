/**
 * Created by zhiboliu on 16-7-4.
 */

//建造出一个小窗口

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//need test field and area

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
        setVisible(true);

    }
}
