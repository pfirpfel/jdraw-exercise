package jdraw.figures;

import java.awt.Cursor;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

public abstract class AbstractDrawTool implements DrawTool {
    
    /** 
     * the image resource path. 
     */
    protected static final String IMAGES = "/images/";

    /**
     * The context we use for drawing.
     */
    protected DrawContext context;

    /**
     * The context´s view. This variable can be used as a shortcut, i.e.
     * instead of calling context.getView().
     */
    protected DrawView view;
    
    /**
     * The currently used cursor
     */
    protected Cursor currentCursor;
    
    public AbstractDrawTool(DrawContext context, Cursor cursor){
        if(cursor != null){
            currentCursor = cursor;
        } else {
            Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR); //default for figures
        }
        this.context = context;
        this.view = context.getView();
    }
    
    /**
     * Deactivates the current mode by resetting the cursor
     * and clearing the status bar.
     * @see @see jdraw.framework.DrawTool#deactivate()
     */
    @Override
    public void deactivate() {
        // TODO: reset cursor
        context.showStatusText("");

    }

    @Override
    public Cursor getCursor() {
        return currentCursor;
    }

}
