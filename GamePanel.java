import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

import java.io.File;

import java.io.IOException;

import java.util.LinkedList;

import java.util.List;

import java.util.Map;

import javax.imageio.ImageIO;

//Main game panel handling the games logic and look
public class GamePanel extends JPanel implements ActionListener {

    private Bird bird;

    private LinkedList<Pipe> pipes;

    private Timer timer;

    private boolean gameOver;

    private boolean gameStarted;

    private Image backgroundImage;

    private JButton startOverButton;

    private JButton quitButton;

    private int score;

    private HighScoreManager highScoreManager;

    private List<String> playerNames;

    private String currentPlayer;

    private int currentPlayerIndex;

    public GamePanel() {

        //Custom block placement
        setLayout(null);

        bird = new Bird(50, 250);

        pipes = new LinkedList<>();

        timer = new Timer(20, this);

        gameOver = false;

        gameStarted = false;

        score = 0;

        highScoreManager = new HighScoreManager();

        playerNames = new LinkedList<>();

        try {

            backgroundImage = ImageIO.read(new File("flappy-bird-background-gecj5m4a9yhhjp87.jpg"));

        } catch (IOException e) {

            e.printStackTrace();
        }

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                    if (!gameStarted) {

                        startGame();

                    } else if (!gameOver) {

                        bird.flap();
                    }
                }
            }
        });

        setFocusable(true);

        timer.start();

        //JButton "Start over button"
        startOverButton = new JButton("Start Over");

        //Set button position and size
        startOverButton.setBounds(350, 320, 100, 30);

        startOverButton.setVisible(false);

        startOverButton.addActionListener(e -> resetGame());

        this.add(startOverButton);

        //JButton "quit button"
        quitButton = new JButton("Quit");

        //Set button position and size
        quitButton.setBounds(350, 360, 100, 30);

        quitButton.setVisible(false);

        //Eliminate code
        quitButton.addActionListener(e -> System.exit(0));

        this.add(quitButton);

        getPlayerNames();
    }

    private void getPlayerNames() {

        PlayerInputDialog dialog = new PlayerInputDialog((JFrame) SwingUtilities.getWindowAncestor(this));

        dialog.setVisible(true);

        if (dialog.isConfirmed()) {

            playerNames = dialog.getPlayerNames();

        } else {
            System.exit(0);
        }

        currentPlayerIndex = 0;

        currentPlayer = playerNames.get(currentPlayerIndex);
    }

    private void startGame() {

        gameStarted = true;

        repaint();

        timer.start();
    }

    private void nextPlayer() {

        if (currentPlayerIndex < playerNames.size() - 1) {

            currentPlayerIndex++;

            currentPlayer = playerNames.get(currentPlayerIndex);

            bird = new Bird(50, 250);

            pipes.clear();

            gameOver = false;

            gameStarted = false;

            score = 0;

            repaint();

        } else {
            displayWinner();

            startOverButton.setVisible(true);

            //Visualize quit button
            quitButton.setVisible(true);
        }
    }

    private void resetGame() {

        highScoreManager = new HighScoreManager();

        getPlayerNames();

        currentPlayerIndex = 0;

        currentPlayer = playerNames.get(currentPlayerIndex);

        startOverButton.setVisible(false);

        //Hide quit button when the game is repeated
        quitButton.setVisible(false);

        gameStarted = false;

        gameOver = false;

        score = 0;

        bird = new Bird(50, 250);

        pipes.clear();

        repaint();
    }

    private void displayWinner() {

        Map<String, Integer> highScores = highScoreManager.getHighScores();

        String winner = null;

        int highestScore = -1;

        for (String player : playerNames) {

            int playerScore = highScores.getOrDefault(player, 0);

            if (playerScore > highestScore) {

                highestScore = playerScore;

                winner = player;
            }
        }
        //JOptionPane "Show winner"
        JOptionPane.showMessageDialog(this, "Winner: " + winner + " with score: " + highestScore, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    //Overridden method update
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (backgroundImage != null) {

            g.drawImage(backgroundImage, 0, 0, null);
        }

        bird.draw(g);

        for (Pipe pipe : pipes) {

            pipe.draw(g);
        }

        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.BOLD, 24));

        g.drawString("Score: " + score, 10, 30);

        if (!gameStarted && !gameOver) {

            g.drawString("Press Space to Start", 300, 250);

        } else if (gameOver) {

            g.drawString("Game Over", 350, 300);
        }

        // Display player with the highest scores
        g.drawString("Players:", 10, 60);

        Map<String, Integer> highScores = highScoreManager.getHighScores();

        int y = 90;

        for (String player : playerNames) {

            int playerScore = highScores.getOrDefault(player, 0);

            g.drawString(player + ": " + playerScore, 10, y);

            y += 30;
        }
    }

    //Overridden method update
    @Override
    public void actionPerformed(ActionEvent e) {

        if (!gameOver && gameStarted) {

            bird.update();

            try {
                if (bird.getY() < 0 || bird.getY() + bird.getHeight() > getHeight()) {

                    throw new GameOverException("Bird hit the border!");
                }

                for (Pipe pipe : pipes) {

                    pipe.update();

                    if (pipe.collidesWith(bird)) {

                        throw new GameOverException("Bird hit the pipe!");
                    }

                    if (pipe.getX() + pipe.getWidth() < bird.getX() && !pipe.isPassed()) {

                        score++;

                        pipe.setPassed(true);
                    }
                }

                if (pipes.isEmpty() || pipes.getLast().getX() < 400) {

                    pipes.add(new Pipe(800));
                }

                if (pipes.getFirst().getX() < -100) {

                    pipes.removeFirst();
                }

                repaint();

            } catch (GameOverException ex) {

                gameOver = true;

                timer.stop();

                //JOptionPane "Show game over"
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Game Over", JOptionPane.ERROR_MESSAGE);

                highScoreManager.addScore(currentPlayer, score);

                revalidate();

                nextPlayer();
            }
        }
    }
}
