package snake;

import sun.font.TrueTypeFont;

import javax.swing.*;
import java.security.cert.TrustAnchor;

public class Snake {

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setBounds(10,10,900,720);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakePanel panel = new SnakePanel();
        frame.add(panel);

        frame.setVisible(true);
    }
}
