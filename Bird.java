import java.awt.Graphics;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;

//GameObject and implementing Drawable in the Bird class extenion
public class Bird extends GameObject implements Drawable {

    private int velocity;

    private BufferedImage image;

    public Bird(int x, int y) {

        //Overloaded constructor
        super(x, y, 34, 24);

        this.velocity = 0;

        try {

            //Bird image
            image = ImageIO.read(new File("Screenshot 2024-07-25 130532.png"));

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    //Overridden method update
    @Override

    //Speed of the bird
    public void update() {

        velocity += 1;

        y += velocity;
    }

    public void flap() {

        velocity = -10;
    }

    //Overridden method update
    @Override
    public void draw(Graphics g) {

        //The hit box of the bird
        g.drawImage(image, x, y, width, height, null);
    }
}
