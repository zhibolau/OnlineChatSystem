/**
 * Created by zhiboliu on 16-7-4.
 */

//建造出一个小窗口

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

//need test field and area
//0.4 deal with msg of text field sent to text area
//0.5 make a running server
//0.6 deals with how to connect to server
//0.7 wanna send msg to server when press ENTER so need socket, so move socket from connect() to class ChatClient
//0.8 deal with dos close 问题 否则只能输入一次msg
public class ChatClient extends Frame{

    Socket s = null;
    DataOutputStream dos = null; // 最合适的初始化的地方是当connect的时候
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
                disconnect();
                System.exit(0);
            }
        });
        tfTxt.addActionListener(new TextfieldListener());
        setVisible(true);
        connect();
    }

    //connect to server
    // you have to run server first then run client to get connect connected
    public void connect(){
        try {
            s = new Socket("127.0.0.1", 8888);
            dos = new DataOutputStream(s.getOutputStream());
System.out.println(" connected!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disconnect(){//when window is closed, this method should be called
        try {
            dos.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //internal class
    private class TextfieldListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfTxt.getText().trim();// trim delete space on two sides of string
            taContent.setText(str);
            tfTxt.setText("");//after msg send, text field should be blank
            try {
                //DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                // 没必要每次都新建一个dos 其实建好一个在外面 然后等着赋值用就行
                dos.writeUTF(str);
                dos.flush();
                //dos.close();// 这里第一次运行完就会被关闭  所以第二次再输入msg就会报错 所以不应该关闭
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
}
