
package CheckersVsCode;


/**
 * 
 * @author lilie
 *
 * @version 1.0 2025-10-11 Initial implementation
 *
 *
 * @since 1.0
 */
public class CheckerMove
    {
    // class specifies start and end coordinates
        public int fromRow, fromCol, toRow, toCol;
        
        public CheckerMove(int fromRow, int fromCol, int toRow, int toCol) {
            this.fromRow = fromRow;
            this.fromCol = fromCol;
            this.toRow = toRow;
            this.toCol = toCol;
        
        
        }
        
        // getter/setter methods for getting to and from a row/column
        public int getFromRow() {
            return fromRow;
        }
        
        public int getFromCol() {
            return fromCol;
        }
        
        public int getToRow() {
        return toRow;
    }
        
        public int getToCol() {
        return toCol;
    }
        
        
        }
    
   // end class CheckerMove