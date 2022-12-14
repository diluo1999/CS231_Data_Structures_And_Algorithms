/*
  Originally written by Bruce A. Maxwell a long time ago.
  Updated by Brian Eastwood and Stephanie Taylor more recently

  Creates a window using the JFrame class.

  Creates a drawable area in the window using the JPanel class.

  The JPanel calls the Landscape's draw method to fill in content, so the
  Landscape class needs a draw method.
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Displays a Board graphically using Swing.  The Board can be
 * displayed at any scale factor.  The only change from project 2
 * is the use of the Board class instead of the Landscape class.
 * @author bseastwo
 */
public class LandscapeDisplay extends JFrame
{
    // protected JFrame win;
    protected Landscape scape; 
    private LandscapePanel canvas;
    private int scale;

    /**
     * Initializes a display window for a Landscape.
     * @param scape the Landscape to display
     * @param scale controls the relative size of the display
     */
    public LandscapeDisplay(Landscape scape, int scale)
    {
        // // setup the window
        // this.win = new JFrame("Hunt The Wumpus");
        // this.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super("Hunt The Wumpus");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.scape = scape;
        this.scale = scale;
        // create a panel in which to display the Landscape
        this.canvas = new LandscapePanel( this.scape.getWidth()*scale,
					  this.scape.getHeight()*scale );

        // add the panel to the window, layout, and display
        this.add(this.canvas, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Saves an image of the display contents to a file.  The supplied
     * filename should have an extension supported by javax.imageio, e.g.
     * "png" or "jpg".
     *
     * @param filename  the name of the file to save
     */
    public void saveImage(String filename)
    {
        // get the file extension from the filename
        String ext = filename.substring(filename.lastIndexOf('.') + 1, filename.length());

        // create an image buffer to save this component
        Component tosave = this.getRootPane();
        BufferedImage image = new BufferedImage(tosave.getWidth(), tosave.getHeight(), 
                                                BufferedImage.TYPE_INT_RGB);

        // paint the component to the image buffer
        Graphics g = image.createGraphics();
        tosave.paint(g);
        g.dispose();

        // save the image
        try
	    {
		ImageIO.write(image, ext, new File(filename));
	    }
        catch (IOException ioe)
	    {
		System.out.println(ioe.getMessage());
	    }
    }

    /**
     * This inner class provides the panel on which Landscape elements
     * are drawn.
     */
    private class LandscapePanel extends JPanel
    {
        /**
         * Creates the panel.
         * @param width     the width of the panel in pixels
         * @param height        the height of the panel in pixels
         */
        public LandscapePanel(int width, int height)
        {
	    super();
	    this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.white);
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen.  The supplied Graphics
         * object is used to draw.
         * 
         * @param g     the Graphics object used for drawing
         */
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            scape.draw( g, scale);
        } // end paintComponent
        
    } // end LandscapePanel

    public void repaint() {
	   Graphics g = canvas.getGraphics();
       this.requestFocus();
       canvas.paintComponent( g );
    }


    // test function that creates a new LandscapeDisplay and populates it with 200 agents.
    public static void main(String[] args) throws InterruptedException {
        Landscape scape = new Landscape(10,10);
        Vertex v = new Vertex(8,3);
        Vertex a = new Vertex( 4, 4);
        a.setVisible(true);
        v.setVisible(true);
        scape.addAgent(a);
        scape.addAgent(v);
        LandscapeDisplay display = new LandscapeDisplay(scape, 64);

        display.repaint();
    }
}
