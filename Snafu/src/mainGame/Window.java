package mainGame;

import java.awt.BorderLayout;

import javax.swing.*;

public class Window 
{	
	public Window()
	{
		JFrame window = new JFrame("Snafu");
		window.setLayout(new BorderLayout());
		window.setSize(1080, 1200);
		window.setLocation(window.getWidth()/2, window.getHeight()/2);

		Screen screen = new Screen(window);

		window.add(screen, BorderLayout.CENTER);
		window.setVisible(true);
	}

	public static void main(String[] args)
	{
		new Window();
	}
}
