package AmericanToadController;

import java.awt.event.MouseEvent;

import AmericanToadGame.AmericanToad;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Column;
import ks.common.view.BuildablePileView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.Widget;

public class ReservePileController extends SolitaireReleasedAdapter {
	
	public static final String TAG = "ReservePileController";

	BuildablePileView src;
	
	public ReservePileController(AmericanToad theGame, BuildablePileView pileView) {
		super(theGame);
		src = pileView;
	}
	
	public void mousePressed(MouseEvent me) {
		Container c = theGame.getContainer();

		BuildablePile theBP = (BuildablePile) src.getModelElement();
		if (theBP.count() == 0) {
			return;
		}

		// Get a column of cards to move from the BuildablePileView
		// Note that this method will alter the model for BuildablePileView if the condition is met.
		ColumnView colView = src.getColumnView (me);

		if (colView == null) {
			return;
		}

		// Check conditions
		Column col = (Column) colView.getModelElement();
		if (col == null) {
			//System.err.println ("BuildablePileController::mousePressed(): Unexpectedly encountered a ColumnView with no Column.");
			return; // sanity check, but should never happen.
		}
		
		if(col.count() != 1) {
			src.returnWidget(colView);
			src.refresh();
			return;
		}

		// If we get here, then the user has indeed clicked on the top card in the PileView and
		// we are able to now move it on the screen at will. For smooth action, the bounds for the
		// cardView widget reflect the original card location on the screen.
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			//System.err.println ("BuildablePileController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}

		// Tell container which object is being dragged, and where in that widget the user clicked.
		c.setActiveDraggingObject (colView, me);

		// Tell container which BuildablePileView is the source for this drag event.
		c.setDragSource (src);

		// we simply redraw our source pile to avoid flicker,
		// rather than refreshing all widgets...
		src.redraw();
	}

}
