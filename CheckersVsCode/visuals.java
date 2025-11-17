package CheckersVsCode;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class visuals extends JPanel {

    final String PATH = "../img/";
    private BufferedImage backgroundImage;
    private JPanel gameWindow;

    public void ImageDisplayFrame(Piece[][] board) {
        gameWindow.setName("Checkers Vs");
        gameWindow.setSize(800, 800);

        // Set background image
        try {
            BufferedImage img = ImageIO.read(new File(PATH + "Board.png"));
            backgroundImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = backgroundImage.createGraphics();
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Draws individual pieces depending on their position
        for(int i = 0; i < 8; i++){
            for(int j = 0; i < 8; i++){
                DrawChecker panel = new DrawChecker(pieceInfo(board[i][j]), convCoords(i), convCoords(j)); 
                add(panel, BorderLayout.CENTER); // Add the panel to the JFrame
                setVisible(true);
            }
        }
    }

    // Converts piece info to string info
    public String pieceInfo(Piece p) {
        String type;
        String color;
        if(p.getColor() == CheckerBoard.Player.WHITE){
            color = "white";
        } else {
            color = "black";
        }
        if(p.isKing()){
            type = "king";
        } else {
            type = "checker";
        }
        return color + "_" + type;
    }


    // Converts game state coords to image coords
    public int convCoords(int coord){
        return coord * 100;
    }

}