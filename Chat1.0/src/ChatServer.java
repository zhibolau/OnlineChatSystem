/**
 * Created by zhiboliu on 16-7-5.
 */

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
//网络版 当然引入java net包
//0.9 deal with EOFException when client is closed, EOF will be thrown:is end of file exception 文件读到结尾了
//1.0 wanna connect server to multiple clients ，现在只能连接上一个client 第一个连接上后readUTF一直在那等待 main在此就属于暂停了
//当第二个client想连接时候 根本连接不上 不能执行第二个client的accept方法
//you can use thread to solve this or use 异步
// so we need a thread class. if class only serve chatServer then internal class is enough
// if also works for others, need an external class
public class ChatServer {
    boolean started = false;//if server is started
    ServerSocket ss = null;

    public static void main(String args[]){

        new ChatServer().start();

//        //Socket s = null;
//       // DataInputStream dis = null; thread has defined it by itself
//
//
//        //make a new service
//
//        /* tcp port number */
//        try {
//            ss = new ServerSocket(8888);
//        }catch (BindException e) {
//            System.out.println("Port is being used already!");//如果连续run两个server 回报出异常 显示 打印完此句还会执行ss.accept
//            //so the socket should be closed or exit the whole program
//            System.out.println("plz turn off related programs and restart the server.");
//            System.exit(0);
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        try{
//
//            started = true;
//            while(started){
//                //boolean bConnected = false;// if client is connected //不需要了 thread自己定义了
//                Socket s = ss.accept();
//
//




//                Client c = new Client(s);//use thread to handle readUTF
//                //main is static method, client is dynamic class, so in static method you cannot new dynamic object
//                //so use a start() to solve this, move all codes from main to start()
//                start() is not static so can new client()
        //  start() belongs to ChatServer, so new ChatServer() then call start()
//其实我是写了一个新方法 在新方法中new client，然后main再call这个新方法





//
//System.out.println("a client connected!");
//                new Thread(c).start();
//
//                //after thread is created, following codes should be handled in thread class
////                bConnected = true;
////                //DataInputStream dis = new DataInputStream(s.getInputStream()); dis也需要被关闭
////                dis = new DataInputStream(s.getInputStream());
////                //此地只能接受一次smg 然后就关闭了  应该只要他有我就读
////                while(bConnected){
////                    String str = dis.readUTF();//EOFException 一直等待有人send msg给他 没人给就出错了
////                    System.out.println(str);
////                }
//                //if this client is disconnected, then dis is closed.
//                //dis.close();   下边有处理了 这里就不用了
//            }
//        }
////        catch (EOFException e) {
////            //e.printStackTrace();
////            System.out.println("client is closed.");
////        }不需要了 因为在thread中处理了
//        catch (IOException e) {
//            //e.printStackTrace();
//        }finally {
////            try {
////                if(dis != null){
////                    dis.close();//有异常就不读写了 所以dis关掉
////                }
//////                if(s != null){
//////                    s.close();//有异常 把socket关掉 有可能对方断了
//////                } s is already removed from main so this block should be removed
////
////            } catch (IOException e1) {
////                e1.printStackTrace();
////            }
//            try {
//                ss.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void start(){
        //Socket s = null;
        // DataInputStream dis = null; thread has defined it by itself


        //make a new service

        /* tcp port number */
        try {
            ss = new ServerSocket(8888);
            started = true;
        }catch (BindException e) {
            System.out.println("Port is being used already!");//如果连续run两个server 回报出异常 显示 打印完此句还会执行ss.accept
            //so the socket should be closed or exit the whole program
            System.out.println("plz turn off related programs and restart the server.");
            System.exit(0);
        }catch (IOException e) {
            e.printStackTrace();
        }
        try{
            while(started){
                //boolean bConnected = false;// if client is connected //不需要了 thread自己定义了
                Socket s = ss.accept();


                Client c = new Client(s);//use thread to handle readUTF
                //main is static method, client is dynamic class, so in static method you cannot new dynamic object
                //so use a start() to solve this

                System.out.println("a client connected!");
                new Thread(c).start();

                //after thread is created, following codes should be handled in thread class
//                bConnected = true;
//                //DataInputStream dis = new DataInputStream(s.getInputStream()); dis也需要被关闭
//                dis = new DataInputStream(s.getInputStream());
//                //此地只能接受一次smg 然后就关闭了  应该只要他有我就读
//                while(bConnected){
//                    String str = dis.readUTF();//EOFException 一直等待有人send msg给他 没人给就出错了
//                    System.out.println(str);
//                }
                //if this client is disconnected, then dis is closed.
                //dis.close();   下边有处理了 这里就不用了
            }
        }
//        catch (EOFException e) {
//            //e.printStackTrace();
//            System.out.println("client is closed.");
//        }不需要了 因为在thread中处理了
        catch (IOException e) {
            //e.printStackTrace();
        }finally {
//            try {
//                if(dis != null){
//                    dis.close();//有异常就不读写了 所以dis关掉
//                }
////                if(s != null){
////                    s.close();//有异常 把socket关掉 有可能对方断了
////                } s is already removed from main so this block should be removed
//
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client implements Runnable{
        private Socket s;//因为每个client都会建立自己的socket所以main方法中的socket s全局变量不需要了
        private DataInputStream dis = null;
        private boolean bConnected = true;

        public Client(Socket s){
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                //now it is connected so bconnected = true
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            //every time a socket is accepted, hand over taks to an individual thread to handle readUTF

//            //DataInputStream dis = new DataInputStream(s.getInputStream()); dis也需要被关闭
//            try {
//                dis = new DataInputStream(s.getInputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }  dis is already retrieved so this block can be removed


            //此地只能接受一次smg 然后就关闭了  应该只要他有我就读
            try {
                while(bConnected){
                    String str = null;//EOFException 一直等待有人send msg给他 没人给就出错了
                    str = dis.readUTF();
                    System.out.println(str);
                }
            }
            catch (EOFException e) {
                //e.printStackTrace();
                System.out.println("client is closed.");
            }
            catch (IOException e) {
                //e.printStackTrace();
            }finally {
                try {
                    if(dis != null){
                        dis.close();//有异常就不读写了 所以dis关掉
                    }
//                if(s != null){
//                    s.close();//有异常 把socket关掉 有可能对方断了
//                } s is already removed from main so this block should be removed

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
