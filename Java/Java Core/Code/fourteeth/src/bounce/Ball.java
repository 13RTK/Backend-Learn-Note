package bounce;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Ball {
    private static final int X_SIZE = 15;
    private static final int Y_SIZE = 15;
    private double x = 0;
    private double y = 0;
    private double dx = 0;
    private double dy = 0;

    public void move(Rectangle2D bounds) {
        x += dx;
        y += dy;

        if (x < bounds.getMinX()) {
            x = bounds.getMinX();
            dx = -dx;
        }

        if (x + X_SIZE >= bounds.getMaxX()) {
            x = bounds.getMaxX() - X_SIZE;
            dx = -dx;
        }

        if (y < bounds.getMinY()) {
            y = bounds.getMinY();
            dy = -dy;
        }

        if (y + Y_SIZE >= bounds.getMaxY()) {
            y = bounds.getMaxX() - Y_SIZE;
            dy = -dy;
        }
    }

    public Ellipse2D getShape() {
        return new Ellipse2D.Double(x, y, X_SIZE, Y_SIZE);
    }
}
