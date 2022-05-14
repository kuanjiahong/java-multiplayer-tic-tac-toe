import java.awt.event.*;
import javax.swing.*;

import javax.swing.JOptionPane;

/**
 * Event handler to show instruction
 */

public class Instruction implements ActionListener {
    private JFrame frame;
    /**
     * Constructor for Instruction Action listener
     * @param f - a JFrame argument
     */
    public Instruction(JFrame f) {
        this.frame = f;
    }

    /**
     * Constructor for Instruction Action listener
     */
    public Instruction() {}

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = "Some information about the game:\n" +
                         "Criteria for a valid move:\n" + 
                         "- The move is not occupied by any mark.\n" +
                         "- The move is made in the player's turn.\n" + 
                         "- The move is made within the 3 x 3 board.\n" +
                         "The game would continue and switch among the opposite player until it reaches" +
                         " either one of the following conditions:\n"+
                         "- Player 1 wins.\n" +
                         "- Player 2 wins.\n" +
                         "- Draw.";
        JOptionPane.showMessageDialog(frame, message);
    }

}
