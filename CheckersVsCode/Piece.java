
package CheckersVsCode;


/**
 * 
 * @author lilie
 *
 * @version 1.0 2025-10-12 Initial implementation
 *
 *
 * @since 1.0
 */
public class Piece
    {
    // class represents a single piece on the board
        private final CheckerBoard.Player color;
        private boolean isKing ;
        
        public Piece(CheckerBoard.Player color) {
        this.color = color;
        this.isKing = false;
    }
        
        public CheckerBoard.Player getColor() {
        return color;
    }
        
        public boolean isKing() {
        return isKing;
    }
        
        public void kingMe() {
        this.isKing = true;
    }
    }
   // end class Piece