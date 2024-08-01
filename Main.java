import javax.swing.JFrame;

// Main code were the game is executed/played
public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Flappy Bird Replica");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(800, 700);

        frame.add(new GamePanel());

        frame.setVisible(true);
    }
}
