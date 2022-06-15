package mainGame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// The main Window of the game.
public class Window extends JFrame
{	
	public Window()
	{
		add(new MainScreen());
		setTitle("Snafu");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String[] args)
	{
	    SwingUtilities.invokeLater(new Runnable() 
	    {
            @Override
            public void run() 
            {
                new Window();                
            }
        });
	}
}
