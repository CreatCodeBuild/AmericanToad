package AmericanToadController;

import java.awt.event.MouseEvent;

import AmericanToadGame.AmericanToad;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class WastePileController extends SolitaireReleasedAdapter {
	PileView pileViewWaste;
	
	public WastePileController(AmericanToad theGame, PileView pile) {
		super(theGame);
		pileViewWaste = pile;
	}
	
	public void mousePressed(MouseEvent me) {
		Container c = theGame.getContainer();
		
		Pile pileWaste = (Pile) pileViewWaste.getModelElement();
		if (pileWaste.count() == 0) {
			c.releaseDraggingObject();
			return;
		}
		
		CardView cardView = pileViewWaste.getCardViewForTopCard (me);
		
		if (cardView == null) {
			c.releaseDraggingObject();
			return;
		}
		
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("PileWasteController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}
		
		c.setActiveDraggingObject (cardView, me);
		c.setDragSource (pileViewWaste);
		pileViewWaste.redraw();
	}
}
