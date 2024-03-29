package jdraw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelListener;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;
import jdraw.std.StdDrawModel;

import org.junit.Before;
import org.junit.Test;

public class DrawModelTest {

	static class TestFigure implements Figure {
		/**
         * UID
         */
        private static final long serialVersionUID = 2259475897206312395L;
        
        public void setBounds(Point origin, Point corner) {}
		public void draw(Graphics g) {}
		public void move(int dx, int dy) {}
		public boolean contains(int x, int y) { return false;	}
		public java.awt.Rectangle getBounds() {return new java.awt.Rectangle();}
		public List<FigureHandle> getHandles() { return null; }
		public void addFigureListener(FigureListener listener) { }
		public void removeFigureListener(FigureListener listener) { }
		public @Override Object clone() { return null;}
	}

	DrawModel m;
	Figure f;
	int cnt;
	DrawModelEvent.Type type;

	@Before
	public void setUp(){
		m = new StdDrawModel();
		f = new TestFigure();
		cnt = 0;
		type = null;
	}

	@Test
	public void testAddFigure1() {
		m.addFigure(f);
		assertTrue("get back the Figure from the model", (m.getFigures().iterator().next()).equals(f));
	}

	@Test
	public void testAddFigure2(){
		Figure f1 = new TestFigure();
		Figure f2 = new TestFigure();
		Figure f3 = new TestFigure();
		m.addFigure(f1);
		m.addFigure(f2);
		m.addFigure(f3);
		String msg = "order of adding figures is not preserved";
		Iterator<Figure> it = m.getFigures().iterator();
		assertEquals(msg, it.next(), f1);
		assertEquals(msg, it.next(), f2);
		assertEquals(msg, it.next(), f3);
	}

	@Test
	public void testAddFigure3(){
		DrawModelListener l = new DrawModelListener(){
			public void modelChanged(DrawModelEvent e) {
				type = e.getType();
			}
		};
		m.addModelChangeListener(l);
		m.addFigure(f);
		assertEquals("addFigure should notify a FIGURE_ADDED event", type, DrawModelEvent.Type.FIGURE_ADDED);
	}
	
	@Test
	public void testAddFigure4(){
		m.addFigure(f);
		m.addFigure(f);
		Iterator<Figure> it = m.getFigures().iterator();
		it.next();
		assertFalse("figures in the model should be unique", it.hasNext());
	}
	
	@Test
	public void testAddFigure5(){
		DrawModelListener l = new DrawModelListener(){
			public void modelChanged(DrawModelEvent e) {
				cnt++;
			}
		};
		m.addFigure(f);
		m.addModelChangeListener(l);
		m.addFigure(f);
		assertTrue("no notification if figure is already contained in model", cnt == 0);
	}
	
	@Test
	public void testAddFigure6(){
		@SuppressWarnings("serial")
        Figure f = new TestFigure(){
			public void addFigureListener(FigureListener listener) { cnt++; }
		};
		m.addFigure(f);
		assertTrue("model has to register a listener in the figure", cnt > 0);
	}
	
	@Test
	public void testRemoveFigure1() {
		DrawModelListener l = new DrawModelListener(){
			public void modelChanged(DrawModelEvent e) {
				cnt++;
			}
		};
		m.addModelChangeListener(l);
		m.removeFigure(f);
		assertTrue("no notificatoin expected, figure was not contained in model", cnt==0);
	}
	
	@Test
	public void testRemoveFigure2(){
		@SuppressWarnings("serial")
        Figure f = new TestFigure(){
			public void addFigureListener(FigureListener l) { cnt++; }
			public void removeFigureListener(FigureListener l) { cnt--; }
		};
		m.addFigure(f);
		m.removeFigure(f);
		assertTrue("listeners registered by the model must be removed", cnt == 0);
	}

	@Test
	public void testRemoveAllFigures1() {
		DrawModelListener l = new DrawModelListener(){
			public void modelChanged(DrawModelEvent e) {
				type = e.getType();
			}
		};
		m.addFigure(f);
		m.addModelChangeListener(l);
		m.removeAllFigures();
		assertEquals("removeAllFigures should notify a DRAWING_CLEARED event", type, DrawModelEvent.Type.DRAWING_CLEARED);
	}

	@Test
	public void testRemoveAllFigures2(){
		@SuppressWarnings("serial")
        class Fig extends TestFigure {
			public void addFigureListener(FigureListener l) { cnt++; }
			public void removeFigureListener(FigureListener l) { cnt--; }
		};
		Figure f1 = new Fig();
		Figure f2 = new Fig();
		m.addFigure(f1);
		m.addFigure(f2);
		m.removeAllFigures();
		assertTrue("listeners registered by the model must be removed", cnt == 0);
	}


	
	@Test
	public void testSetFigureIndex1(){
		DrawModelListener l = new DrawModelListener(){
			public void modelChanged(DrawModelEvent e) {
				type = e.getType();
			}
		};
		Figure f1 = new TestFigure();
		Figure f2 = new TestFigure();
		Figure f3 = new TestFigure();
		m.addFigure(f1);
		m.addFigure(f2);
		m.addFigure(f3);
		m.addModelChangeListener(l);
		m.setFigureIndex(f3, 0);
		Iterator<Figure> it = m.getFigures().iterator();
		assertEquals("f3 should be at position 0", it.next(), f3);
		assertEquals("f1 should be at position 1", it.next(), f1);
		assertEquals("f2 should be at position 2", it.next(), f2);
		assertEquals("setFigureIndex should notify a DRAWING_CHANGED event", type, DrawModelEvent.Type.DRAWING_CHANGED);
	}

	@Test
	public void testSetFigureIndex2(){
		Figure f1 = new TestFigure();
		m.addFigure(f1);
		try {
			m.setFigureIndex(f, 0);
			fail("IllegalargumentException expected");
		}
		catch(IllegalArgumentException e){
			// ok
		}
		catch(Exception e){
			fail("IllegalargumentException expected");
		}
	}
	
	@Test
    public void testSetFigureIndex3(){
        Figure f1 = new TestFigure();
        m.addFigure(f1);
        try {
            m.setFigureIndex(null, 0);
            fail("IllegalargumentException expected");
        }
        catch(IllegalArgumentException e){
            // ok
        }
        catch(Exception e){
            fail("IllegalargumentException expected");
        }
    }

	@Test
	public void testSetFigureIndex4(){
		Figure f1 = new TestFigure();
		Figure f2 = new TestFigure();
		m.addFigure(f1);
		m.addFigure(f2);
		m.setFigureIndex(f2, 0);
		m.setFigureIndex(f2, 1);
		try {
			m.setFigureIndex(f2, 2);
			fail("IndexOutOfBoundsException expected");
		}
		catch(IndexOutOfBoundsException e){
			// ok
		}
		catch(Exception e){
			fail("IndexOutOfBoundsException expected");
		}
	}
	
}
