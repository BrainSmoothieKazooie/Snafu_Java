package mainGame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import classes.MusicHandler;

public class Window extends JFrame
{	
	public Window()
	{
		add(new GameScreen());
		setTitle("Snafu");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String[] args)
	{
		new Window();
	}
}
