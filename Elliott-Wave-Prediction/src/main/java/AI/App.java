package AI;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Hello world!
 *
 */
public class App 
{
    private static void buildGUI(){
        JFrame frame = new JFrame("Elliott Wave Predictor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        // Display the window.
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main( String[] args )
    {
        buildGUI();
    }
}
