package jdraw.figures;

import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawContext;

public class LineTool extends AbstractDrawTool {

    private Line newLine;

    private Point anchor;

    public LineTool(DrawContext context) {
        super(context, null, "Line");
    }

    @Override
    public void mouseDown(int x, int y, MouseEvent e) {
        if (newLine != null) {
            throw new IllegalStateException();
        }
        anchor = new Point(x, y);
        newLine = new Line((double) x, (double) y, (double) x, (double) y);
        view.getModel().addFigure(newLine);
    }

    @Override
    public void mouseDrag(int x, int y, MouseEvent e) {
        newLine.setLine(anchor, new Point(x, y));
        int length = (int) Math.sqrt(Math.pow(x - anchor.x, 2) + Math.pow(y - anchor.y, 2));
        this.context.showStatusText("line length: " + length);

    }

    @Override
    public void mouseUp(int x, int y, MouseEvent e) {
        newLine.setLine(anchor, new Point(x, y));
        newLine = null;
        anchor = null;
        this.context.showStatusText("Line Mode");
    }

}
