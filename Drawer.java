import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;

public class Drawer {
    private AppMain grid;
    private int size;
    
    private JFrame drawingFrame;
    private JPanel drawingPanel;
    
    private int FPS;
    private double actualFPS;
    private long lastTime;

    public static void main() {
        new Drawer();
    }
    
    public Drawer() {
        //Sets up the basic JFrame
        drawingFrame = new JFrame("Game of Life; Made by PFFFFFFFFFFFTT Productions");
        drawingFrame.setSize(640, 640);
        drawingFrame.setVisible(true);
        
        //Sets up the JPanel
        drawingPanel = new JPanel();
        drawingPanel.setSize(drawingFrame.getSize());
        drawingPanel.setVisible(true);
        drawingFrame.add(drawingPanel);
        
        drawingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Creates a new grid with size 15
        size = 15;
        grid = new AppMain(size);
        
        //Sets up the ideal update rate
        FPS = 60;
        
        //Sets up the FPS counter variables
        actualFPS = 0;
        lastTime = -1;
        
        run();
    }
    
    private void run() {
        while(true) {
            try { Thread.sleep(1000 / FPS); } catch(Exception e) {}
            
            long currentTime = Calendar.getInstance().getTimeInMillis();
            actualFPS = 1000 / (double)(currentTime - lastTime);
            lastTime = currentTime;
            
            //Actual fps
            System.out.println("FPS: " + actualFPS + "/" + (1000/(1000/FPS)));
            
            update();
        }
    }
    
    //Handles all of the updating required.
    private void update() {
        step();
        draw();
    }
    
    //Advances the simulation one step
    private void step() {
        grid.update();
    }
    
    BufferedImage backbuffer;
    
    //Draws the current state of the simulation.
    private void draw() {
        backbuffer = new BufferedImage(drawingPanel.getWidth(), drawingPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) backbuffer.getGraphics();           
        
        drawGrid(g2d);
        
        boolean[][] array = grid.getArray();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //If this cell is alive..
                if (array[i][j]) {
                    drawLiving(g2d, i * 7, j * 7);
                }
            }
        }
        
        drawingPanel.getGraphics().drawImage(backbuffer, 0, 0, drawingPanel);
    }
    
    private void drawGrid(Graphics2D g) {
        for (int i = 0; i * 7 < drawingPanel.getWidth(); i++) {
            for (int j = 0; j * 7 < drawingPanel.getHeight(); j++) {
                for (int k = 0; k < 7; k++) {
                    for (int l = 0; l < 7; l++) {
                        if (k == 0 || l == 0 || k == 6 || l == 6) {
                            g.setColor(Color.WHITE);
                            g.fillRect((i * 7) + k, (j * 7) + l, 1, 1);
                        } else {
                            g.setColor(Color.DARK_GRAY);                            
                        }
                    }
                }
            }
        }
    }
    
    //Draws a living pattern.
    private void drawLiving(Graphics2D g, int x, int y) {
        g.setColor(Color.YELLOW);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                g.fillRect(x + i, y + j, 1, 1);
            }
        }
    }
}

