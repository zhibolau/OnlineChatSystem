/**
 * Created by zhiboliu on 16-7-5.
 */

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
//网络版 当然引入java net包
//0.9 deal with EOFException when client is closed, EOF will be thrown:is end of file exception 文件读到结尾了
public class ChatServer {
    public static void main(String args[]){
        boolean started = false;//if server is started
        ServerSocket ss = null;
        Socket s = null;
        DataInputStream dis = null;
        //make a new service

        /* tcp port number */
        try {
            ss = new ServerSocket(8888);
        }catch (BindException e) {
            System.out.println("Port is being used already!");//如果连续run两个server 回报出异常 显示 打印完此句还会执行ss.accept
            //so the socket should be closed or exit the whole program
            System.out.println("plz turn off related programs and restart the server.");
            System.exit(0);
        }catch (IOException e) {
            e.printStackTrace();
        }
        try{

            started = true;
            while(started){
                boolean bConnected = false;// if client is connected
                s = ss.accept();
System.out.println("a client connected!");
                bConnected = true;
                //DataInputStream dis = new DataInputStream(s.getInputStream()); dis也需要被关闭
                dis = new DataInputStream(s.getInputStream());
                //此地只能接受一次smg 然后就关闭了  应该只要他有我就读
                while(bConnected){
                    String str = dis.readUTF();//EOFException 一直等待有人send msg给他 没人给就出错了
                    System.out.println(str);
                }
                //if this client is disconnected, then dis is closed.
                //dis.close();   下边有处理了 这里就不用了
            }
        }catch (EOFException e) {
            //e.printStackTrace();
            System.out.println("client is closed.");
        }catch (IOException e) {
            //e.printStackTrace();
        }finally {
            try {
                if(dis != null){
                    dis.close();//有异常就不读写了 所以dis关掉
                }
                if(s != null){
                    s.close();//有异常 把socket关掉 有可能对方断了
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
