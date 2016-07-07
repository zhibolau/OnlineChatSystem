/**
 * Created by zhiboliu on 16-7-4.
 */

//建造出一个小窗口

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

//need test field and area
//0.4 deal with msg of text field sent to text area
//0.5 make a running server
//0.6 deals with how to connect to server
//0.7 wanna send msg to server when press ENTER so need socket, so move socket from connect() to class ChatClient
//0.8 deal with dos close 问题 否则只能输入一次msg
//1.2 deal with receiving msg from server side sent by other clients
// we cannot write receive function in main, it will only allow one client to receive as it will wait there for ever
// so go with thread,the same method as server

public class ChatClient extends Frame{

    Socket s = null;
    DataOutputStream dos = null; // 最合适的初始化的地方是当connect的时候
    DataInputStream dis = null;
    private boolean bConnected = false;
    Thread clientReceiveThread = new Thread(new ReceiveThread());

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
        clientReceiveThread.start();//used to get msg from server
    }

    //connect to server
    // you have to run server first then run client to get connect connected
    public void connect(){
        try {
            s = new Socket("127.0.0.1", 8888);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
System.out.println(" connected!");
            bConnected = true;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disconnect(){//when window is closed, this method should be called

        try {
            dos.close();
            dis.close();// 若不关闭 会出现 socket exception  即使关闭了也不行 readUTF还在等 所以要把bconnected设置false
            //即使设成false也有时候不行  因为 readUTF线程有可能已经在那里等着了 这时候设置成 也晚了
            //所以要先停止线程 才能执行close语句 但是线程现在在launchFrame中 所以要拿到外面去声明
            //现在还是停止不了 因为readUTF是阻塞式的方法 一直在那里等待 即使join也不行
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        try {
            bConnected = false;
            clientReceiveThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
                dis.close();// 若不关闭 会出现 socket exception  即使关闭了也不行 readUTF还在等 所以要把bconnected设置false
                //即使设成false也有时候不行  因为 readUTF线程有可能已经在那里等着了 这时候设置成 也晚了
                //所以要先停止线程 才能执行close语句 但是线程现在在launchFrame中 所以要拿到外面去声明
                //现在还是停止不了 因为readUTF是阻塞式的方法 一直在那里等待 即使join也不行
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */
    }


    //internal class
    private class TextfieldListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfTxt.getText().trim();// trim delete space on two sides of string
            //taContent.setText(str); now we need not to set this str as we can get it from server
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

    private class ReceiveThread implements Runnable{

        @Override
        public void run() {
            //if we need to read data , we need data input stream, so declare in the chatclient class
            try {
                while(bConnected){//true 不好 太硬  所以我们定义一个变量来控制它 灵活
                    String str = null;
                    str = dis.readUTF();
System.out.println(str);
                    taContent.setText(taContent.getText() + str +'\n');//show msg in client text area
                    // taContent.getText() + str will not remove what you see before
                }
            }catch (SocketException e) {//SocketException 本身就是一种IOException
                System.out.println("system is existing.");
            }catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
