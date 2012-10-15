package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.util.List;

import jdraw.framework.FigureHandle;

public class Polygon extends AbstractFigure {

    private static final long serialVersionUID = -158562265915173414L;

    private GeneralPath newPolygon;
    private int radius;
    private int corners;
    private int x, y;

    /**
     * Creates a new polygon of the given specifications
     * 
     * @param x the x-coordinate of the upper left bound corner of the polygon
     * @param y the y-coordinate of the upper left bound corner of the polygon
     * @param corners number of corners
     * @param radius radius of the polygon
     */
    public Polygon(int x, int y, int corners, int radius) {
        this.corners = corners;
        this.radius = radius;
        this.x = x;
        this.y = y;
        setPolygon(this.corners, this.radius, this.x, this.y);
    }

    public int getCorners() {
        return corners;
    }

    public void setCorners(int corners) {
        this.corners = corners;
    }

    /**
     * Sets polygon coordinates
     * 
     * @param corners Number of corners to draw
     * @param radius Radius of polygon (half of width/height)
     * @param xoffset Offset of X-coordinate
     * @param yoffset Offset of Y-coordinate
     */
    private void setPolygon(int corners, int radius, int xoffset, int yoffset) throws IllegalArgumentException {
        if (corners <= 0 && radius < 0) {
            throw new IllegalArgumentException();
        }
        Point[] p = new Point[corners];
        for (int i = 0; i < corners; i++) {
            p[i] = new Point(0, 0);
            double arc = (double) i / corners * (2 * Math.PI);
            p[i].x = (int) (radius + Math.cos(arc) * radius + 0.5 + xoffset);
            p[i].y = (int) (radius - Math.sin(arc) * radius + 0.5 + yoffset);
        }
        newPolygon = new GeneralPath();
        newPolygon.moveTo(p[0].x, p[0].y);

        for (int i = 1; i < p.length; i++)
            newPolygon.lineTo(p[i].x, p[i].y);
        newPolygon.closePath();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.translate(radius, radius);
        g2d.fill(newPolygon);
    }

    @Override
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            setPolygon(corners, radius, x + dx, y + dy);
            this.notifyListeners();
        }
    }

    @Override
    public boolean contains(int x, int y) {
        Rectangle containRect = new Rectangle(this.x, this.y, radius * 2, radius * 2);
        return containRect.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        int radius,x ,y;
        if (origin.x <= corner.x) { // x on the left
            radius = (int) (((double) (corner.x - origin.x) + 0.5) / 2);
        } else {
            radius = (int) (((double) (origin.x - corner.x) + 0.5) / 2);
        }
        if (origin.y <= corner.y) { // origin on top
            if(origin.x <= corner.x){ // origin on the left
                x = origin.x;
                y = origin.y;
            } else {
                x = corner.x;
                y = origin.y;
            }
        } else { // corner on top
            if(origin.x <= corner.x){ // origin on the left
                x = origin.x;
                y = corner.y;
            } else {
                x = corner.x;
                y = corner.y;
            }
            setPolygon(corners, radius, corner.x, corner.y);
        }
        setPolygon(corners, radius, x, y);
        notifyListeners();
    }

    @Override
    public Rectangle getBounds() {
        Rectangle containRect = new Rectangle(this.x, this.y, radius * 2, radius * 2);
        return containRect.getBounds();
    }

    @Override
    public List<FigureHandle> getHandles() {
        // TODO Auto-generated method stub
        return null;
    }

}
