package classes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public abstract class Sprite 
{
	protected boolean visible;
    protected Image image;
    protected Rectangle collider;

    public Sprite() {

        visible = true;
        collider = new Rectangle(0, 0);
    }
    
    public Sprite(Image image) {

        setImage(image);
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
    
    public void draw(Graphics2D graphics)
    {
      graphics.drawImage(image, collider.x, collider.y, collider.width, collider.height,null);
    }
    
    protected void drawCollider(Graphics2D graphics)
    {
    	graphics.setColor(new Color(1f,0f,0f,.5f));
		graphics.fillRect(collider.x, collider.y, collider.width, collider.height);
    }
}
