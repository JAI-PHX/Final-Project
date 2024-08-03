import java.awt.Graphics;

import java.awt.Rectangle;

//Parent class for objects
public abstract class GameObject {

    protected int x, y, width, height;

    public GameObject(int x, int y, int width, int height) {

        this.x = x;

        this.y = y;

        this.width = width;

        this.height = height;
    }

    //Update game status
    public abstract void update();

    public abstract void draw(Graphics g);

    //Draw bounders of the rectangle for the game
    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public int getWidth() {

        return width;
    }

    public int getHeight() {

        return height;
    }

    public Rectangle getBounds() {

        return new Rectangle(x, y, width, height);
    }
}
