package mainGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.HashMap;
import java.util.Random;

import classes.*;
import classes.MusicHandler.MusicHandlerException;

/*
 * GameSetter.java
 * 
 * A class where the user can set how many
 * snakes they would like on the screen, what types,
 * their color, music, etc. 
 * 
 * Each option will be represented by its own component and
 * placed in a GridBagLayout.
 * 
 * Components will be grouped based on type and there
 * will be independent methods that take GridBagConstraints 
 * that create and add each component to the JPanel.
 * 
 * The User will be prevented from starting the 
 * game with one or less PlayerSnakes/AISnakes, or 
 * more than four PlayerSnakes/AISnakes. 
 * 
 * When the user presses the Start Button, then a GameScreen will
 * be created with all of the user's selected options. 
 * 
 * Author: Andrew Tacoi
 */

@SuppressWarnings("serial")
public class GameSetter extends JPanel implements ActionListener, ItemListener, ChangeListener
{
    // *********************  Fields  *********************
    
	private MainScreen mainScreen;
	private int speed;
	private Color[] snakeColors;
	private HashMap<String, JSpinner> spinners;
	private JComboBox musicList;
	private Font font = new Font("impact", Font.PLAIN, 40);
	private JButton[] colorChooserButtons;
	
	// *********************  Constructors  *********************
	
	public GameSetter(MainScreen screen)
	{
	    setName("game setter screen");
	    setLayout(new GridBagLayout());
	    setBackground(new Color(243, 150, 0));
		mainScreen = screen;
		speed = 10;
		snakeColors = new Color[4];
		spinners = new HashMap<>();
		setRandomColors();
		addComponents();
	}
	
	// *********************  Public Methods  *********************
	
