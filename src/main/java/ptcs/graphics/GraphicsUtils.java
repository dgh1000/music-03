package ptcs.graphics;

import ptcs.core.*;
import ptcs.util.*;
import java.awt.*;


public class GraphicsUtils {
    public static void drawComp(Graphics g, Comp comp, int width, 
        int height, double tMin, double tMax, Color c) {
        for (Note n: comp.notes) {
            drawNote(g, n, width, height, tMin, tMax, c);
        }
    }

    public static void drawNote(
        Graphics g, Note n, int width, int height, double scaleMin, 
        double scaleMax, Color c) {

        g.setColor(c);
        double x1 = MyMath.scale_3_2(scaleMin, n.tOn, scaleMax, 0, width);
        double x2 = MyMath.scale_3_2(scaleMin, n.tOff, scaleMax, 0, width);
        double y1 = MyMath.scale_3_2(20, n.pitch, 90, height, 0);
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y1);
        

    }
}