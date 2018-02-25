import jdk.internal.org.objectweb.asm.Handle;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject {
    Handler handler;
    Random r = new Random();
    int choose;
    int hp = 100;
    BufferedImage[] Enemy_image = new BufferedImage[3];
    Animation anim;
    boolean atackMode = false;
    GameObject PlayerObject = null;

    public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x,y,id,ss);
        this.handler = handler;
        Enemy_image[0] = ss.grabImage(4,1,32,32);
        Enemy_image[1] = ss.grabImage(5,1,32,32);
        Enemy_image[2] = ss.grabImage(6,1,32,32);

        anim = new Animation(3, Enemy_image[0], Enemy_image[1], Enemy_image[2]);
    }
    @Override
    public void tick() {
         x += velX;
         y += velY;

         choose = r.nextInt(10);


         for(int i = 0; i <handler.object.size();i++){
             GameObject tempObj = handler.object.get(i);

                     if(tempObj.getId() == ID.Block){ //lógica de colisão

                         if(getBounds().intersects(tempObj.getBounds())){
                             x += (velX*5) * -1;
                             y += (velY*5) * -1;
                             velX *= -1;
                             velY *= -1;

                         }
                         if(choose == 0){
                             velX = (r.nextInt(4 - - 4) + -4);
                             velY = (r.nextInt(4 - - 4) + -4);
                         }




                     }

                     if(tempObj.getId() == ID.Bullet) {
                         if(getBounds().intersects(tempObj.getBounds())){
                             hp -= 50;
                             handler.removeObject(tempObj);

                         }
                     }

                     if(tempObj.getId() == ID.Player){

                         if(getBoundsBig().intersects(tempObj.getBounds()) || atackMode == true){
                             atackMode = true;
                             PlayerObject = tempObj;

                             velX = velY = 0;

                             velX += (tempObj.getX() - this.getX())*1/25;
                             velY += (tempObj.getY() - this.getY())*1/25;

                             x += (int) velX;
                             y += (int) velY;




                         }




                     }
         }

         if(hp == 0 ) handler.removeObject(this);



         if(choose == 0) {
             velX = (r.nextInt(4- -4) + -4);
             velY = (r.nextInt(4- -4) + -4);}

             anim.runAnimation();

    }

    @Override
    public void render(Graphics g) {
        /*g.setColor(Color.YELLOW);
        g.fillRect(x,y,32,32);
*/
        Graphics2D g2d = (Graphics2D)g;

        //mostra o campo de visão do inimigo
        //g2d.setColor(Color.green);
        //g2d.fillRect(-128,-128,256,-256);

        //g2d.draw(getBoundsBig());


        /*
        try {
            if(atackMode) {
                g.setColor(Color.blue);
                g.drawLine(x,y,PlayerObject.getX(), PlayerObject.getY() );
            }


        }
        catch (Exception e) {

        }
*/


        //((Graphics2D) g).drawImage(Enemy_image[0],x,y,null);


        anim.drawAnimation(g,x,y,0);


    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }

    public Rectangle getBoundsBig() {
        return new Rectangle(x - 128,y - 128,256,256);
    }
/*
    public Line2D getInteraction() {
        return new Line2D() {
            @Override
            public double getX1() {
                return 0;
            }

            @Override
            public double getY1() {
                return 0;
            }

            @Override
            public Point2D getP1() {
                return null;
            }

            @Override
            public double getX2() {
                return 0;
            }

            @Override
            public double getY2() {
                return 0;
            }

            @Override
            public Point2D getP2() {
                return null;
            }

            @Override
            public void setLine(double x1, double y1, double x2, double y2) {

            }

            @Override
            public Rectangle2D getBounds2D() {
                return null;
            }
        }
    }*/
}
