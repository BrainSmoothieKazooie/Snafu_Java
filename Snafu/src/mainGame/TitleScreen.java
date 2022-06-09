package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import classes.*;

public class TitleScreen extends Screen
{
	private Screen window;
	private long titleTime;
	private final long TITLE_LOADING_TIME = 500;
	private Sprite logo;
	private boolean finishedLoading;
	private int speed = 2;
	private boolean once = true;
	private Timer timer;
	
	public TitleScreen(Window window)
	{
		super(window);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (isEnabled())
		{
			if (once)
			{
				window.getMusicHandler().play("Rolling Down The Street, In My Katamari");
				once = !once;
			}
			
			if (titleTime <= TITLE_LOADING_TIME)	
			{
				Rectangle collider = logo.getCollider();
				logo.setCollider(new Rectangle(collider.x + 1 * speed, collider.y, collider.width, collider.height));
			}
			else
				finishedLoading = true;
			
			repaint();
			titleTime++;
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(logo.getImage(), logo.getCollider().x, logo.getCollider().y, 
					logo.getCollider().width, logo.getCollider().height, null);
		
		if (finishedLoading)
		{
			Graphics2D graphics = (Graphics2D)g;
			graphics.setColor(Color.white);
			graphics.setFont(new Font("SansSerif", Font.PLAIN, 40));
			graphics.drawString("Snafu", 350, 450);
			graphics.drawString("Press C to Start", 350, 500);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_C)
		{
			window.switchScreen("main screen");
			timer.stop();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
