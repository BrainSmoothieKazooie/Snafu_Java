package classes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;

import mainGame.GameScreen;

/**
 * A Sprite represents an image that has
 * a collider, represented as a Rectangle 
 * object, that is used to determine the image's
 * current position and its bounds.
 * 
 * There is also a method that allows for
 * an image to be resized, however, when 
 * placed on a grid, such as the GameScreen,
 * it will only take up one box. 
 *
 * Author: Andrew Tacoi
 */

public class Sprite 
{
    // *********************  Fields  *********************
    
	protected boolean visible;
    protected Image image;
    protected Rectangle collider;
    
    // *********************  Constructors  *********************

    public Sprite() 
    {
        visible = true;
        collider = new Rectangle(0, 0);
    }
    
    public Sprite(Image image) 
    {
    	collider = new Rectangle(0, 0);
        setImage(image);
    }
    
    public Sprite(Image image, GameScreen screen) 
    {
        Point p = randomPoint(screen);
        collider = new Rectangle (p.x, p.y, 0, 0);
        setImage(image);
    }
    
    public Sprite(Image image, int x, int y) 
    {
    	collider = new Rectangle(x, y, 0, 0);
        setImage(image);
    }
    
    // *********************  Public Methods  *********************
    
    public void resize(int width, int height, HashMap<Point, Rectangle> positions)
    {
        Image temp = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        setImage(new ImageIcon(temp).getImage());
        collider = new Rectangle(collider.x, collider.y, image.getWidth(null), image.getHeight(null));
        
        int countOutter = 0;
        for (int h = collider.y; countOutter < height/GameScreen.PIXEL_SIZE; h += GameScreen.PIXEL_SIZE)
        {
            int count = 0;
            for (int w  = collider.x; count < width/GameScreen.PIXEL_SIZE; w += GameScreen.PIXEL_SIZE)
            {
                positions.put(new Point(w, h), new Rectangle(w, h, GameScreen.PIXEL_SIZE, GameScreen.PIXEL_SIZE));
                count++;
            }
            countOutter++;
        }
    }
    
    public void resize(int width, int height)
    {
        Image temp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        setImage(new ImageIcon(temp).getImage());
        collider = new Rectangle(collider.x, collider.y, image.getWidth(null), image.getHeight(null));
    }
    
    public void draw(Graphics2D graphics)
    {
        graphics.drawImage(image, collider.x, collider.y, collider.width, collider.height,null);
        
        //drawCollider(graphics);
    }
    
    // *********************  Private Methods  *********************
    
    private Point randomPoint(GameScreen screen)
    {
        int quarterWidth = screen.getWidth()/4;
        int quarterHeight = screen.getHeight()/4;
        
        int randomX = getRandom(GameScreen.PIXEL_SIZE, (screen.getWidth()-quarterWidth)/GameScreen.PIXEL_SIZE);
        int randomY = getRandom(GameScreen.PIXEL_SIZE, (screen.getHeight()-quarterHeight)/GameScreen.PIXEL_SIZE);
        
        Point p = new Point(randomX, randomY);
        
        while (screen.getPositions().containsKey(p))
        {
            randomX = getRandom(GameScreen.PIXEL_SIZE, (screen.getWidth()-quarterWidth)/GameScreen.PIXEL_SIZE);
            randomY = getRandom(GameScreen.PIXEL_SIZE, (screen.getHeight()-quarterHeight)/GameScreen.PIXEL_SIZE);
            p = new Point(randomX, randomY);
            System.out.println(p);
        }
        return p;
    }
    
    private int getRandom(int low, int high) // returns a random number based on the given range.
    {
        Random random = new Random();
        return random.nextInt((int)(high))*low;
    }
    
    private void drawCollider(Graphics2D graphics)
    {
        graphics.setColor(new Color(1f,0f,0f,.5f));
        graphics.fillRect(collider.x, collider.y, collider.width, collider.height);
    }
    
    // *********************  Getter & Setter Methods  *********************

    public boolean isVisible() 
    { return visible; }
    
    public void setVisible(boolean visible) 
    { this.visible = visible; }
    
    public Rectangle getCollider()
    { return collider; }
    
    public void setCollider(Rectangle rect)
    { collider = rect; }
    
    public Image getImage() 
    { return image; }

    public void setImage(Image image) 
    {
        this.image = image;
        collider = new Rectangle(collider.x, collider.y, image.getWidth(null), image.getHeight(null));
    }
}
