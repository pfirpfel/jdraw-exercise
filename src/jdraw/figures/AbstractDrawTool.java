package jdraw.figures;

import java.awt.Cursor;

import javax.swing.Icon;
import javax.swing.ImageIcon;

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
     * The context�s view. This variable can be used as a shortcut, i.e.
     * instead of calling context.getView().
     */
    protected DrawView view;
    
    protected String name;
    
    @Override
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
    
    /**
     * The currently used cursor
     */
    protected Cursor currentCursor;
    
    public AbstractDrawTool(DrawContext context, Cursor cursor, String name){
        if(cursor != null){
            currentCursor = cursor;
        } else {
            Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR); //default for figures
        }
        this.context = context;
        this.view = context.getView();
        this.name = name;
    }
    
    /**
     * Activates the current mode. There will be a
     * specific menu added to the menu bar that provides settings for
     * this figure and the status bar will be updated.
     * @see jdraw.framework.DrawTool#activate()
     */
    public void activate() {
      this.context.showStatusText(name + " Mode");
      // todo add menu
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
    
    @Override
    public Icon getIcon() {
      return new ImageIcon(getClass().getResource(IMAGES + name.toLowerCase() +".png"));
    }

}
