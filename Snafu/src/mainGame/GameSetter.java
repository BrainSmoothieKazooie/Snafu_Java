package mainGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;
import java.util.HashMap;
import java.util.Random;

import classes.*;
import classes.MusicHandler.MusicHandlerException;

@SuppressWarnings("serial")
public class GameSetter extends JPanel implements ActionListener, ItemListener
{
	private MainScreen mainScreen;
	private int speed;
	private Color[] snakeColors;
	private HashMap<String, JSpinner> spinners;
	private JComboBox musicList;
	private Font font = new Font("arial", Font.PLAIN, 40);
	
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
	
	private void setRandomColors()
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
	
	private void addComponents()
	{
	    GridBagConstraints constraints = new GridBagConstraints();
	    
	    addSpeedOptions(constraints);
	    
	    constraints.gridwidth = 2;

        addAllSliders(constraints);
        
        addAllMusicTrackOptions(constraints);
	    
	    constraints.gridy++;
	    constraints.gridwidth += 2;
	    
	    // Adds the start button
	    JButton startButton = new JButton("start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);
        startButton.setFont(font);
        startButton.setBackground(new Color(243, 150, 0));
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
	
	private void addAllSliders(GridBagConstraints constraints)
	{
	    int max = 4;
	    int min = 0;

        String[] numberSliders = {"Number of Players:", "Number of Computers:", "Number of Objects:", "Number of Rounds:"};
        
        for (int i = 0; i < numberSliders.length; i++)
        {
            if (numberSliders[i].equals("Number of Objects:"))
                max = 10;
            else if (numberSliders[i].equals("Number of Rounds:"))
            {
                max = 20;
                min = 1;
            }
            constraints.gridy++;
            JLabel spinnerName = new JLabel(numberSliders[i]);
            spinnerName.setFont(font);
            spinnerName.setForeground(Color.white);
            add(spinnerName, constraints);
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, min, max, 1));
            ((DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
            spinner.setFont(mainScreen.textFont);
            spinner.setName(numberSliders[i]);
            spinners.put(numberSliders[i], spinner);
            add(spinner, constraints);
        }
        constraints.gridy++;
	}
	
	public void addAllMusicTrackOptions(GridBagConstraints constraints)
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

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getActionCommand().contains("speed"))
        {
            changeSpeed(e.getActionCommand().substring("speed".length()+1));
        }
        
        else if (e.getActionCommand().equals("start"))
        {
            int numOfPlayers = getSpinnerValue("Number of Players:");
            int numOfComp = getSpinnerValue("Number of Computers:");
            
            if (numOfPlayers != -1 && numOfComp != -1)
            {
                int snakeTotal = numOfPlayers+numOfComp;
                if (snakeTotal > 4)
                    JOptionPane.showMessageDialog(null, "Sorry but you can only have up to four snakes on the screen!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    
                    mainScreen.add(new GameScreen(mainScreen, speed, getSpinnerValue("Number of Objects:"), 
                                                  numOfPlayers, numOfComp, snakeColors, getSpinnerValue("Number of Rounds:")), "game screen");
                    
                    mainScreen.switchScreen("game screen");
                }
                    
            }
        }
    }
    
    private int getSpinnerValue(String key)
    {
        JSpinner spinner = spinners.get(key);
        
        int val = -1;
        
        if (spinner.getValue() instanceof Integer)
        {
            val = (int)spinner.getValue();
        }
        
        return val;
    }
    
    private void changeSpeed(String speedName)
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

    @Override
    public void itemStateChanged(ItemEvent e) 
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

}
