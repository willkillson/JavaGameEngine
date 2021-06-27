import game.Game;
import game.gamegfx.Screen;
import game.gamegfx.ScreenLayers;
import input.ConsoleHandler;
import input.InputEvent;
import input.KeyHandler;
import input.MouseHandler;
import ui.TextAreaOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.PrintStream;
import java.util.Stack;

import util.Constants;

public class Main extends Canvas implements Runnable{

    public static int scale = 1;
    
    private Thread thread;
    public JFrame frame;
    private boolean running = false;

    private Stack<input.InputEvent> inputEventQue;
    private MouseHandler mouseHandler;
    private KeyHandler keyHandler;

    //Game Time
    private final double updateRate = 1.0d/60.0d;
    private long nextStateTime;
    private int fps;
    private int ups;

    Game game;
    private ScreenLayers screenLayers;
    private BufferedImage image = new BufferedImage(Constants.WIDTH,Constants.HEIGHT,BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public static void main(String[] args) {
        new Main();
    }

    public Main(){

        Dimension size = new Dimension(Constants.WIDTH*scale,Constants.HEIGHT*scale);
        setPreferredSize(size);
        
        frame = new JFrame();


        this.screenLayers = new ScreenLayers(3);
        this.inputEventQue = new Stack<InputEvent>();
        this.mouseHandler = new MouseHandler(inputEventQue);
        this.keyHandler = new KeyHandler(inputEventQue);

        this.addMouseListener(this.mouseHandler);
        this.addMouseMotionListener(this.mouseHandler);
        this.addKeyListener(this.keyHandler);
        frame.setResizable(true);
        frame.setTitle("TestBench");

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(this,BorderLayout.CENTER);
        
        //// Console
        JTextArea ta = new JTextArea(10,5);
        TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );

        ta.addKeyListener(new ConsoleHandler(inputEventQue));
        p.add(new JScrollPane(ta),BorderLayout.SOUTH);
        frame.add(p);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        start();
    }

    public synchronized void start(){
        
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
        game = new Game(this.screenLayers, this.inputEventQue);
        game.init();
        running = true;
        double accumulator = 0;

        long currentTime = System.currentTimeMillis();
        long lastUpdate = currentTime;

        // Main game loop
        while(running){
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate)/ 1000d;
            accumulator += lastRenderTimeInSeconds;
            lastUpdate = currentTime;

            if(accumulator >= updateRate){
                while(accumulator>updateRate){
                    game.update();
                    accumulator-=updateRate;
                    ups++;
                }
                game.composeFrame();
                render();
                printStats();
            }


        }
    }

    private void printStats(){
        if(System.currentTimeMillis()>nextStateTime){
            System.out.println(String.format("FPS: %d, UPS: %d", fps, ups));
            fps = 0;
            ups = 0;
            nextStateTime = System.currentTimeMillis() + 1000;
        }
    }

    public void render(){
        fps++;
        BufferStrategy bs = getBufferStrategy();
        if(bs==null){
            createBufferStrategy(3);//triple buffering
            return;
        }
        // Copy all our screen pixels into our pixels buffer
        for(int i = 0;i<pixels.length;i++){
            pixels[i] = this.screenLayers.main[i];
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0,0,getWidth(),getHeight(), null);
        g.dispose();
        bs.show();
    }
}