package CheckersVsCode;
    import javax.swing.*;
    import java.awt.*;
    import java.awt.image.BufferedImage;
    import javax.imageio.ImageIO;
    import java.io.File;
    import java.io.IOException;

    public class DrawChecker extends JPanel {
        final String PATH = "../img/";
        private BufferedImage image;
        private int imageX;
        private int imageY;

        public DrawChecker(String imageName, int x, int y) {
            try {
                image = ImageIO.read(new File(PATH + imageName + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.imageX = x;
            this.imageY = y;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, imageX, imageY, this);
            }
        }

        public void setImagePosition(int x, int y) {
            this.imageX = x;
            this.imageY = y;
            repaint();
        }
    }