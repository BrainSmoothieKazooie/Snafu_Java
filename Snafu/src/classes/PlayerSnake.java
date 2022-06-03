package classes;

import java.awt.event.*;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;

public class PlayerSnake extends Snake implements KeyListener
{	
    public PlayerSnake()
    {
	    ImageIcon temp = new ImageIcon("src/Sprites/Snake/Snake.png");
        setImage(temp.getImage());
        resize(100, 100);
        collider.x = 100;
        collider.y = 100;
    }

    public void draw(Graphics2D graphics)
    {
      super.draw(graphics);
      drawCollider(graphics);
    }
  
    @Override
	public void keyTyped(KeyEvent e) 
	{
		// TODO Auto-generated method stub
	
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) && direction != Direction.SOUTH)
			direction = Direction.NORTH;
		
		else if ((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) && direction != Direction.NORTH)
			direction = Direction.SOUTH;
		
		else if ((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) && direction != Direction.WEST)
			direction = Direction.EAST;
		
		else if ((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) && direction != Direction.EAST)
			direction = Direction.WEST;
	}
	
	@Override
	public void keyReleased(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
}
