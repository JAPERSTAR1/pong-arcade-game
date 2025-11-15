package ui;

import java.awt.*;
import javax.swing.*;

import java.awt.Toolkit;

public class PongFrame extends JFrame {

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static PongPanel pongPanel = new PongPanel();
    public PongFrame(){
    this.setTitle("Pong Game");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setSize(screenSize.width, screenSize.height);
    this.setLayout(null);
    this.setLocationRelativeTo(null);
    this.setUndecorated(true);
    


    this.add(pongPanel);
    this.setVisible(true);



    }






}
