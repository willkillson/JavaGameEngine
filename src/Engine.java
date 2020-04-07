import game.Game;
import graphics.Screen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Engine extends Canvas implements Runnable{

    public static int width = 600;
    public static int height = width / 16 * 9;
    public static int scale = 3;

    private Thread thread;
    private JFrame frame;
    private boolean running = false;

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
        game = new Game(screen);

        game.init();
        while(running){
            game.update();
            game.composeFrame();
            render();
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
