package game;

import java.awt.*;
import java.util.Random;
public class Ball extends Rectangle {
    
    public int xVelocity;
    public int yVelocity;
    public Random random;
    public static final int SPEED = 5; 
    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.randomize();
        

    }

    public void setXdirection(int randomXDirection) {
      xVelocity = randomXDirection ;
    }

    public void setYdirection(int randomYDirection) {
      yVelocity = randomYDirection ;
    }
    public void move() {  
        x += xVelocity;
        y += yVelocity;
    }
    public void draw(Graphics g) {
     g.setColor(Color.LIGHT_GRAY);
     g.fillOval(x, y, width, height);
    }
    public void randomize(){
      random = new Random();
        int randomXDirection = random.nextInt(2) == 0 ? -SPEED : SPEED; 
        int randomYDirection = random.nextInt(2) == 0 ? -SPEED : SPEED; 
        setXdirection(randomXDirection);
        setYdirection(randomYDirection);
    }

}
