package game;

import java.awt.*;
import java.awt.event.*;
public class Paddel extends Rectangle {
    
    public int id;
    public int yVelocity;
    public int speed = 13;

    public Paddel(int x, int y, int width, int height, int id) {
        super(x, y, width, height);
        this.id = id;
        this.yVelocity = 0;


    }

    public void keyPressed(KeyEvent e) {
          switch(id){
            case 1: 
                if(e.getKeyCode() == KeyEvent.VK_W) {
                    setYdirection(-speed);
                }
                if(e.getKeyCode() == KeyEvent.VK_S) {
                    setYdirection(speed);
                }
                
                break;
            case 2: 
                
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    setYdirection(-speed);
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYdirection(speed);
                }
          }
        }

        
    public void keyReleased(KeyEvent e) {
          switch(id) {
        case 1:
            if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
                setYdirection(0);
            }
            break;
        case 2:
            if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                setYdirection(0);
            }
            break;
    }
        }
    
    public void setYdirection(int yDirection) {
       yVelocity = yDirection;
       move();  
       
    
    }
    public void move() {  
     y += yVelocity;
    }
    public void draw(Graphics g) {
     if(id == 1) {
            g.setColor(Color.blue);
        } else {
            g.setColor(Color.red);
        }
        g.fillRect(x, y, width, height);
    }    

}
