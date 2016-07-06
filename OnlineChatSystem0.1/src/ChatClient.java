/**
 * Created by zhiboliu on 16-7-4.
 */

//建造出一个小窗口

import java.awt.*;

public class ChatClient extends Frame{
    public static  void main (String[] args){
        new ChatClient().launchFrame();
    }

    public void launchFrame(){
        setLocation(400, 300);
        this.setSize(900, 900);
        setVisible(true);

    }
}
