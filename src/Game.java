import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class Game extends Canvas implements Runnable {
static int altura = 560;
static int largura = 1000;
private static final long serialVersionUID = 1L;
private Thread thread;
private boolean isRunning = false;
private Handler handler;
BufferedImage level = null;
BufferedImage sprite_sheet = null;
BufferedImage floor = null;
private Camera camera;
public int ammo = 10;
private SpriteSheet ss;

    public Game(){
        altura = altura * 3/2;
        largura = largura * 3/2;
       // System.out.println("Ola mundo!");
        new Janela(largura, altura , "Primeiro game", this);
        start();
        handler = new Handler();
        camera = new Camera(0,0);
        Game game;

        //handler.addObject(new Wizard(100, 100, ID.Player, handler));
        //handler.addObject(new Wizard(100, 200, ID.Player, handler));



        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(new MouseInput(handler, camera , this,ss));

        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/cenario.png");

        sprite_sheet = loader.loadImage("/sprite_sheet.png");
        ss= new SpriteSheet(sprite_sheet);

        floor = ss.grabImage(4,2,32,32);

        loadLevel(level);
        //start();
    }


    public static void main(String[] args)
    {
        new Game();
    }

    public void tick(){

        for(int i =0; i < handler.object.size() ; i++){
            if(handler.object.get(i).getId() == ID.Player){
                camera.tick(handler.object.get(i));
            }
        }



        handler.tick();



    }
    Graphics g;

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D)g;
////

       /* g.setColor(Color.red);
        g.fillRect(0 , 0, largura, altura); */




        g2d.translate(-camera.getX(), -camera.getY());
/*
        for(int xx = 0; xx < 30*72; xx += 32) {
            for(int yy = 0; yy < 30*72; yy += 32) {
                ((Graphics2D) g).drawImage(floor,xx,yy,null);

            }
        }*/

        for(int xx = 0; xx < largura*4; xx += 32) {
            for(int yy = 0; yy < altura*4; yy += 32) {
                ((Graphics2D) g).drawImage(floor,xx,yy,null);

            }
        }

        handler.render(g);
        g2d.translate(-camera.getX(), -camera.getY());
////
        g.dispose();
        bs.show();

    }


    public void render2(BufferedImage image){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D)g;
////

       /* g.setColor(Color.red);
        g.fillRect(0 , 0, largura, altura); */




        g2d.translate(-camera.getX(), -camera.getY());
/*
        for(int xx = 0; xx < 30*72; xx += 32) {
            for(int yy = 0; yy < 30*72; yy += 32) {
                ((Graphics2D) g).drawImage(floor,xx,yy,null);

            }
        }*/


        for(int xx = 0; xx < largura*4; xx += 32) {
            for(int yy = 0; yy < altura*4; yy += 32) {
                ((Graphics2D) g).drawImage(floor,xx,yy,null);

            }
        }






        handler.render(g);
        g2d.translate(-camera.getX(), -camera.getY());
////
        g.dispose();
        bs.show();

    }

    private void loadLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();

        for(int xx = 0 ; xx < w ; xx++){
            for(int yy = 0; yy < h;  yy++){
                int pixel = image.getRGB(xx,yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 255) handler.addObject(new Box(xx*32 , yy*32 , ID.Block,ss));

                if(blue == 255 && green == 0) handler.addObject(new Wizard(xx*32,yy*32,ID.Player, handler , this,ss));

                if(green == 255  && blue == 0) handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handler,ss));

                if(green == 255 && blue == 255) handler.addObject(new Crate(xx*32, yy*32, ID.Crate,ss));

            }
        }
    }


    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(isRunning)
        {

            long now = System.nanoTime();
            delta+= (now - lastTime) / ns;
            lastTime = now;
            while(delta > 1){
                tick();
                delta--;
            }

            //render();
            render2(level);
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;
            }



        }





    }

    public void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();

    }

    public void stop() {
        isRunning = false;
        try
        {
            thread.join(); //aguarda a finalização da thread.
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

    }

}
