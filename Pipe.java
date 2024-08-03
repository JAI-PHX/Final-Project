import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.Rectangle;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import java.util.Random;

import javax.imageio.ImageIO;

//GameObject child class
public class Pipe extends GameObject implements Drawable {

    private int gap, yTop, yBottom;

    private BufferedImage imageTop, imageBottom;

    //Spawns in new cactus when the user passes through previous cactus's
    private boolean passed;

    public Pipe(int x) {

        super(x, 0, 60, 400);

        //Stop spawning new cactus's when player does not make it though the cactus's
        this.passed = false;

        //Generate random gaps in between the cactus
        Random rand = new Random();

        //Generate gap between 100 to 250
        this.gap = rand.nextInt(150) + 100;

        this.yTop = -rand.nextInt(height / 2);

        this.yBottom = yTop + height + gap;

        try {
            imageTop = ImageIO.read(new File("Cactus flipped.png"));

            imageBottom = ImageIO.read(new File("Cactus.png"));

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        x -= 5;
    }

    @Override
    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        //Implement upside down cactus
        g2d.drawImage(imageBottom, x, yBottom, width, height, null);

        //Implement upright cactus
        g2d.drawImage(imageTop, x, yTop, width, height, null);
    }

    public boolean collidesWith(Bird bird) {

        return getBoundsTop().intersects(bird.getBounds()) || getBoundsBottom().intersects(bird.getBounds());
    }

    public Rectangle getBoundsTop() {
        return new Rectangle(x, yTop, width, height);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle(x, yBottom, width, height);
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
