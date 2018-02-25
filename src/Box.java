import java.awt.*;
import java.awt.image.BufferedImage;

public class Box extends GameObject {
    BufferedImage block_image;



    @Override
    public void tick() {
        x += velX;
        y += velY;

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(x,y,32,32);

        g.drawImage(block_image,x,y,null);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }

    public Box(int x, int y, ID id, SpriteSheet ss){
        super(x,y,id,ss);
        block_image = ss.grabImage(5,2,32,32);
    }
}