	// Checks when the user pushes one the buttons.
	@Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getActionCommand().contains("speed")) // Speed Button Check
        {
            changeSpeed(e.getActionCommand().substring("speed".length()+1));
        }
        
        else if (e.getActionCommand().contains("Snake")) // Snake Color Check
        {
            Color color = JColorChooser.showDialog(this, "Select a color", Color.red); // Opens a JColorChooser in a separate window. 
            int index = Character.getNumericValue(e.getActionCommand().charAt(e.getActionCommand().length()-1));
            snakeColors[index-1] = color;
        }
        
        else if (e.getActionCommand().equals("start")) // Start Button Check
        {
            int numOfPlayers = getSpinnerValue("Number of Players:");
            int numOfComp = getSpinnerValue("Number of Computers:");
            int numOfObstacles = getSpinnerValue("Number of Obstacles:");
            int numOfRounds = getSpinnerValue("Number of Rounds:");
            
            if (numOfPlayers != -1 && numOfComp != -1 && numOfObstacles != -1 && numOfRounds != -1) // All values are valid.
            {
                int snakeTotal = numOfPlayers + numOfComp;
                
                if (snakeTotal > 4) // If the user has chosen more than four snakes.
                    JOptionPane.showMessageDialog(null, "Sorry but you can only have up to four snakes on the screen!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                
                else if (snakeTotal <= 1) //If the user has chosen one or less snakes.  
                    JOptionPane.showMessageDialog(null, "Sorry but you need two of more snakes on the screen!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                
                else // 1 < snakeTotal <= 4
                {
                    mainScreen.add(new GameScreen(mainScreen, speed, numOfObstacles, numOfPlayers, numOfComp, numOfRounds, snakeColors), "game screen"); // GameScreen card
                    
                    mainScreen.switchScreen("game screen");
                }
                    
            }
        }
    }
	
	@Override
    public void itemStateChanged(ItemEvent e) // When the User selects a different music track, this method is called.
    {
        if (e.getSource() == musicList) 
        {
            MusicHandler backgroundMusicHandler = mainScreen.getMusicHandler();
            
            if (musicList.getSelectedItem() instanceof String)
            {
                String itemName = (String)musicList.getSelectedItem();
                
                try {
                    backgroundMusicHandler.play(itemName, true);
                    
                } catch (MusicHandlerException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) // Is called when one of the Spinners in the spinners HashMap is changed.
    {
        int totalSnakes = getSpinnerValue("Number of Players:") + getSpinnerValue("Number of Computers:");

        boolean isEnabled = true;
        for (int i = 0; i < 4; i++) // Updates the Snake Color Buttons
        {         
            if (i >= totalSnakes) // Disables the rest of the Spinners when there are more buttons than totalSnakes. 
                isEnabled = false;
            
            colorChooserButtons[i].setEnabled(isEnabled);
        }
    }
    
    // *********************  Private Methods  *********************
	
	private void setRandomColors() // Gets random RGB values and sets each snake color to these random values. 
	{
	    for (int i = 0; i < snakeColors.length; i++)
	    {
	        Random random = new Random();
	        float r = random.nextFloat();
	        float g = random.nextFloat();
	        float b = random.nextFloat();
	        
	        snakeColors[i] = new Color(r, g, b);
	    }
	}
	
	private void addComponents() // Adds all menu options using a GridBagLayout
	{
	    GridBagConstraints constraints = new GridBagConstraints();
	    
	    addSpeedOptions(constraints);
	    
	    constraints.gridwidth = 2;

        addAllSliders(constraints);
        
        constraints.gridy++;
        constraints.gridwidth = 1;
        
        addColorChooserButtons(constraints);
        
        constraints.gridy++;
        constraints.gridwidth = 2;
        
        addAllMusicTrackOptions(constraints);
	    
	    constraints.gridy++;
	    constraints.gridwidth += 2;
	    
	    // Adds the start button
	    JButton startButton = new JButton("start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);
        startButton.setFont(font);
        add(startButton, constraints);
	}
	
	private void addSpeedOptions(GridBagConstraints constraints) // Adds the three different speed options by using a JRadioButton
	{
	    ButtonGroup group = new ButtonGroup();
	       
        constraints.gridy = 0;
        
        JLabel speed = new JLabel("Speed:");
        speed.setFont(font);
        speed.setForeground(Color.white);
        add(speed, constraints);

        String[] speedChoices = {"slow", "medium", "fast", "hyper"};
        
        constraints.gridwidth = speedChoices.length;
        
        for (int i = 0; i < speedChoices.length; i++)
        {
            String speedChoice = speedChoices[i];

            JRadioButton button = new JRadioButton(speedChoice);
            button.setSelected(true);
            button.setActionCommand("speed " + speedChoice);
            group.add(button);
            button.addActionListener(this);
            button.setForeground(Color.white);
            button.setFont(font);
            add(button);
        }
	}
	
	private void addColorChooserButtons(GridBagConstraints constraints) // Adds four buttons to change each Snake's color. 
	{
	    colorChooserButtons = new JButton[4];
	    
	    for (int i = 1; i <= colorChooserButtons.length; i++)
	    {
	        colorChooserButtons[i-1] = new JButton("Snake #" + i);
	        colorChooserButtons[i-1].setActionCommand(colorChooserButtons[i-1].getName());
	        colorChooserButtons[i-1].addActionListener(this);
	        colorChooserButtons[i-1].setFont(font);
	        colorChooserButtons[i-1].setEnabled(false); // Enables the button based on the computer and player Spinners.
	        add(colorChooserButtons[i-1], constraints);
	    }
	}
	
	private void addAllSliders(GridBagConstraints constraints) // Adds sliders to the panel.
	{
	    int max = 4;
	    int min = 0;

        String[] numberSliders = {"Number of Players:", "Number of Computers:", "Number of Obstacles:", "Number of Rounds:"};
        
        for (int i = 0; i < numberSliders.length; i++)
        {
            if (numberSliders[i].equals("Number of Obstacles:")) // Sets Object's max value to 10.
                max = 10;
            
            else if (numberSliders[i].equals("Number of Rounds:")) // Sets Round's max value to 20 and its min to 1.
            {
                max = 20;
                min = 1;
            }
            
            constraints.gridy++;
            
            JLabel spinnerName = new JLabel(numberSliders[i]);
            spinnerName.setFont(font);
            spinnerName.setForeground(Color.white);
            add(spinnerName, constraints);
            
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(min, min, max, 1));
            ((DefaultEditor) spinner.getEditor()).getTextField().setEditable(false); // Prevents the user from changing the spinner's textfield
            spinner.setFont(mainScreen.textFont);
            spinner.setName(numberSliders[i]);
            spinner.addChangeListener(this);
            spinners.put(numberSliders[i], spinner); // Places Spinner in the hash map where it can be retrieved by accessing its name.
            add(spinner, constraints);
        }
        constraints.gridy++;
	}
	
	private void addAllMusicTrackOptions(GridBagConstraints constraints) // Adds All Music tracks to the game.
	{
	    String[] trackNames = mainScreen.getMusicHandler().getMusicTrackNames();
        
        JLabel music = new JLabel("Music");
        music.setFont(font);
        music.setForeground(Color.white);
        add(music, constraints);
        
        musicList = new JComboBox(trackNames);
        musicList.setSelectedIndex(0);
        musicList.addItemListener(this);
        add(musicList, constraints);
	}
    
    private int getSpinnerValue(String key) // Returns the value of the specified spinner by retrieving the Spinner by its name. 
    {
        JSpinner spinner = spinners.get(key);
        
        int val = -1;
        
        if (spinner.getValue() instanceof Integer)
        {
            val = (int)spinner.getValue();
        }
        
        return val;
    }
    
    private void changeSpeed(String speedName) // Changes the speed variable based on the speedName given.
    {
        switch (speedName)
        {
            case "slow":
                speed = 5;
                break;
            case "medium":
                speed = 20;
                break;
            case "fast":
                speed = 40;
                break;
            case "hyper":
                speed = 60;
                break;
        }
    }   
}
