/**
 * Created by zhiboliu on 16-7-5.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
//网络版 当然引入java net包

public class ChatServer {
    public static void main(String args[]){
        boolean started = false;//if server is started

        //make a new service

        /* tcp port number */
        try {
            ServerSocket ss = new ServerSocket(8888);
            started = true;
            while(started){
                boolean bConnected = false;// if client is connected
                Socket s = ss.accept();
System.out.println("a client connected!");
                bConnected = true;
                DataInputStream dis = new DataInputStream(s.getInputStream());
                //此地只能接受一次smg 然后就关闭了  应该只要他有我就读
                while(bConnected){
                    String str = dis.readUTF();
                    System.out.println(str);
                }
                //if this client is disconnected, then dis is closed.
                dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
