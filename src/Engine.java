import game.Game;
import graphics.Screen;
import input.ConsoleHandler;
import input.InputEvent;
import input.KeyHandler;
import input.MouseHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.PrintStream;
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
            //long beginTime = System.currentTimeMillis();
            game.update();
            game.composeFrame();
            render();
            //long timeTaken = System.currentTimeMillis() - beginTime;
        }
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs==null){
            createBufferStrategy(3);//triple buffering
            return;
        }
        // Copy all our screen pixels into our pixels buffer
        for(int i = 0;i<pixels.length;i++){
            pixels[i] = screen.pixels[i];
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0,0,getWidth(),getHeight(), null);
        g.dispose();
        bs.show();
    }
}