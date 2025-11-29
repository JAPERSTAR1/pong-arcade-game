package ui;

import game.Ball;
import game.Paddel;
import game.Score;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;



import java.awt.event.*;
import java.util.Random;
public class PongPanel extends JPanel implements Runnable, ActionListener {
    
    public static final int WIDTH = PongFrame.screenSize.width;
    public static final int HEIGHT = PongFrame.screenSize.width * 9 / 16;
    public static final int BALL_DIAMETER = 40;
    public static final int PADDLE_WIDTH = 50;
    public static final int PADDLE_HEIGHT = 260;
    public static Thread gameThread;
    public static Image image;
    public static Graphics graphics;
    public static Random random;
    public static Paddel player1;
    public static Paddel player2;
    public static Ball ball;
    public static Score score;
    public static boolean gameStarted = false;
    public static int numOfHits = 0;
    public static boolean gameOver = false;
    public static String winner = "no winner";
    public static int redWins = 0;
    public static int blueWins = 0;
    PongPanel() {
        newPaddels();
        newBall();
        score = new Score(WIDTH, HEIGHT);
        this.setBorder(new LineBorder(Color.GREEN, 5));
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new Kl());
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        image = createImage(WIDTH, HEIGHT);
        graphics = image.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        draw(graphics);
        g.drawImage(image, 0, 0, this);
        
    }

    public void newBall() {
      
      ball = new Ball(WIDTH / 2 - BALL_DIAMETER / 2, HEIGHT / 2 - BALL_DIAMETER / 2, BALL_DIAMETER, BALL_DIAMETER);
    }

    public void newPaddels() {
        player1 = new Paddel(0, HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        player2 = new Paddel(WIDTH - PADDLE_WIDTH, HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 2);
        

    }

    public void draw(Graphics g) {
     if(!gameStarted) {
        if(winner.equals("Red")){
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("The Winner is Red !", WIDTH / 2 - 140, HEIGHT / 2 - 400);  
        }
        if(winner.equals("Blue")){
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("The Winner is Blue !", WIDTH / 2 - 140, HEIGHT / 2 - 400);  
        }
        
        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Press Enter to Start", WIDTH / 2 - 140, HEIGHT / 2 - 100);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("" + redWins, WIDTH / 2 + 30, HEIGHT / 2 - 300);
        g.setColor(Color.WHITE);
        g.drawString("-"  , WIDTH / 2 - 10, HEIGHT / 2 - 302);
        g.setColor(Color.BLUE);
        g.drawString("" + blueWins, WIDTH / 2 - 60, HEIGHT / 2 - 303);
        
     }
     else {
            g.setColor(Color.GRAY);
            g.drawOval(WIDTH/2 - 20, HEIGHT/2, BALL_DIAMETER, BALL_DIAMETER);
            g.drawLine(WIDTH/2,0,WIDTH/2,HEIGHT);
        }
     g.setColor(Color.WHITE);
     g.setFont(new Font("Arial", Font.PLAIN, 20));
     g.drawString("Speed: " + Math.abs(ball.xVelocity), 20, 30);  
     player1.draw(g);
     player2.draw(g);
     ball.draw(g);


    }

    public void move() {
        player1.move();
        player2.move();
        ball.move();
     
    }
    public void checkCollision() {
        if(ball.x < 0){
            winner = "Red";
            redWins++;
         gameOver();
        }

        if(ball.x > WIDTH - BALL_DIAMETER){
         gameOver();
         winner = "Blue";
         blueWins++;
        }


        if(ball.y <= 0) {
            ball.setYdirection(-ball.yVelocity);
        }
        if(ball.y >= HEIGHT - BALL_DIAMETER) {
            ball.setYdirection(-ball.yVelocity);
        }
        if(ball.intersects(player1)) {
            numOfHits++;
            ball.xVelocity = Math.abs(ball.xVelocity) + 3;
            if(ball.xVelocity > 22) 
            ball.xVelocity = 22;
            ball.setXdirection(ball.xVelocity);
        }
        if(ball.intersects(player2)) {
            numOfHits++;
            ball.xVelocity = -Math.abs(ball.xVelocity) -3;
            if(ball.xVelocity < -22) 
            ball.xVelocity = -22;
            ball.setXdirection(ball.xVelocity);
        }



        if(player1.y <= 0) 
            player1.y = 0;
        if(player1.y >= HEIGHT - PADDLE_HEIGHT) 
            player1.y = HEIGHT - PADDLE_HEIGHT;
        if(player2.y <= 0) 
            player2.y = 0;
        if(player2.y >= HEIGHT - PADDLE_HEIGHT) 
            player2.y = HEIGHT - PADDLE_HEIGHT;
    }
        
    public void gameOver() {
    gameOver = false;
    reset();
    }
    public void reset() {
        if(winner.equals("Red")){
     ball.xVelocity = -5;
     ball.yVelocity = -5;
        }
        else{
     ball.xVelocity = 5;
     ball.yVelocity = 5;
        }
        ball.randomize();
     numOfHits = 0;
     gameStarted = false;
     player1.y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
     player2.y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
     ball.x = WIDTH / 2 - BALL_DIAMETER / 2;
     ball.y = HEIGHT / 2 - BALL_DIAMETER / 2;
     
    }

    public void run(){
      long lastTime = System.nanoTime();
        double amountOfTicks = 0.001;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            Long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            if (delta >= 1) {
                if(gameStarted) {
                    
                        move();
                        checkCollision();
                    } else {
                        
                    }
                
                repaint();
                delta--;
                lastTime = now;
                
            }

            
        }
         
    }
    public class Kl extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(!gameStarted){
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    winner = "no winner";
                    gameStarted = true;
                    
                
            }else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                winner = "no winner";
                gameStarted = true;
                    
            }
            else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
               System.exit(0);
            }else{
              return;
            }
        }
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
               System.exit(0);
            }
         player1.keyPressed(e);
         player2.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
          player1.keyReleased(e);
          player2.keyReleased(e);
        }
    }
}
