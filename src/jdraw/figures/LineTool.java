package jdraw.figures;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jdraw.framework.DrawContext;

public class LineTool extends AbstractDrawTool {

    private Line newLine;

    private Point anchor;

    public LineTool(DrawContext context) {
        super(context, null);
    }

    /**
     * Activates the Line Mode. There will be a specific menu added to the menu bar that provides
     * settings for Line attributes
     */
    @Override
    public void activate() {
        this.context.showStatusText("Line Mode");
        // TODO ? add menu

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

    @Override
    public Icon getIcon() {
        return new ImageIcon(getClass().getResource(IMAGES + "line.png"));
    }

    @Override
    public String getName() {
        return "Line";
    }

}
