/*
 * Copyright (c) 2000-2012 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.util.Collections;
import java.util.HashSet;

import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelListener;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;

/**
 * Provide a standard behavior for the drawing model. This class initially does not implement the methods
 * in a proper way.
 * It is part of the course assignments to do so.
 * @author Michael Kuenzli, <michael.kuenzli@students.fhnw.ch
 */
public class StdDrawModel implements DrawModel {
    
    /**
     * Stores the figures
     */
    private IndexList<Figure> figures = new IndexList<Figure>();
    
    /**
     * Listen to changes in figures
     */
    private FigureListener fl = new FigureListener(){
            public void figureChanged(FigureEvent e){
                notifyListeners(e.getFigure(),DrawModelEvent.Type.FIGURE_CHANGED);
            }
    };
    
	@Override
	public void addFigure(Figure f) {
	    if(f != null && !figures.contains(f) && figures.add(f)) {
	        f.addFigureListener(fl);
	        notifyListeners(f,DrawModelEvent.Type.FIGURE_ADDED);
	    }
	}

	@Override
	public Iterable<Figure> getFigures() {
	    return Collections.unmodifiableList(figures);
	}

	@Override
	public void removeFigure(Figure f) {
	    if(f!= null && figures.remove(f)) {
	        // notify only if list changed
	        f.removeFigureListener(fl);
	        notifyListeners(f,DrawModelEvent.Type.FIGURE_REMOVED);
	    }
	}
	
	/**
	 * Stores all listeners
	 */
	private HashSet<DrawModelListener> listeners = new HashSet<DrawModelListener>();
	
	/**
	 * Notifies listeners if changes happened
	 * @param f
	 * @param type
	 */
	private void notifyListeners(Figure f, DrawModelEvent.Type type) {
	    DrawModelListener[] copy;
	    synchronized(this){
	        copy = listeners.toArray(new DrawModelListener[listeners.size()]);
	    }
	    for(DrawModelListener l : copy) {
	        l.modelChanged(new DrawModelEvent(this, f, type));
	    }
	}

	@Override
	public void addModelChangeListener(DrawModelListener listener) {
	    if(listener != null) {
	        listeners.add(listener);
	    }
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
	    if(listener != null && listeners.contains(listener)) {
	        listeners.remove(listener);
	    }
	}

	/** The draw command handler. Initialized here with a dummy implementation. */
	// TODO: initialize with your implementation from the assignments.
	private DrawCommandHandler handler = new EmptyDrawCommandHandler();

	/**
	 * Retrieve the draw command handler in use.
	 * @return the draw command handler.
	 */
	public DrawCommandHandler getDrawCommandHandler() {
		return handler;
	}

	@Override
	public void setFigureIndex(Figure f, int index) {
	    figures.setPosition(f, index);
	    notifyListeners(f, DrawModelEvent.Type.DRAWING_CHANGED);
	}

	@Override
	public void removeAllFigures() {
	    for(Figure f : figures){
	        f.removeFigureListener(fl);
	    }
	    figures.clear();
	    notifyListeners(null, DrawModelEvent.Type.DRAWING_CLEARED);
	}

}
