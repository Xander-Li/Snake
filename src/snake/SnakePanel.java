package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.Random;

public class SnakePanel extends JPanel implements KeyListener, ActionListener {

    ImageIcon up = new ImageIcon("up.png");
    ImageIcon down = new ImageIcon("down.png");
    ImageIcon right = new ImageIcon("right.png");
    ImageIcon left = new ImageIcon("left.png");
    ImageIcon title = new ImageIcon("title.jpg");
    ImageIcon food = new ImageIcon("food.png");
    ImageIcon body = new ImageIcon("body.png");

    int[] snakex = new int[750];
    int[] snakey = new int[750];

    Random rand = new Random();
    int foodx;
    int foody;

    int len;
    String fangxiang; //R右，L左，U上，D下
    int score;

    boolean isStarted = false;
    boolean isFailed = false;

    Timer timer = new Timer(100,this);

    public void setup(){
        len = 3;
        fangxiang = "R";
        score = 0;
        foodx = rand.nextInt(34)*25+25;
        foody = rand.nextInt(24)*25+75;

        snakex[0] = 100;
        snakey[0] = 100;
        snakex[1] = 75;
        snakey[1] = 100;
        snakex[2] = 50;
        snakey[2] = 100;
        isStarted = false;
        isFailed = false;
    }

    public SnakePanel(){
        this.setFocusable(true);
        this.addKeyListener(this);
        setup();
        timer.start();
    }

    public void paint(Graphics g){
        super.paint(g);
        this.setBackground(Color.WHITE);
        title.paintIcon(this, g, 25,11);
        g.fillRect(25, 75, 850, 600);

        //画蛇头
        if(fangxiang.equals("R")){
            right.paintIcon(this,g,snakex[0],snakey[0]);
        }else if(fangxiang.equals("L")){
            left.paintIcon(this,g,snakex[0],snakey[0]);
        }else if(fangxiang.equals("U")){
            up.paintIcon(this,g,snakex[0],snakey[0]);
        }else if(fangxiang.equals("D")){
            down.paintIcon(this,g,snakex[0],snakey[0]);
        }

        //画蛇的身体
        for(int i=1; i<len; i++){
            body.paintIcon(this,g,snakex[i],snakey[i]);
        }

        food.paintIcon(this,g,foodx,foody);

        if(!isStarted){
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial",Font.BOLD,30));
            g.drawString("Press Space to Start/Pause",300,300);
        }

        if(isFailed){
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial",Font.BOLD,30));
            g.drawString("Game Over! Press Space to ReStart",300,300);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 10));
        g.drawString("Score:" + score,800,30);
        g.drawString("Length:" + len,800,50);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int KeyCode = e.getKeyCode();
        if(KeyCode == KeyEvent.VK_SPACE){
            if(isFailed) {
                setup();
            }else{
                isStarted = !isStarted;
            }
        }else if(KeyCode == KeyEvent.VK_UP && fangxiang != "D"){
            fangxiang = "U";
        }else if(KeyCode == KeyEvent.VK_DOWN && fangxiang != "U"){
            fangxiang = "D";
        }else if(KeyCode == KeyEvent.VK_RIGHT && fangxiang != "L"){
            fangxiang = "R";
        }else if(KeyCode == KeyEvent.VK_LEFT && fangxiang != "R"){
            fangxiang = "L";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 1.定义一个闹钟
        timer.start();

        // 2.更新与蛇有关的数据
        if (isStarted && !isFailed) {
            // 移动身子
            for (int i = len; i > 0; i--) {
                snakex[i] = snakex[i - 1];
                snakey[i] = snakey[i - 1];
            }

            // 移动头
            if (fangxiang.equals("R")) {
                snakex[0] = snakex[0] + 25;
            } else if (fangxiang.equals("L")) {
                snakex[0] = snakex[0] - 25;
            } else if (fangxiang.equals("U")) {
                snakey[0] = snakey[0] - 25;
            } else if (fangxiang.equals("D")) {
                snakey[0] = snakey[0] + 25;
            }

            // 吃食物
            if(snakex[0]==foodx && snakey[0]==foody){
                len++;
                score++;
                foodx = rand.nextInt(34)*25+25;
                foody = rand.nextInt(24)*25+75;
            }

            // 蛇头与身体碰撞
            for(int i=1; i<len; i++){
                if(snakex[0] == snakex[i] && snakey[0] == snakey[i]){
                    isFailed = true;
                }
            }

            // 蛇头撞到墙壁
            if(snakex[0]>850 || snakex[0]<25 || snakey[0]>650 || snakey[0]<75){
                isFailed = true;
            }
        }

        // 3.repaint()
        repaint();
    }
}
