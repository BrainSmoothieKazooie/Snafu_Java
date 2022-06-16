package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

import classes.*;

/*
 * GameScreen.java
 * 
 * A class where the main game is played.
 * 
 * It contains two ArrayLists representing all
 * the snakes and obstacles on the Screen.
 * 
 * All variables in the constructor will be given from
 * the GameSetter class.
 * 
 * Each object (snakes, obstacles) will be placed on a 
 * grid, with its size being determined by the
 * PIXEL_SIZE constant. 
 * 
 * Before each snake moves, the screen will wait for
 * the amount of time in the LOADING_TIME variable and
 * then start movements, collision checks, etc. 
 * 
 * The HashMap positions is used to allow for snakes
 * and the screen itself to easily access, and check
 * if a Point does exist. This Map is the main
 * way that collisions will be handled and AISnake
 * direction determination.
 * 
 * Author: Andrew Tacoi
 */

@SuppressWarnings("serial")
public class GameScreen extends JPanel implements ScreenActions 
{ 
    // *********************  Fields  *********************  

    public static final int PIXEL_SIZE = 16;
    private final long LOADING_TIME = 20;
    
    private HashMap<Point, Rectangle> positions;
    private ArrayList<Snake> snakes;
    private ArrayList<Sprite> obstacles;
    private int numberOfSnakesAlive;
    private MainScreen mainScreen;
    private long loadingTime;
    private int numberOfRounds;
    private int numberOfObstacles;
    private int numberOfPlayers;
    private int numberOfComputers;
    private Color[] colors;
    
    // *********************  Constructors  *********************

    public GameScreen(MainScreen screen, int speed, int obstacleCount, int playerCount, int computerCount,
                      int numOfRounds, Color[] colors) 
    {
        mainScreen = screen;
        setName("game screen");
        setBackground(Color.black);
        this.setBounds(this.getX(), this.getY(), mainScreen.getWidth(), mainScreen.getHeight());
        
        positions = new HashMap<>();
        numberOfRounds = numOfRounds;
        numberOfPlayers = playerCount;
        numberOfComputers = computerCount;
        numberOfObstacles = obstacleCount;
        this.colors = colors;

        screen.timer.setDelay(1000 / speed);
        
        initializeScreen();
    }
    
    // *********************  Public Methods  *********************
    
    // Returns true if the current Point is beyond the GameScreen's boundaries.
    public boolean isOutOfBounds(Rectangle rect) 
    {
        return (rect.x < 0 || rect.x > getWidth() - rect.width || rect.y < 0 || rect.y > getHeight() - rect.height);
    }

    public boolean isOutOfBounds(Snake snake) 
    {
        Rectangle collider = snake.getCollider();
        return (collider.x < 0 || collider.x > getWidth() - collider.width || collider.y < 0
                || collider.y > getHeight() - collider.height);
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        if (numberOfSnakesAlive > 1) // Paints only when there are two or more snakes alive. 
        {
            for (Snake snake : snakes) 
            {
                if (snake.isAlive())
                    snake.draw((Graphics2D) g);
            }
            for (Sprite object : obstacles) 
            {
                object.draw((Graphics2D) g);
            }
        } 
        
        else 
        {
            Graphics2D graphics = (Graphics2D) g;
            
            String temp = "";
            
            int winnerIndex = getSnakeAliveIndex();
            
            if (winnerIndex == -1) // No snakes are alive. 
            {
                temp = "draw";
            }
            
            else // Draws the last Snake alive. 
            {
                temp = "Snake #" + (winnerIndex+1) + " wins!";
                Snake winnerSnake = snakes.get(winnerIndex);                
                winnerSnake.draw(graphics);
            }
        
            graphics.setColor(Color.white);
            graphics.setFont(new Font("impact", Font.PLAIN, 40));
            graphics.drawString(temp, getWidth() / 2 - temp.length() * (PIXEL_SIZE/4), getHeight() / 2);
        }
    }
    
