package classes;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public abstract class Sprite 
{
	protected boolean visible;
    protected Image image;
    protected Rectangle collider;
    protected boolean isColliding;

    public Sprite() {

        visible = true;
        isColliding = false;
        collider = new Rectangle(0, 0);
    }
    
    public Sprite(Image image) {

        this.image = image;
        collider = new Rectangle(collider.x, collider.y, image.getWidth(null), image.getHeight(null));
        isColliding = false;
    }


    public boolean isVisible() {

        return visible;
    }

    protected void setVisible(boolean visible) {

        this.visible = visible;
    }

    public void setImage(Image image) {

        this.image = image;
        collider = new Rectangle(collider.x, collider.y, image.getWidth(null), image.getHeight(null));
    }

    public Image getImage() {

        return image;
    }
    
    public Rectangle getCollider()
    { return collider; }
    
    public void resize(int width, int height)
    {
    	Image temp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    	this.image = new ImageIcon(temp).getImage();
    	collider = new Rectangle(collider.x, collider.y, image.getWidth(null), image.getHeight(null));
    }
    
    public abstract void draw(Graphics2D graphics);
    
    protected abstract void drawCollider(Graphics2D graphics);
    
    public abstract boolean isColliding(Sprite sprite);
	
}
