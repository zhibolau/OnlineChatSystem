/**
 * Created by zhiboliu on 16-7-5.
 */

import java.io.IOException;
import java.net.*;
//网络版 当然引入java net包

public class ChatServer {
    public static void main(String args[]){
        //make a new service

        /* tcp port number */
        try {
            ServerSocket ss = new ServerSocket(8888);
            while(true){
                Socket s = ss.accept();
System.out.println("a client connected!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
