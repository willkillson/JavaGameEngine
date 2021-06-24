import game.Game;
import graphics.Screen;
import input.InputEvent;
import input.KeyHandler;
import input.MouseHandler;
import input.ImageLoader;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Stack;

public class Engine extends Canvas implements Runnable{

    public static int width = 1920;
    public static int height = 1080;
    public static int scale = 1;

    private Thread thread;
    public JFrame frame;
    private boolean running = false;

    private Stack<input.InputEvent> inputEventQue;
    private MouseHandler mouseHandler;
    private KeyHandler keyHandler;

    Game game;
    private Screen screen;
    
    private BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

    //private BufferedImage image2 = ImageLoader.loadImage("Doggie.png");
    private BufferedImage image3 = ImageLoader.loadImage("./assets/hex_tiles/water_hex_tile_1000.png");
    
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public static void main(String[] args) {
        new Engine();
    }

    public Engine(){

        Dimension size = new Dimension(width*scale,height*scale);
        setPreferredSize(size);

        screen = new Screen(width,height);
        frame = new JFrame();

        this.inputEventQue = new Stack<InputEvent>();
        this.mouseHandler = new MouseHandler(inputEventQue);
        this.keyHandler = new KeyHandler(inputEventQue);

        this.addMouseListener(this.mouseHandler);
        this.addMouseMotionListener(this.mouseHandler);
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
        game = new Game(screen, this.inputEventQue);

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

        int iWidth = image3.getWidth()/10;
        int iHeight = image3.getHeight()/10;

        //BufferedImage resizedImage3 = ImageLoader.resizeImage(image3, iWidth, iHeight);
        //int[] pixelArray = ImageLoader.getPixelArray(image3, image3.getWidth(), image3.getHeight());
        BufferedImage resizedImage3 = ImageLoader.resizeImage(image3, iWidth, iHeight);
        int[] pixelArray = resizedImage3.getRGB(0, 0, resizedImage3.getWidth(), resizedImage3.getHeight(), null, 0, resizedImage3.getWidth());

        screen.movePixels(pixelArray,screen.width/2,screen.height/2,resizedImage3.getWidth(),resizedImage3.getHeight());


        // Copy all our screen pixels into our pixels buffer
        for(int i = 0;i<pixels.length;i++){
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        
        g.drawImage(image, 0,0,getWidth(),getHeight(), null);
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
}