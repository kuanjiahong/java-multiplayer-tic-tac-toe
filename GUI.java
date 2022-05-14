import java.awt.*;
import javax.swing.*;


/**
 * Class to implement to GUI for tic tac toe
 */
public class GUI {
    private JFrame frame = new JFrame(); // Frame object

    private JPanel mainPanel = new JPanel(); // Main panel

    private JPanel textPanel = new JPanel(); // Panel for text
    private JLabel textLabel = new JLabel(); // label object to display text

    private JPanel gamePanel = new JPanel(); // Pannel for buttons
    private JButton[] buttons = new JButton[9];

    private JPanel inputPanel = new JPanel(); // Panel for input section
    private JTextField inputField = new JTextField(10); // text area for input
    private JButton submit = new JButton("Submit"); // button to submit player name

    private JMenuBar menuBar = new JMenuBar();
    private JMenu help = new JMenu("Help");
    private JMenuItem instruction = new JMenuItem("Instruction");
    private JMenu control = new JMenu("Control");
    private JMenuItem exit = new JMenuItem("Exit");

    
    private JMenuBar createMenu() {

        instruction.addActionListener(new Instruction(frame));
        help.add(instruction);
        control.add(exit);

        menuBar.add(control);
        menuBar.add(help);

        return menuBar;
    }

    private void setUpMenuBar() {
        this.frame.setJMenuBar(createMenu());
    }

    private void setUpFrame() {
        this.frame.setTitle("Tic Tac Toe");
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.setSize(800,800);
        this.frame.setVisible(true);
    }

    private void setUpMainPanel() {
        this.mainPanel.setLayout(new GridLayout(3,1));
        setUpTextPanel();
        this.mainPanel.add(this.textPanel);
        setUpGamePanel();
        this.mainPanel.add(this.gamePanel);
        setUpInputPanel();
        this.mainPanel.add(this.inputPanel);
        this.frame.getContentPane().add(mainPanel);
    }

    private void setUpTextPanel() {
        this.textLabel.setText("Enter your name: ");;
        this.textPanel.add(textLabel);
    }

    private void setUpGamePanel() {
        this.gamePanel.setLayout(new GridLayout(3,3));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            gamePanel.add(buttons[i]);
        }
    }

    private void setUpInputPanel() {
        this.inputPanel.add(this.inputField);
        this.inputPanel.add(this.submit);
    }

    private void create() {
        setUpMenuBar();
        setUpMainPanel();
        setUpFrame();
    }

    /**
     * Show the GUI to the user
     */
    public void run() {
        create();
    }
    
    /**
     * Disable all the buttons in the game board
     */
    public void disableGamePanel() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }

    }

    /**
     * Enable buttons that has not been clicked
     */
    public void enableGamePanel() {
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().equals("X") || buttons[i].getText().equals("O")) {
                continue;
            } else { 
                buttons[i].setEnabled(true); 
            }
                
                
        }
    }


    /**
     * Get submit button
     * @return submit button
     */
    public JButton getSubmiButton() {
        return this.submit;
    }

    /**
     * Get text field
     * @return text field
     */
    public JTextField getTextField() {
        return this.inputField;
    }
    
    /**
     * Get text label
     * @return text label
     */
    public JLabel getTextLabel() {
        return this.textLabel;
    }

    /**
     * Get the frame of the GUI
     * @return the frame of the GUI
     */
    public JFrame getFrame() {
        return this.frame;
    }

    /**
     * Get the exit menu item
     * @return the exit menu item
     */
    public JMenuItem getExitItem() {
        return this.exit;
    }

    /**
     * Get all the buttons in the game board
     * @return all the buttons in the game board
     */
    public JButton[] getAllButtons() {
        return this.buttons;
    }


}
