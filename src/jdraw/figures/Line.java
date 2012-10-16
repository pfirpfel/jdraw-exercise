package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.awt.geom.Line2D;

import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

public class Line extends AbstractFigure implements Figure {
    private static final long serialVersionUID = -4883525024593907346L;

    private Line2D.Double line;
    private static final int PT_DIST = 6;

    /**
     * Constructs a line with the given coordinates.
     * 
     * @param x1 x-coordinate of start point
     * @param y1 y-coordinate of start point
     * @param x2 x-coordinate of end point
     * @param y2 y-coordinate of end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        line = new Line2D.Double(x1, y1, x2, y2);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
    }

    @Override
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            line.x1 = line.x1 + dx;
            line.x2 = line.x2 + dx;
            line.y1 = line.y1 + dy;
            line.y2 = line.y2 + dy;
            notifyListeners();
        }
    }

    @Override
    public boolean contains(int x, int y) {
      if(line.ptLineDist(x, y) <= PT_DIST) {
        return true;
      } else {
        return false;
      }
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        int newx1 = 0, newy1 = 0; // top left
        int newx2 = 0, newy2 = 0; // bottom right

        if (origin.x <= corner.x) {
            newx1 = origin.x;
            newx2 = corner.x;
        } else {
            newx1 = corner.x;
            newx2 = origin.x;
        }
        if (origin.y <= corner.y) {
            newy1 = origin.y;
            newy2 = corner.y;
        } else {
            newy1 = corner.y;
            newy2 = origin.y;
        }

        if (line.x1 <= line.x2) {
            line.x1 = newx1;
            line.x2 = newx2;
        } else {
            line.x1 = newx2;
            line.x2 = newx1;
        }
        if (line.y1 <= line.y2) {
            line.y1 = newy1;
            line.y2 = newy2;
        } else {
            line.y1 = newy2;
            line.y2 = newy1;
        }

        notifyListeners();
    }
    
    /**
     * Resets line to given coordinates
     * @param startpoint start point, origin of line
     * @param endpoint end point, where the line ends
     */
    public void setLine(Point startpoint, Point endpoint){
        line.x1 = startpoint.x;
        line.y1 = startpoint.y;
        line.x2 = endpoint.x;
        line.y2 = endpoint.y;
        
        notifyListeners();
    }
    
    @Override
    public Rectangle getBounds() {
        return line.getBounds();
    }

    @Override
    public List<FigureHandle> getHandles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object clone() {
        return null;
    }

}
