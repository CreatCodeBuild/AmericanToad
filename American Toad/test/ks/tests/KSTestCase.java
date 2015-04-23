package ks.tests;

import java.awt.event.MouseEvent;

import junit.framework.TestCase;
import ks.common.games.Solitaire;
import ks.common.view.Widget;

/**
 * Defines useful helper methods for testing KombatSolitaire plugins.
 * <p>
 * If you would like to use this capability, have your JUnit test case
 * extend from this class instead of {@link TestCase}.
 * 
 * @author George Heineman
 */
public abstract class KSTestCase extends TestCase {
	/** (dx,dy) are offsets into the widget space. Feel Free to Use as Is. */
	public MouseEvent createPressed (Solitaire game, Widget view, int dx, int dy) {
		MouseEvent me = new MouseEvent(game.getContainer(), MouseEvent.MOUSE_PRESSED, 
				System.currentTimeMillis(), 0, 
				view.getX()+dx, view.getY()+dy, 0, false);
		return me;
	}
	
	/** (dx,dy) are offsets into the widget space. Feel Free to Use as Is. */
	public MouseEvent createRightClick (Solitaire game, Widget view, int dx, int dy) {
		MouseEvent me = new MouseEvent(game.getContainer(), MouseEvent.MOUSE_PRESSED, 
				System.currentTimeMillis(), 0, 
				view.getX()+dx, view.getY()+dy, 0, true);
		return me;
	}
	
	/** (dx,dy) are offsets into the widget space. Feel Free to Use as Is. */
	public MouseEvent createReleased (Solitaire game, Widget view, int dx, int dy) {
		MouseEvent me = new MouseEvent(game.getContainer(), MouseEvent.MOUSE_RELEASED, 
				System.currentTimeMillis(), 0, 
				view.getX()+dx, view.getY()+dy, 0, false);
		return me;
	}
	
	/** (dx,dy) are offsets into the widget space. Feel Free to Use as Is. */
	public MouseEvent createClicked (Solitaire game, Widget view, int dx, int dy) {
		MouseEvent me = new MouseEvent(game.getContainer(), MouseEvent.MOUSE_CLICKED, 
				System.currentTimeMillis(), 0, 
				view.getX()+dx, view.getY()+dy, 1, false);
		return me;
	}
	
	/** (dx,dy) are offsets into the widget space. Feel Free to Use as Is. */
	public MouseEvent createDoubleClicked (Solitaire game, Widget view, int dx, int dy) {
		MouseEvent me = new MouseEvent(game.getContainer(), MouseEvent.MOUSE_CLICKED, 
				System.currentTimeMillis(), 0, 
				view.getX()+dx, view.getY()+dy, 2, false);
		return me;
	}
}
