import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;
import java.awt.event.*;

public class Drawer {
    private AppMain grid;
    private int size;

    private JFrame drawingFrame;
    private JPanel drawingPanel;

    private int FPS;
    private double actualFPS;
    private long lastTime;

    private Thread simulation;
    public static void main() {
        new Drawer();
    }

    public Drawer() {
        //Sets up the basic JFrame
        drawingFrame = new JFrame("Game of Life; Made by PFFFT Productions");
        drawingFrame.setSize(640, 640);
        drawingFrame.setVisible(true);
        drawingFrame.addKeyListener(new KeyAdapter(){
                public void keyPressed(KeyEvent e){
                    System.out.println("Key Pressed: " + e.getKeyCode());
                    if(e.getKeyCode()==KeyEvent.VK_OPEN_BRACKET){
                        if (grid.updatesPerSecond > 1) grid.updatesPerSecond -= 1;
                    } else if (e.getKeyCode()==KeyEvent.VK_CLOSE_BRACKET) {
                        if (grid.updatesPerSecond < 120) grid.updatesPerSecond += 1;
                    } else if (e.getKeyCode()==KeyEvent.VK_SPACE) {
                        grid.running = !grid.running;
                    }
                } 
            });
        drawingFrame.setFocusable(true);

        //Sets up the JPanel
        drawingPanel = new JPanel();
        drawingPanel.setSize(drawingFrame.getSize());
        drawingPanel.setVisible(true);
        drawingFrame.add(drawingPanel);

        drawingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creates a new grid with size 15
        size = 100;
        grid = new AppMain(size);

        //Sets up the ideal graphics update rate
        FPS = 60;

        //Sets up the FPS counter variables
        actualFPS = 0;
        lastTime = -1;

        simulation = new Thread(grid);

        run();
    }

    private void run() {
        simulation.start();
        while(true) {
            //try { Thread.sleep(1000 / FPS); } catch(Exception e) {}

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
        draw();
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
        for (int i = 0; i < drawingPanel.getWidth() && i< size * 7; i += 7) {
            for (int j = 0; j < drawingPanel.getHeight() && j < size * 7; j += 7) {
               g.setColor(Color.DARK_GRAY);
               g.fillRect(i, j, 6, 6);
            }
        }
    }

    //Draws a living pattern.
    private void drawLiving(Graphics2D g, int x, int y) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 5, 5);
    }
}

