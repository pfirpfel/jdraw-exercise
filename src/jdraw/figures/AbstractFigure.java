package jdraw.figures;

import java.util.HashSet;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;

public abstract class AbstractFigure implements Figure {

    private static final long serialVersionUID = 2308398630061027192L;
    
    private HashSet<FigureListener> listeners = new HashSet<FigureListener>();
    
    /**
     * Notify all listeners
     */
    protected void notifyListeners() {
        FigureListener[] copy;
        synchronized(this){
            copy = listeners.toArray(new FigureListener[listeners.size()]);
        }
        for(FigureListener fl : copy) {
            fl.figureChanged(new FigureEvent(this));
        }
    }

    @Override
    public void addFigureListener(FigureListener listener) {
        if(listener != null){
            listeners.add(listener);
        }
    }

    @Override
    public void removeFigureListener(FigureListener listener) {
        if(listener != null && listeners.contains(listener)){
            listeners.remove(listener);
        }
    }
    
    @Override
    public Object clone(){
        return null;
    }

}
