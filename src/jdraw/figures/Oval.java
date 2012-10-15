package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.List;

import jdraw.framework.FigureHandle;

public class Oval extends AbstractFigure {

    private static final long serialVersionUID = 8620621540502532263L;
    
    private Ellipse2D.Double oval;

    /**
     * Create a new oval of the given size
     * 
     * @param x x-coordinate of the circle
     * @param y y-coordinate of the circle
     * @param w width of the circle
     * @param h height of the circle
     */
    public Oval(double x, double y, double w, double h) {
        oval = new Ellipse2D.Double(x, y, w, h);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval((int) oval.x, (int) oval.y, (int) oval.width, (int) oval.height);
        g.setColor(Color.BLACK);
        g.drawOval((int) oval.x, (int) oval.y, (int) oval.width, (int) oval.height);
    }

    @Override
    public void move(int dx, int dy) {
        if (dx != 0 && dy != 0) {
            oval.setFrame((int) oval.x + dx, (int) oval.y + dy, (int) oval.width, (int) oval.height);
            notifyListeners();
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return oval.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        oval.setFrameFromDiagonal(origin, corner);
        notifyListeners();
    }

    @Override
    public Rectangle getBounds() {
        return oval.getBounds();
    }

    @Override
    public List<FigureHandle> getHandles() {
        // TODO Auto-generated method stub
        return null;
    }

}
