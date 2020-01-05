package ptcs.util;

public class Math {
    public static double scale_3_2(double x0, double x1, double x2, double y0, double y1) {
        return y0 + (y1 - y0) * (x1 - x0) / (x2 - x0);
    }   
}