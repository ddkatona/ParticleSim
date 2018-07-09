import javafx.geometry.HorizontalDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Thread.sleep;

/**
 * Creates an animation of an explosion when the user clicks on the screen.
 */
public class Window extends JFrame {

    private Animation animation;
    private Engine engine;
    private JLabel fpsCounter;
    private JLabel speedLabel;

    private double playSpeed = 1;
    private boolean play = true;

    private Window() {
        engine = createEngine();
        animation = new Animation();
        addMouseListener(new MyMouseAdapter());
        setWindowProperties();
        setButtons();

        startEngine();
    }

    private Engine createEngine () {
        Engine engine = new Engine();
        Container cp = getContentPane();
        cp.add(engine);
        engine.setPreferredSize(new Dimension(Properties.WIDTH, Properties.HEIGHT));
        engine.setLayout(new FlowLayout(FlowLayout.LEFT));

        // FPS counter
        fpsCounter = new JLabel("60");
        fpsCounter.setFont(new Font("Normal", Font.BOLD, 22));
        fpsCounter.setForeground(new Color(236, 240, 241));
        engine.add(fpsCounter);

        // Speed label
        /*speedLabel = new JLabel("1x");
        speedLabel.setFont(new Font("Normal", Font.BOLD, 22));
        speedLabel.setForeground(new Color(236, 240, 241));
        engine.add(speedLabel);*/

        return engine;
    }

    private void setWindowProperties () {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    /**
     * Contains the main loop of the animation.
     */
    private class Engine extends JPanel implements Runnable {

        private boolean running = false;

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            setBackground(Properties.BACK_COLOR);
            animation.paint(graphics);
        }

        /**
         * Begin the animation.
         */
        @Override
        public void run() {

            long start = System.nanoTime();
            double elapsedTime = 0.0;
            double FPS = 60.0;

            // Main animation loop.
            while (true) {

                double diffSec = (System.nanoTime() - start)/1000000000.0;
                elapsedTime += diffSec * FPS;
                String fpsString = String.format("%.0f", 1/diffSec);
                fpsCounter.setText(fpsString);
                start = System.nanoTime();

                if (elapsedTime >= 1/playSpeed) {

                    if(play) {
                        animation.incrementPoints();
                        repaint();
                    }

                    elapsedTime -= 1/playSpeed;
                }

                try {
                    sleep(8);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            super.mouseClicked(mouseEvent);


        }
    }

    private void startEngine() {
        if (engine.running) {
            return;
        }

        Thread th = new Thread(engine);
        th.start();
        engine.running = true;
    }

    private void restart() {
        SwingUtilities.invokeLater(() -> new Window());
    }


    private void setButtons() {
        // on ESC key close frame
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new AbstractAction() {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        // on P for fPs
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "FPS");
        getRootPane().getActionMap().put("FPS", new AbstractAction() {
            public void actionPerformed(ActionEvent e)
            {
                boolean prevState = fpsCounter.isVisible();
                fpsCounter.setVisible(!prevState);
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_K, 0), "StartStop");
        getRootPane().getActionMap().put("StartStop", new AbstractAction() {
            public void actionPerformed(ActionEvent e)
            {
                play = !play;
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_L, 0), "Faster");
        getRootPane().getActionMap().put("Faster", new AbstractAction() {
            public void actionPerformed(ActionEvent e)
            {
                playSpeed *= 1.25;
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_J, 0), "Slower");
        getRootPane().getActionMap().put("Slower", new AbstractAction() {
            public void actionPerformed(ActionEvent e)
            {
                playSpeed /= 1.25;
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "Restart");
        getRootPane().getActionMap().put("Restart", new AbstractAction() {
            public void actionPerformed(ActionEvent e)
            {
                restart();
            }
        });
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> new Window());
    }

}