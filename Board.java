import java.util.ArrayList;

/**
 * Class for tic tac toe game board
 */
public class Board {
    private String[] board = new String[9];
    private int count = 0;
    private ArrayList<String> players = new ArrayList<String>();


    /**
     * Constructor for the game board
     */
    public Board() {
        for (int i = 0; i < 9; i++) {
            board[i] = "Empty";
        }
    }


    /**
     * Add player to the array list
     * @param playerName - the player's name
     */
    public synchronized void addPlayer(String playerName) {
        players.add(playerName);
    }

    /**
     * Update the board according to player's move
     * @param sign the player's sign (O or X)
     * @param index the location of the board
     */
    public synchronized void updateBoard(String sign, int index) {
        board[index] = sign;
        this.count++;
    }

    /**
     * Print out the game board
     */
    public synchronized void printBoard() {
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                System.out.println();
            }
            System.out.print(board[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

     /**
     * Check for winner
     * @param sign the sign of the player
     * @return true if the sign wins. false if there isn't
     */
    public synchronized boolean checkWinner(String sign) {
        // Row
        if      (board[0].equals(sign) && board[1].equals(sign) && board[2].equals(sign)) return true;
        else if (board[3].equals(sign) && board[4].equals(sign) && board[5].equals(sign)) return true;
        else if (board[6].equals(sign) && board[7].equals(sign) && board[8].equals(sign)) return true;
        // Column
        else if (board[0].equals(sign) && board[3].equals(sign) && board[6].equals(sign)) return true;
        else if (board[1].equals(sign) && board[4].equals(sign) && board[7].equals(sign)) return true;
        else if (board[2].equals(sign) && board[5].equals(sign) && board[8].equals(sign)) return true;
        // Diagonal
        else if (board[0].equals(sign) && board[4].equals(sign) && board[8].equals(sign)) return true;
        else if (board[6].equals(sign) && board[4].equals(sign) && board[2].equals(sign)) return true;

        return false;
    }

    /**
     * Check if the game ends in draw
     * @return true if game is draw. false if not
     */
    public synchronized boolean isDraw() {
        if (count >= 9) {
            System.out.println("Game ends in Draw.");
            return true;
        }
        System.out.println("There still empty space left.");
        return false;
    }

    /**
     * Get number of player
     * @return number of player
     */
    public synchronized int getNoOfPlayers() {
        return this.players.size();
    }

    /**
     * Reset the number of player
     */
    public synchronized void resetPlayer() {
        players.clear();
    }

    /**
     * Reset the game board and the counter to 0
     */
    public synchronized void resetBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = "Empty";
        }
        count = 0;
    }
    
}
