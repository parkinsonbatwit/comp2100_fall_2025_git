
package CheckersVsCode;

import java.util.ArrayList ;
import java.util.List ;

/**
 * 
 * @author lilie
 *
 * @version 1.0 2025-10-10 Initial implementation
 *
 *
 * @since 1.0
 */
public class CheckerBoard
    {
    
    // Initializes board, its size, and the pieces
    private Piece[][] board;
    
    private static final int SIZE = 8;
    public enum Player { WHITE, BLACK }
    
    private int whitePieces;
    private int blackPieces;
    
    private Player currentPlayer;
    
    public CheckerBoard(){
        this.board = new Piece[SIZE][SIZE];
        initializeBoard();
        this.currentPlayer = Player.WHITE;
    }
    
    private void initializeBoard() {
        this.whitePieces = 12;
        this.blackPieces = 12;
    
        // Iterates through rows and columns, helps decide where to place initial pieces
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(i < 3 && (i + j) % 2 != 0) {
                    this.board[i][j] = new Piece(Player.WHITE);
                } else if  (i > 4 && (i + j) % 2 != 0){
                    this.board[i][j] = new Piece(Player.BLACK);
                } else {
                    this.board[i][j] = null;
                }
            }
        }

    }
    
    public boolean makeMove(CheckerMove move) {      
        int fromRow = move.getFromRow();
        int fromCol = move.getFromCol();
        int toRow = move.getToRow();
        int toCol = move.getToCol();
        
        Piece piece = this.board[fromRow][fromCol];
        
        // returns the position after piece moves and removes old position
        this.board[toRow][toCol] = piece;
        this.board[fromRow][fromCol] = null;
        // checks for king pieces
        checkKings();
        
        // determines when to swap to next player
        switchPlayer();
        return true;
    }
    
    // checks if a piece is a king
    public void checkKings() {
        for (int col = 0; col < SIZE; col++) {
            if (this.board[SIZE - 1][col] != null && this.board[SIZE - 1][col].getColor() == Player.WHITE && !this.board[SIZE - 1][col].isKing()) {
                this.board[SIZE - 1][col].kingMe();
            }
        }
        
        for (int col = 0; col < SIZE; col++) {
            if (this.board[0][col] != null && this.board[0][col].getColor() == Player.BLACK && !this.board[0][col].isKing()) {
                this.board[0][col].kingMe();
            }
        }
    }
    
    // generates a list of all legal moves
    public List<CheckerMove> getLegalMoves(){
        List<CheckerMove> normalMoves = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                Piece piece = this.board[i][j];
                if (piece != null && piece.getColor() == this.currentPlayer) {
                    normalMoves.addAll(normalMoves(i, j));
                }
            }
        }
        return normalMoves;
    }
    
    private List<CheckerMove> normalMoves(int row, int col){
        List<CheckerMove> moves = new ArrayList<>();
        Piece piece = this.board[row][col];
        int direction = (piece.getColor() == Player.WHITE) ? -1 : 1;
        
        // checks normal moves
        checkNormalMove(moves, row, col, row + direction, col -1);
        checkNormalMove(moves, row, col, row + direction, col + 1);
        
        // checks backwards moves for king pieces
        if (piece.isKing()) {
            checkNormalMove(moves, row, col, -direction, -1);
            checkNormalMove(moves, row, col, -direction, 1);
    }
        return moves;
    }
    
    // helps to check if a move is possible
    private void checkNormalMove(List<CheckerMove> moves, int fromRow, int fromCol, int rowDir, int colDir) {
    int toRow = fromRow + rowDir;
    int toCol = fromCol + colDir;
    if (isValidCoordinate(toRow, toCol) && this.board[toRow][toCol] == null) {
        moves.add(new CheckerMove(fromRow, fromCol, toRow, toCol));
    }
    }
    
    // checks to see if a move coordinate is valid
    private static boolean isValidCoordinate(int row, int col) {
    return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
}
    
    public Player getCurrentPlayer() {
    return this.currentPlayer;
    }   
    
    private void switchPlayer() {
    this.currentPlayer = (this.currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
}
    public Player getWinner() {
    if (this.currentPlayer == Player.WHITE && getLegalMoves().isEmpty())
        return Player.BLACK;
    if (this.currentPlayer == Player.BLACK && getLegalMoves().isEmpty()) 
        return Player.WHITE;
    return null;
}
    
    // checks for game over
    public boolean isGameOver() {
        return getLegalMoves().isEmpty(); 
}
    
    
    
    
    
    
    }
