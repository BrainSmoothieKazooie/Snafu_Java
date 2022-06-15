package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import classes.AISnake;
import classes.Direction;
import classes.PlayerSnake;
import classes.Snake;
import classes.Sprite;

@SuppressWarnings("serial")
public class GameScreen extends JPanel implements ScreenActions 
{
    private HashMap<Point, Rectangle> positions;
    public static final int PIXEL_SIZE = 16;
    private ArrayList<Snake> snakes;
    private ArrayList<Sprite> objects;
    private int numberOfSnakesAlive;
    private MainScreen mainScreen;
    private long loadingTime;
    private final long LOADING_TIME = 20;
    private int numberOfRounds;
    private int numberOfObjects;
    private int numberOfPlayers;
    private int numberOfComputers;
    private Color[] colors;

    public GameScreen(MainScreen screen, int speed, int objectCount, int playerCount, int computerCount,
                      Color[] colors, int numOfRounds) {
        mainScreen = screen;
        setName("game screen");
        setBackground(Color.black);
        this.setBounds(this.getX(), this.getY(), mainScreen.getWidth(), mainScreen.getHeight());

        positions = new HashMap<>();
        numberOfRounds = numOfRounds;
        numberOfPlayers = playerCount;
        numberOfComputers = computerCount;
        numberOfObjects = objectCount;
        this.colors = colors;

        screen.timer.setDelay(1000 / speed);
        
        initializeScreen();
    }

    private void addObjects(int numOfObjects) {
        ArrayList<Image> objectSprites = loadAllObjectSprites();
        objects = new ArrayList<>();

        for (int i = 1; i <= numOfObjects; i++) {
            
            int randomSize = (int)(Math.random() * 4)+3;
            int randomIndex = (int) (Math.random() * objectSprites.size());
            Sprite sprite = new Sprite(objectSprites.get(randomIndex), this);
            sprite.resize(PIXEL_SIZE*randomSize, PIXEL_SIZE*randomSize, positions);
            objects.add(sprite);
        }
    }

    private ArrayList<Image> loadAllObjectSprites() {
        ArrayList<Image> objs = new ArrayList<>();
        File sourceFolder = new File("src/Sprites/Object_Sprites");

        for (File musicFile : sourceFolder.listFiles()) {
            objs.add(new ImageIcon(musicFile.getPath()).getImage());
        }
        return objs;
    }

    private void addSnakes(int numOfPlayers, int numOfComputers, Color[] colors) {
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

        for (int i = 0; i < numOfPlayers; i++) {
            snakes.add(new PlayerSnake(startingPositions[i].x, startingPositions[i].y, positions, colors[i], PIXEL_SIZE,
                    playerKeys[i], startingDirections[i]));
        }
        for (int i = numOfPlayers; i < numOfComputers + numOfPlayers; i++) {
            snakes.add(new AISnake(startingPositions[i].x, startingPositions[i].y, this, colors[i], PIXEL_SIZE,
                    startingDirections[i]));
        }
        numberOfSnakesAlive = snakes.size();
    }

    private void checkCollisions() {
        for (int i = 0; i < snakes.size(); i++) {
            Snake snake = snakes.get(i);
            if ((snake.hasCollided(snake) || isOutOfBounds(snake)) && snake.isAlive()) 
            {
                snake.setIsAlive(false);
                snakes.get(i).removeAllPositions();
                numberOfSnakesAlive--;
            }
        }
    }

    public boolean isOutOfBounds(Rectangle rect) {
        return (rect.x < 0 || rect.x > getWidth() - rect.width || rect.y < 0 || rect.y > getHeight() - rect.height);
    }

    public boolean isOutOfBounds(Snake snake) {
        Rectangle collider = snake.getCollider();
        return (collider.x < 0 || collider.x > getWidth() - collider.width || collider.y < 0
                || collider.y > getHeight() - collider.height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (numberOfSnakesAlive > 1) {
            for (Snake snake : snakes) 
            {
                if (snake.isAlive())
                    snake.draw((Graphics2D) g);
            }
            for (Sprite object : objects) {
                object.draw((Graphics2D) g);
            }
        } 
        else 
        {
            String temp = "";
            int winnerIndex = getSnakeAliveIndex();
            
            if (winnerIndex == -1)
            {
                temp = "draw";
            }
            else
            {
                temp = "Snake #" + (winnerIndex+1) + " wins!";
            }
            Graphics2D graphics = (Graphics2D) g;
            
            Snake winnerSnake = snakes.get(winnerIndex);
            winnerSnake.draw(graphics);
        
            graphics.setColor(Color.white);
            graphics.setFont(new Font("impact", Font.PLAIN, 40));
            graphics.drawString(temp, getWidth() / 2 - temp.length()*(PIXEL_SIZE/4), getHeight() / 2);
        }
    }
    
    private int getSnakeAliveIndex()
    {
        for (int i = 0; i < snakes.size(); i++)
        {
            if (snakes.get(i).isAlive())
                return i;
        }
        
        return -1;
    }

    private void update() {
        for (Snake snake : snakes) {
            int[] arr = snake.getDirection().directionValues;
            snake.move(arr[0], arr[1]);
        }
    }

    public HashMap<Point, Rectangle> getPositions() {
        return positions;
    }

    @Override
    public void step() 
    {
        if (numberOfSnakesAlive > 1 && !(loadingTime <= LOADING_TIME)) 
        {
            update();
            checkCollisions();
        }
        repaint();
        loadingTime++;
    }

    private boolean playerSnakeAlive() {
        for (Snake snake : snakes) {
            if (snake instanceof PlayerSnake)
                return true;
        }
        return false;
    }

    @Override
    public void initializeScreen() 
    {
        addSnakes(numberOfPlayers, numberOfComputers, colors);
        addObjects(numberOfObjects);
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if ((numberOfSnakesAlive <= 1 && e.getKeyCode() == KeyEvent.VK_ENTER) || 
            (e.getKeyCode() == KeyEvent.VK_ENTER && !playerSnakeAlive())) 
        {
            numberOfRounds--;
            if (numberOfRounds > 0)
            {
                removeAllItems();
                initializeScreen();
            }
            
            else
            {
                mainScreen.remove(this);
                mainScreen.timer.setDelay(1000 / 60);
                mainScreen.switchScreen("options screen");
            }
        }

        for (Snake snake : snakes) 
        {
            if (snake instanceof PlayerSnake) 
            {
                PlayerSnake player = (PlayerSnake) snake;
                player.input(e, true);
            }
        }
    }
    
    private void removeAllItems()
    {
        for (int i = snakes.size()-1; i >=0; i--)
        {
            snakes.remove(i);
        }
        
        for (int i = objects.size()-1; i >= 0; i--)
        {
            objects.remove(i);
        }
        
        positions.clear();
        
        loadingTime = 0;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (Snake snake : snakes) {
            if (snake instanceof PlayerSnake) {
                PlayerSnake player = (PlayerSnake) snake;
                player.input(e, false);
            }
        }
    }
}
