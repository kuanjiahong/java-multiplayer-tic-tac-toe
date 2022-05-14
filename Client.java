import java.io.*;
import java.net.*;


import javax.swing.*;
import java.awt.event.*;

/**
 * Class for player to connect to the server
 */
public class Client {
    Socket sock;
    PrintWriter writer;
    InputStreamReader streamReader;
    BufferedReader reader;

    private String playerName;
    private String playerSign;

    private JTextField textField;
    private JLabel textLabel;
    private JButton submitButton;

    private JFrame frame;

    private JMenuItem exit;

    private JButton[] buttons;


    Board gameBoard = new Board();

    GUI gui;

    public static void main(String[] args) {
        Client client = new Client();
        client.go();
    }

    /**
     * Start the client
     */
    public void go() {
        gui = new GUI();
        gui.run();
        frame = gui.getFrame();
        frame.addWindowListener(new Close());
        textField = gui.getTextField();
        textLabel = gui.getTextLabel();
        submitButton = gui.getSubmiButton();
        submitButton.addActionListener(new Submit());

        buttons = gui.getAllButtons();
        initButtons();

        exit = gui.getExitItem();
        exit.addActionListener(new Exit());
        

        gui.disableGamePanel();

        try {
            sock = new Socket("127.0.0.1", 5000);
            writer = new PrintWriter(sock.getOutputStream(), true);
            streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);

            writer.println("Joined");

            String command;

            while ((command = reader.readLine()) != null) {
                System.out.println("Output from server: " + command);

                if (command.equals("NE")) {
                    gui.disableGamePanel();
                    submitButton.setEnabled(false);
                    textField.setEnabled(false);
                    System.out.println("Not enough players. Waiting for more to join.");
                }

                if (command.equals("ENO")) {
                    submitButton.setEnabled(true);
                    textField.setEnabled(true);
                    System.out.println("Two player has joined.");
                }

                if (command.equals("More than two")) {
                    String message = "Maximum player reached. Please close the window and try again later.";
                    JOptionPane.showMessageDialog(frame, message);
                    gui.disableGamePanel();
                    submitButton.setEnabled(false);
                    break;
                }


                if (command.equals("1")) {
                    playerSign = "X";
                    System.out.println("Your sign is " + playerSign);
                    frame.setTitle("Tic Tac Toe-Player: " + playerName);
                    submitButton.setEnabled(false);
                } 
                if (command.equals("2") && playerSign == null) {
                    playerSign = "O";
                    System.out.println("Your sign is " + playerSign);
                    frame.setTitle("Tic Tac Toe-Player: " + playerName);
                    gui.disableGamePanel();
                    submitButton.setEnabled(false);
                }

                if (command.startsWith("2") && playerSign == "X") {
                    textLabel.setText("Please make a move.");
                    gui.enableGamePanel();
                }

                if (command.startsWith("2") && playerSign == "O") {
                    textLabel.setText("Wait for your opponent to make a move");
                }

                if (command.startsWith("M")) {
                    gameBoard.updateBoard(command.substring(1,2), Integer.parseInt(command.substring(2,3)));
                    updateButtons(command.substring(1,2), Integer.parseInt(command.substring(2,3)));
                }

                if (command.equals("O turn") && playerSign == "O") {
                    textLabel.setText("Your opponent has moved, now is your turn");
                    gui.enableGamePanel();
                } else if (command.equals("X turn") && playerSign == "X") {
                    textLabel.setText("Your opponent has moved, now is your turn");
                    gui.enableGamePanel();
                }

                if (command.equals("D")) {
                    JOptionPane.showMessageDialog(frame, "Draw.");
                    break;
                }

                if (command.equals("W")) {
                    JOptionPane.showMessageDialog(frame, "Congratulation. You win!");
                    break;
                }
                
                if (command.equals("L")) {
                    JOptionPane.showMessageDialog(frame, "You lose.");
                    break;
                }

                if (command.equals("Q")) {
                    System.out.println("One player left the game.");
                    JOptionPane.showMessageDialog(frame, "Game Ends. One of the player left");
                    gui.disableGamePanel();
                    submitButton.setEnabled(false);
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initButtons() {
        for (int i = 0; i < 9; i++) {
            buttons[i].addActionListener(new Move());
        }
    }

    private void updateButtons(String sign, int index) {
        buttons[index].setText(sign);
        buttons[index].setEnabled(false);
    }

    /**
     * Event handler when player makes a move
     */
    class Move implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 9; i++) {
                if (e.getSource() == buttons[i]) {
                    buttons[i].setText(playerSign);
                    buttons[i].setEnabled(false);
                    writer.println("M"+playerSign+i);
                    textLabel.setText("Valid move, wait for your opponent.");
                }
            }
            gui.disableGamePanel();
        }
    }


    /**
     * Event handler when submit button is clicked
     */
    class Submit implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = textField.getText();
            if (name.equals("")) {
                System.out.println("Input field is empty.");
            } else {
                textLabel.setText("WELCOME " + textField.getText());
                playerName = textField.getText();
                textField.setEditable(false);;
                writer.println("N"+playerName);
                System.out.println("You have typed in " + name);
            }
        }
    }

    /**
     * Event handler to handle when window is closed
     */
    class Close implements WindowListener {
        @Override
        public void windowActivated(WindowEvent e) {}
        @Override
        public void windowClosed(WindowEvent e) {}
        @Override
        public void windowOpened(WindowEvent e) {}
        @Override
        public void windowDeiconified(WindowEvent e) {}
        @Override
        public void windowIconified(WindowEvent e) {}
        @Override
        public void windowDeactivated(WindowEvent e) {}
        @Override
        public void windowClosing(WindowEvent e) {
            try {
                writer.println("Q");
                reader.close();
                streamReader.close();
                sock.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }
    

    /**
     * Event handler when exit menu item is clicked
     */
    class Exit implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                writer.println("Q");
                reader.close();
                streamReader.close();
                sock.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }
}
