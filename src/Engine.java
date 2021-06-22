import game.Game;
import graphics.Screen;
import graphics.Vec2;
import input.InputHandler;
import input.KeyHandler;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

public class Engine extends Canvas implements Runnable{

    public static int width = 1920;
    public static int height = 1080;
    public static int scale = 1;

    private Thread thread;
    public JFrame frame;
    private boolean running = false;

    private InputHandler inputHandler;
    private KeyHandler keyHandler;
    Game game;
    private Screen screen;
    
    private BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    private BufferedImage image2 = ImageLoader.loadImage("Doggie.png");
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public static void main(String[] args) {

        new Engine();

    }

    public Engine(){

        Dimension size = new Dimension(width*scale,height*scale);
        setPreferredSize(size);

        screen = new Screen(width,height);
        frame = new JFrame();

        this.inputHandler = new InputHandler();
        this.keyHandler = new KeyHandler();

        this.addMouseListener(this.inputHandler);
        this.addMouseMotionListener(this.inputHandler);
        this.addKeyListener(this.keyHandler);

        frame.setResizable(false);
        frame.setTitle("TestBench");
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        start();
    }

    public synchronized void start(){
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop(){
        running = false;
        try{
            thread.join();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        game = new Game(screen, this.inputHandler);

        game.init();

        // Main game loop
        while(running){
            long beginTime = System.currentTimeMillis();
            game.update();
            game.composeFrame();
            render();

            long timeTaken = System.currentTimeMillis() - beginTime;

        }
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs==null){
            createBufferStrategy(3);//triple buffering
            return;
        }

        for(int i = 0;i<pixels.length;i++){
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        
        g.drawImage(image, 0,0,getWidth(),getHeight(), null);
        g.drawImage(image2, 0,0,image2.getWidth(),image2.getHeight(), null);

        Font myFont =  new java.awt.Font("MONOSPACED", Font.PLAIN,24);
        g.setFont(myFont);

        // try{        
        //     PointerInfo mi = java.awt.MouseInfo.getPointerInfo();
        //     GraphicsDevice gd = mi.getDevice();
        //     double mouse_x = frame.getMousePosition().x;
        //     double mouse_y = frame.getMousePosition().y;
        //     double width = gd.getDefaultConfiguration().getBounds().getWidth();
        //     double height = gd.getDefaultConfiguration().getBounds().getHeight();

        //     String mouseLocation = 
        //     "mouse_x: " + mouse_x + " " + 
        //     "mouse_y: " + mouse_y + " " + 
        //     "screen_width: "+ width + " " +
        //     "screen_height: "+ height + " "+
        //     "mouse_buttons: "+ java.awt.MouseInfo.getNumberOfButtons();

        //     g.drawString(mouseLocation, 50, 50);
        //     //System.out.println(mouseLocation);

        // }catch(Exception e){
        //    //System.out.println(e);
        // }


        
        
        g.dispose();
        bs.show();

    }

    private static final class ImageLoader{

        static BufferedImage loadImage(String fileName)
        {
            BufferedImage bi = null;

            //System.err.println("....setimg...." + fileName);

            try {
                bi = ImageIO.read(new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Image could not be read");
                System.exit(1);
            }

            return bi;
        }
    }

}
