/*
 * Released under MIT license.
 */
package CheckersVsCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.List;

import CheckersVsCode.CheckerBoard.Player;

public class checkersServer {

    //Local variable to determine whose turn it is.
    private static boolean whitesTurn;
    static boolean pOneIsWhite;
    static boolean pTwoIsWhite;
	static boolean whileConnectingOne = true;
	static boolean whileConnectingTwo = true;
	static boolean endGame;

    /*
     *  Waits for ping from both players, assigns player one IP and player two IP
     *  call assignColors()
     *  sets whitesTurn to be true.
     *  initializes game board
     *  listens until game ends
     */
public static void onStart(String[] args) {

    int port = 7879; // The port to listen on

    try (ServerSocket serverSocket = new ServerSocket(port)) {
        System.out.println("Checkers TCP Server started on port " + port);

        Socket clientSocket1 = null;
        Socket clientSocket2 = null;

        BufferedReader in1 = null;
        BufferedReader in2 = null;
        PrintWriter out1 = null;
        PrintWriter out2 = null;

        // --- WAIT FOR PLAYER 1 ---
        System.out.println("Waiting for Player 1...");
        clientSocket1 = serverSocket.accept();
        System.out.println("Player 1 connected: " + clientSocket1.getInetAddress());

        in1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
        out1 = new PrintWriter(clientSocket1.getOutputStream(), true);

        out1.println("CONNECTED AS PLAYER 1");

        // --- WAIT FOR PLAYER 2 ---
        System.out.println("Waiting for Player 2...");
        clientSocket2 = serverSocket.accept();
        System.out.println("Player 2 connected: " + clientSocket2.getInetAddress());

        in2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
        out2 = new PrintWriter(clientSocket2.getOutputStream(), true);

        out2.println("CONNECTED AS PLAYER 2");

        // --- At this point both players are connected ---
        System.out.println("Both players connected. Starting game...");

        // Example: read initial handshake messages
        String p1msg = in1.readLine();
        String p2msg = in2.readLine();

        System.out.println("Player1: " + p1msg);
        System.out.println("Player2: " + p2msg);

        // Echo back
        out1.println("ACK: " + p1msg);
        out2.println("ACK: " + p2msg);


		whitesTurn = true; // start white player
		CheckerBoard game = new CheckerBoard();
		assignColors();

        // ENTER GAME LOOP
        listen(in1, out1, in2, out2, game);

        if (endGame) {
            try {
                clientSocket1.close();
                clientSocket2.close();
            } catch (Exception e) {
                System.exit(2);
            }
        }

    } catch (IOException e) {
        System.err.println("I/O error: " + e.getMessage());
    }
}

    /*
     *  listen() Method:
     *  on receiving packet, decode
     *  if not players turn return and do nothing
     *  call validMove(Move) with Move being the move decoded
     *  if valid, call updateMoves and switch whitesTurn
     *  sendInformation
     */

private static void listen(BufferedReader in1, PrintWriter out1, BufferedReader in2, PrintWriter out2, CheckerBoard game) throws IOException {

	System.out.println("Starting listen loop...");

    while (!endGame) {

        // Determine whose turn it is
        BufferedReader currentIn = whitesTurn ? in1 : in2;
        PrintWriter currentOut = whitesTurn ? out1 : out2;

        BufferedReader waitingIn = whitesTurn ? in2 : in1; // Use this to buffer a move or something
        PrintWriter waitingOut = whitesTurn ? out2 : out1;

        // Tell players whose turn it is
        currentOut.println("YOUR_TURN");
        waitingOut.println("OPPONENT_TURN");

        // --- WAIT FOR CURRENT PLAYER MOVE ---
        String moveMessage = currentIn.readLine();

        if (moveMessage == null) {
            System.out.println("A player disconnected. Ending game.");
            endGame = true;
            break;
        }

        System.out.println((whitesTurn ? "White" : "Black") + " sent: " + moveMessage);

		while(!game.isGameOver()) {
        	List<CheckerMove> legalMoves = game.getLegalMoves();
        	System.out.println("\nIt's " + game.getCurrentPlayer() + "'s turn.");
        
        if (legalMoves.isEmpty()) {
       		System.out.println(game.getCurrentPlayer() + " has no legal moves. Game over!");
			endGame = true;
        	break;
    	}

    	try {
        System.out.print("Enter move (from_row from_col to_row to_col): ");
        int fromRow = findNum(moveMessage, 0);
        int fromCol = findNum(moveMessage, 1);
        int toRow = findNum(moveMessage, 2);
        int toCol = findNum(moveMessage, 3);
        
        CheckerMove chosenMove = new CheckerMove(fromRow, fromCol, toRow, toCol);
        
        if (legalMoves.stream().anyMatch(move ->
        move.getFromRow() == chosenMove.getFromRow() &&
        move.getFromCol() == chosenMove.getFromCol() &&
        move.getToRow() == chosenMove.getToRow() &&
        move.getToCol() == chosenMove.getToCol()))
    {
        game.makeMove(chosenMove);
    } else {
		// Send invalid move
        System.out.println("Invalid move. Try again.");
    }
    } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter four integers.");
        moveMessage = currentIn.readLine();
    }     
	}
    Player winner = game.getWinner();
    if (winner != null) {
    	System.out.println("\nGame Over! " + winner + " win!");
	} else {
    	System.out.println("\nGame Over! It's a draw.");
	}

    // ACK to both players
    out1.println("MOVE_PLAYED:" + moveMessage);
    out2.println("MOVE_PLAYED:" + moveMessage);

    // Switch turns
    whitesTurn = !whitesTurn;
}

}

    /*
     * assignColors() Method:
     *  randomly assigns colors to the two different players
     */
    static void assignColors() {
        if (Math.random() * 2 > 1) {
            pOneIsWhite = true;
            pTwoIsWhite = false;
        } else {
            pOneIsWhite = false;
            pTwoIsWhite = true;
        }
    }

	static int findNum(String s, int i){
		char[] arr = s.toCharArray();
		return arr[i];
	}

}
