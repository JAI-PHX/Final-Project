import javax.swing.*;

import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {

        setTitle("Flappy Bird Replica");

        setSize(800, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Layout manager with a JFrame
        setLayout(new BorderLayout());

        GamePanel gamePanel = new GamePanel();

        add(gamePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
