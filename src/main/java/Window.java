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

    private Window() {
        engine = createEngine();
        animation = new Animation();
        addMouseListener(new MyMouseAdapter());
        setWindowProperties();

        // on ESC key close frame
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
                //framename.setVisible(false);
            }
        });
    }

    private Engine createEngine () {
        Engine engine = new Engine();
        Container cp = getContentPane();
        cp.add(engine);
        engine.setPreferredSize(new Dimension(Properties.WIDTH, Properties.HEIGHT));
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

                elapsedTime += ((System.nanoTime() - start) / 1000000000.0) * FPS;
                start = System.nanoTime();

                if (elapsedTime >= 1) {
                    animation.incrementPoints();
                    repaint();
                    elapsedTime--;
                }

                try {
                    sleep(10);
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

            if (engine.running) {
                return;
            }

            Thread th = new Thread(engine);
            th.start();
            engine.running = true;
        }
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> new Window());
    }

}