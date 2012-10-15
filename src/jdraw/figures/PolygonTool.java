package jdraw.figures;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jdraw.framework.DrawContext;

public class PolygonTool extends AbstractDrawTool {
    /**
     * Temporary variable. During polygon creation (during a mouse down - mouse drag - mouse up
     * cycle) this variable refers to the new polygon that is inserted.
     */
    private Polygon newPoly = null;

    /**
     * Temporary variable. During polygon creation this variable refers to the point the mouse was
     * first pressed.
     */
    private Point anchor = null;

    /**
     * Create a new polygon tool for the given context.
     * 
     * @param context a context to use this tool in.
     */
    public PolygonTool(DrawContext context) {
        super(context, null);
    }

    @Override
    public void activate() {
        this.context.showStatusText("Polygon Mode");
    }

    @Override
    public void mouseDown(int x, int y, MouseEvent e) {
        if (newPoly != null) {
            throw new IllegalStateException();
        }
        anchor = new Point(x, y);
        newPoly = new Polygon(x, y, 5, 0);
        view.getModel().addFigure(newPoly);
    }

    @Override
    public void mouseDrag(int x, int y, MouseEvent e) {
        newPoly.setBounds(anchor, new Point(x, y));
        this.context.showStatusText("x1:" + anchor.x + " y1:" + anchor.y + "  drag x2:" + x + " y2:" + y);
    }

    @Override
    public void mouseUp(int x, int y, MouseEvent e) {
        newPoly.setBounds(anchor, new Point(x, y));
        newPoly = null;
        anchor = null;
        this.context.showStatusText("Polygon Mode");

    }

    @Override
    public Icon getIcon() {
        return new ImageIcon(getClass().getResource(IMAGES + "polygon.png"));
    }

    @Override
    public String getName() {
        return "Polygon";
    }

}
