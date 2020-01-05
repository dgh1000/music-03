package ptcs;

import ptcs.core.*;
import ptcs.graphics.GraphicsUtils;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
// import java.awt.color.*;
import java.util.Map.*;
import java.util.*;

public class GsApp {
    public static MyPanel createAndShowGUI() {
        System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyPanel mp = new MyPanel();
        f.add(mp);
        f.pack();
        f.setVisible(true);
        return mp;
    }
}

class MyPanel extends JPanel {
    static final long serialVersionUID = 1;

    private List<Entry<Comp,Color>> compsToDraw;
    double tMin;
    double tMax;

    public MyPanel() {
        compsToDraw = new ArrayList<>();
        setBorder(BorderFactory.createLineBorder(Color.black));

        /*
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveSquare(e.getX(), e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                moveSquare(e.getX(), e.getY());
            }
        });
        */
    }

    public void setCompList(List<Entry<Comp,Color>> comps, double tMin, double tMax) {
        compsToDraw = comps;
        this.tMin = tMin;
        this.tMax = tMax;
        repaint();
    }

    /*
    private void moveSquare(int x, int y) {
        int OFFSET = 1;
        if ((squareX != x) || (squareY != y)) {
            repaint(squareX, squareY, squareW + OFFSET, squareH + OFFSET);
            squareX = x;
            squareY = y;
            repaint(squareX, squareY, squareW + OFFSET, squareH + OFFSET);
        }
    }
    */

    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Entry<Comp,Color> e: compsToDraw) {
            GraphicsUtils.drawComp(g, e.getKey(), 800, 800, tMin, tMax, e.getValue());
        }

        // Draw Text
        // g.drawString("This is my custom Panel!", 10, 20);


        // g.setColor(Color.RED);
        // g.fillRect(100, 100, 100, 100);
        /*
        g.setColor(Color.RED);
        g.fillRect(squareX, squareY, squareW, squareH);
        g.setColor(Color.BLACK);
        g.drawRect(squareX, squareY, squareW, squareH);
        */
    }
}