    @Override
    public void step() 
    {
        if (numberOfSnakesAlive > 1 && !(loadingTime <= LOADING_TIME)) // Executes when the Screen has finished loading and there are two or more snakes alive.  
        {
            update();
            checkCollisions();
        }
        
        repaint();
        loadingTime++;
    }

    // Checks if the given Object's Point is already in the positions HashMap.
    public boolean hasCollided(Snake snake)
    { return hasCollided(snake.getCollider()); }

    public boolean hasCollided(Rectangle rect)
    { return hasCollided(new Point(rect.x, rect.y)); }
    
    public boolean hasCollided(Point point)
    { return positions.containsKey(point); }
    
    @Override
    public void initializeScreen() // Creates all the Snakes and Obstacles. 
    {
        addSnakes(numberOfPlayers, numberOfComputers, colors);
        addObstacles(numberOfObstacles);
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        // Allows the user to skip to the next round if all PlayerSnakes are dead, or if all Snakes are dead.
        if ((numberOfSnakesAlive <= 1 && e.getKeyCode() == KeyEvent.VK_ENTER) || 
            (e.getKeyCode() == KeyEvent.VK_ENTER && !playerSnakeAlive())) 
        {
            numberOfRounds--;
            if (numberOfRounds > 0)
            {
                removeAllItems();
                initializeScreen();
            }
            
            else // Resets back to the OptionsScreen
            {
                mainScreen.remove(this);
                mainScreen.timer.setDelay(1000 / 60);
                mainScreen.switchScreen("options screen");
            }
        }
        
        else if (KeyEvent.VK_ESCAPE == e.getKeyCode()) // User can exit to the OptionsScreen by pressing the Escape key.
        {
            mainScreen.remove(this);
            mainScreen.timer.setDelay(1000 / 60);
            mainScreen.switchScreen("options screen");
        }

        for (Snake snake : snakes) // Gives all PlayerSnakes the keyPressed.
        {
            if (snake instanceof PlayerSnake) 
            {
                PlayerSnake player = (PlayerSnake) snake;
                player.input(e, true);
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) 
    {
        for (Snake snake : snakes) {
            if (snake instanceof PlayerSnake) // Gives all PlayerSnakes the keyReleased.
            {
                PlayerSnake player = (PlayerSnake) snake;
                player.input(e, false);
            }
        }
    }
    
    // Returns the positions HashMap
    public HashMap<Point, Rectangle> getPositions() 
    { return positions; }
    
    // *********************  Private Methods  *********************

    // adds all obstacles by getting a random obstacle image, creating a Sprite and setting it to a random size.
    private void addObstacles(int numOfObjects)
    {
        ArrayList<Image> objectSprites = loadAllObjectSprites();
        obstacles = new ArrayList<>();

        for (int i = 0; i < numOfObjects; i++) {
            
            int randomSize = (int)(Math.random() * 4)+3;
            int randomIndex = (int) (Math.random() * objectSprites.size());
            
            Sprite sprite = new Sprite(objectSprites.get(randomIndex), this);
            
            positions.put(new Point(sprite.getCollider().x, sprite.getCollider().y), 
                          new Rectangle(sprite.getCollider().x, sprite.getCollider().y,
                          sprite.getCollider().width, sprite.getCollider().height));
            
            sprite.resize(PIXEL_SIZE*randomSize, PIXEL_SIZE*randomSize, positions);
            
            obstacles.add(sprite);
        }
    }

    // Loads all ObjectSprites by accessing the Object_Sprites folder.
    private ArrayList<Image> loadAllObjectSprites()
    {
        ArrayList<Image> objs = new ArrayList<>();
        
        String[] objectNames = {"cone_sprite.png", "cookie_sprite.png", "red_block_sprite.png", "spray_sprite.png"};
        
        for (String objectName : objectNames)
        {
            try {
                objs.add(ImageIO.read(getClass().getResource("/Resources/Sprites/Object_Sprites/" + objectName))); // Loads the image by finding the sprite's path. 
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return objs;
    }

    /* 
     * Adds all Snakes to the screen based on the amount and Colors given in the parameters. 
     * 
     * All Snakes are placed in either of the four edges of the screen.
     * 
     */
    
    private void addSnakes(int numOfPlayers, int numOfComputers, Color[] colors) 
    {
        Point[] startingPositions = { new Point(PIXEL_SIZE, PIXEL_SIZE), new Point(mainScreen.getWidth() - PIXEL_SIZE * 2, PIXEL_SIZE),
                new Point(PIXEL_SIZE, mainScreen.getHeight() - PIXEL_SIZE * 2),
                new Point(mainScreen.getWidth() - PIXEL_SIZE * 2, mainScreen.getHeight() - PIXEL_SIZE * 2) };
        
        Direction[] startingDirections = { Direction.EAST, Direction.SOUTH, Direction.NORTH, Direction.WEST };

        int[] player1Keys = { KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_A };
        int[] player2Keys = { KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT };
        int[] player3Keys = { KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_J };
        int[] player4Keys = { KeyEvent.VK_T, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_F };

        int[][] playerKeys = { { player1Keys[0], player1Keys[1], player1Keys[2], player1Keys[3] },
                { player2Keys[0], player2Keys[1], player2Keys[2], player2Keys[3] },
                { player3Keys[0], player3Keys[1], player3Keys[2], player3Keys[3] },
                { player4Keys[0], player4Keys[1], player4Keys[2], player4Keys[3] } };

        snakes = new ArrayList<>();

        if (numOfPlayers + numOfComputers > 4)
            return;

        for (int i = 0; i < numOfPlayers; i++) 
        {
            snakes.add(new PlayerSnake(startingPositions[i].x, startingPositions[i].y, positions, colors[i], PIXEL_SIZE,
                    playerKeys[i], startingDirections[i]));
        }
        
        for (int i = numOfPlayers; i < numOfComputers + numOfPlayers; i++) 
        {
            snakes.add(new AISnake(startingPositions[i].x, startingPositions[i].y, this, colors[i], PIXEL_SIZE,
                    startingDirections[i]));
        }
        
        numberOfSnakesAlive = snakes.size();
    }

    // Checks if a Snake has collided with the edge of the screen, or with another Point.
    private void checkCollisions() 
    {
        for (int i = 0; i < snakes.size(); i++) 
        {
            Snake snake = snakes.get(i);
            if ((hasCollided(snake) || isOutOfBounds(snake)) && snake.isAlive()) 
            {
                snake.setIsAlive(false);
                snakes.get(i).removeAllPositions(); // Removes all traces of the Snake
                numberOfSnakesAlive--;
            }
        }
    }
    
    /* 
     * Returns the Index of the only Snake alive.
     * 
     * If no Snake is alive, then it will return -1.
     */
    private int getSnakeAliveIndex()
    {
        for (int i = 0; i < snakes.size(); i++)
        {
            if (snakes.get(i).isAlive())
                return i;
        }
        
        return -1;
    }

    // Moves all the Snakes currently alive by retrieving their directionValues.
    private void update() 
    {
        for (Snake snake : snakes) 
        {
            if (snake.isAlive())
            {
                int[] arr = snake.getDirection().directionValues;
                snake.move(arr[0], arr[1]);
            }
        }
    }

    // Returns a boolean based upon if their is a PlayerSnake that is alive in the snakes ArrayList  
    private boolean playerSnakeAlive() 
    {
        for (Snake snake : snakes) 
        {
            if (snake instanceof PlayerSnake && snake.isAlive())
                return true;
        }
        return false;
    }
    
    // Used to remove all Points and other objects when a new round is started.
    private void removeAllItems()
    {
        for (int i = snakes.size()-1; i >=0; i--)
            snakes.remove(i);
        
        for (int i = obstacles.size()-1; i >= 0; i--)
            obstacles.remove(i);
        
        positions.clear();
        
        loadingTime = 0;
    }
}
