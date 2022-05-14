import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Class to implement the server
 */
public class Server {
    ServerSocket serverSock;
    Board gameBoard = new Board();
    ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();

    public static void main(String[] args) {
        Server server = new Server();
        server.go();
    }

    /**
     * Start the server
     */
    public void go() {
        try {
            serverSock = new ServerSocket(5000);
            System.out.println("Server is running at port 5000...");
            while (true) {
                Socket sock = serverSock.accept();
                System.out.println("Server is connected to client");
                ClientHandler clientHandler = new ClientHandler(sock);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Class to handle client
     */
    class ClientHandler implements Runnable {
        private Socket sock;
        private boolean result;
        public ClientHandler(Socket sock) {
            this.sock = sock;
        }
        @Override
        public void run() {
            try {

                PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);

                // There already two player playing the game
                if (writers.size() >= 2) {
                    writer.println("More than two");
                    return;
                }

                writers.add(writer);

                InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                String command;
                while ((command = reader.readLine()) != null) {
                    System.out.println("Command from client: " + command);
                    System.out.println("Before command: ");
                    gameBoard.printBoard();

                    // Player X makes a move
                    if (command.startsWith("MX")) {
                        gameBoard.updateBoard(command.substring(1,2), Integer.parseInt(command.substring(2,3)));
                        System.out.println("After command: ");
                        gameBoard.printBoard();

                        writeToAllWriters(command);

                        result = gameBoard.checkWinner(command.substring(1, 2));

                        if (result == true) {
                            writer.println("W");
                            gameBoard.resetBoard();
                            for (PrintWriter eachWriter: writers) {
                                if (eachWriter.equals(writer)) continue;
                                else eachWriter.println("L");
                            }
                            
                        } else {
                            result = gameBoard.isDraw();
                            if (result == true) {
                                writeToAllWriters("D");
                                gameBoard.resetBoard();
                            } else {
                                writeToAllWriters("O turn");
                            }
                            
                        }


                    }
                    // Player O makes a move 
                    else if (command.startsWith("MO")) {
                        gameBoard.updateBoard(command.substring(1,2), Integer.parseInt(command.substring(2,3)));
                        System.out.println("After command: ");
                        gameBoard.printBoard();

                        writeToAllWriters(command);

                        result = gameBoard.checkWinner(command.substring(1, 2));

                        if (result == true) {
                            writer.println("W");
                            gameBoard.resetBoard();
                            for (PrintWriter eachWriter: writers) {
                                if (eachWriter.equals(writer)) continue;
                                else eachWriter.println("L");
                            }
                        } else {
                            result = gameBoard.isDraw();
                            if (result == true) {
                                writeToAllWriters("D");
                                gameBoard.resetBoard();
                            } else {
                                writeToAllWriters("X turn");
                            }
                        }
                    } 
                    // Check number of connections.
                    else if (command.equals("Joined")) {
                        if (writers.size() < 2) {
                            writer.println("NE");
                        } else {
                            writeToAllWriters("ENO");
                        }

                    }
                    // A name has been inputted
                    else if (command.startsWith("N")) {
                        gameBoard.addPlayer(command.substring(1));
                        writeToAllWriters(command);

                        if (gameBoard.getNoOfPlayers() == 1) {
                            writer.println("1");
                        } else if (gameBoard.getNoOfPlayers() == 2) {
                            for (PrintWriter eachWriter : writers) {
                                eachWriter.println("2");
                            }
                        }
                    }
                    // A player quit the game
                    else if (command.equals("Q")) {
                        writer.println("Q");
                        System.out.println("A client is disconnted from the server.");
                        writeToAllWriters("Q");
                        System.out.println("Reset player...");
                        gameBoard.resetPlayer();
                        System.out.println("Reset writers...");
                        writers.clear();
                        break;
                    }

                    System.out.println("Server is broadcasting to all client");

                }
                System.out.println("A client socket is closed.");
                System.out.println("The client thread ended.");
                sock.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void writeToAllWriters(String message) {
        for (PrintWriter eachWriter : writers) {
            eachWriter.println(message);
        }
    }
}