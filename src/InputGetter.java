import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InputGetter extends JPanel {

    KeyListener listener;

    public InputGetter(MazeUI ui) {
        //initializer with a key listner and tell it to connect with the UI window
        listener = new MyKeyListener(ui);
        addKeyListener(listener);
        setFocusable(true);
    }

//below is used for debugging the key listener
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("keystrokes broke again :( ");
//        KeyboardExample keyboardExample = new KeyboardExample();
//        frame.add(keyboardExample);
//        frame.setSize(200, 200);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }

    public class MyKeyListener implements KeyListener {
        MazeUI mazeUI;

        public MyKeyListener(MazeUI ui) {
            mazeUI = ui;
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            String key = KeyEvent.getKeyText(e.getKeyCode());
            //System.out.println("keyPressed = "+KeyEvent.getKeyText(e.getKeyCode()));

            switch (key) {
                case "W":
                    //System.out.println("\t\t\t\tCalling North\t\tmove command is " + key);
                    try {
                        mazeUI.updateComponents(new int[]{-1, 0}, "o-N");
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "A":
                    //System.out.println("\t\t\t\tCalling a\\t\\tmove command is " + key);
                    try {
                        mazeUI.updateComponents(new int[]{0, -1}, "o-W");
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "S":
                    //System.out.println("\t\t\t\tCalling s\\t\\tmove command is " + key);
                    try {
                        mazeUI.updateComponents(new int[]{1, 0}, "o-S");
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "D":
                    //System.out.println("\t\t\t\tCalling d\\t\\tmove command is " + key);
                    try {
                        mazeUI.updateComponents(new int[]{0, 1}, "o-E");
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
        }

    }
}