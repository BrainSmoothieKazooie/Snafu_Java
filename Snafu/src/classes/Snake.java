package classes;

import javax.swing.*;
import java.util.*;
import java.awt.Image;
import java.awt.Graphics2D;

public abstract class Snake extends Sprite
{
  private int tailLength;
  private int health;
  
  public Snake()
  {
    tailLength = 0;
    health = 1;
  }

  public Snake(int tailLength)
  {
    this.tailLength = tailLength;
    health = 1;
  }
  
  public Snake(int tailLength, Image img)
  {
    this.tailLength = tailLength;
    health = 1;
    setImage(img);
  }

  public Snake(Image img)
  {
    this.tailLength = 0;
    health = 1;
    setImage(img);
  }

  public void draw(Graphics2D graphics)
  {
    
  }
    
  protected void drawCollider(Graphics2D graphics)
  {
    
  }
    
  public boolean isColliding(Sprite sprite)
  {
     return false;
  }

  public int getHealth()
  { return health; }
  
  public void setHealth(int h)
  { health = h; }

  public abstract void move(int dx, int dy);
}
