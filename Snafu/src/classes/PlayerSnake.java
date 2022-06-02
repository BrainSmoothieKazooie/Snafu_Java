package classes;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.Graphics2D;

public class PlayerSnake extends Snake implements KeyListener
{
	private String direction = "NORTH";
	
  public PlayerSnake()
  {
	  ImageIcon temp = new ImageIcon("src/Images/snake_boy.jpg");
      setImage(temp.getImage());
      resize(100, 100);
  }

  public void draw(Graphics2D graphics)
  {
    graphics.drawImage(image, collider.x, collider.y, collider.width, collider.height,null);
  }
    
  protected void drawCollider(Graphics2D graphics)
  {
    
  }
    
  public boolean isColliding(Sprite sprite)
  {
    return false;
  }
  
  public void move(int dx, int dy)
  {
    collider.x += dx;
    collider.y += dy;
  }
  
  @Override
	public void keyTyped(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			direction = "EAST";
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public String getDirection()
	{ return direction; }
}
