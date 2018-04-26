/*
 * SierpinskiTriangle.java
 * 2/28/12
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A program that draws a Sierpinski triangle of various depths.
 * 
 * @author YOUR_NAME_HERE
 * @author YOUR_NAME_HERE
 */
public class SierpinskiTriangle extends JPanel implements ChangeListener {

    /** Panel dimension. */
    private static final Dimension PANEL_DIM = new Dimension(640, 480);

    /** Slider to specify the number of nested triangles. */
    private JSlider depthSlider;

    /** Maximum order of Koch snowflake. */
    private static final int MAX_DEPTH = 10;

    /** Minimum order of Koch snowflake. */
    private static final int MIN_DEPTH = 1;

    /** Initial order of Koch snowflake. */
    private static final int INIT_DEPTH = 1;

    /**
     * Run the program.
     * 
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        JFrame frame = new JFrame("Sierpinski Triangle");
        frame.getContentPane().add(new SierpinskiTriangle());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Initialize the window contents.
     */
    public SierpinskiTriangle() {
        JLabel depthLabel = new JLabel("Depth:");
        depthSlider = new JSlider(JSlider.HORIZONTAL, MIN_DEPTH, MAX_DEPTH,
                INIT_DEPTH);
        depthSlider.addChangeListener(this);
        add(depthLabel);
        add(depthSlider);
        setBackground(Color.white);
        setPreferredSize(PANEL_DIM);
    }

    /**
     * Paint a Sierpinski triangle to the window.
     * 
     * @param g graphics object to draw to
     */
    public final void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Point2D.Double point1 = new Point2D.Double(getWidth() / 2.0, 0.0);
        Point2D.Double point2 = new Point2D.Double(0.0, getHeight());
        Point2D.Double point3 = new Point2D.Double(getWidth(), getHeight());
        int depth = depthSlider.getValue();
        sierpinski(g, point1, point2, point3, depth, 0);
    }

    /**
     * Draw a Sierpinski triangle.
     * 
     * @param g the graphics object to draw to
     * @param point1 the first vertex of the triangle to draw
     * @param point2 the second vertex of the triangle to draw
     * @param point3 the third vertex of the triangle to draw
     * @param depth the number of nested triangles
     */
    public void sierpinski(final Graphics g, final Point2D point1, final Point2D point2, final Point2D point3, final int depth, int currentDepth)
    {
        //quit if it's past the current depth
        if(currentDepth > depth)
            return;

        //base triangle
        if(currentDepth == 0)
        {
            g.setColor(Color.GREEN);
            g.fillPolygon(new int[]{(int)point1.getX(),(int)point2.getX(),(int)point3.getX()},new int[]{(int)point1.getY(),(int)point2.getY(),(int)point3.getY()},3);
        }

        //create 3 midpoints
        Point2D.Double pointW = new Point2D.Double(.5*point1.getX()+.5*point2.getX(),.5*point1.getY()+.5*point2.getY());
        Point2D.Double pointE = new Point2D.Double(.5*point2.getX()+.5*point3.getX(),.5*point2.getY()+.5*point3.getY());
        Point2D.Double pointS = new Point2D.Double(.5*point3.getX()+.5*point1.getX(),.5*point3.getY()+.5*point1.getY());

        //create middle triangle
        int[] midXVals = new int[] {(int)pointW.getX(),(int)pointE.getX(),(int)pointS.getX()};
        int[] midYVals = new int[] {(int)pointW.getY(),(int)pointE.getY(),(int)pointS.getY()};
        g.setColor(Color.WHITE);
        g.fillPolygon(midXVals,midYVals,3);

        //make 3 sierpinski triangles out of the newly created top, bottom, and left triangles
        sierpinski(g,point1,pointW,pointS,depth,currentDepth+1);
        sierpinski(g,pointW,point2,pointE,depth,currentDepth+1);
        sierpinski(g,pointS,pointE,point3,depth,currentDepth+1);
    }

    /**
     * Repaint the window if the slider moves.
     * 
     * @param event change event
     */
    public final void stateChanged(final ChangeEvent event) {
        repaint();
    }
}
