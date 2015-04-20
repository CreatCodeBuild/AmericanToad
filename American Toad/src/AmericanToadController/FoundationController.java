package AmericanToadController;

import java.awt.event.MouseEvent;

import AmericanToadGame.AmericanToad;
import AmericanToadGame.MoveCardFromReservePileToFoundation;
import AmericanToadGame.MoveCardFromWastePileToFoundation;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.BuildablePileView;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class FoundationController extends java.awt.event.MouseAdapter {
	
	public static final String TAG = "FoundationController";

	protected AmericanToad theGame;
	protected PileView src;	

	public FoundationController(AmericanToad theGame, PileView foundation) {
		super();
		this.theGame = theGame;
		this.src = foundation;
	}
	
	public void mouseReleased(MouseEvent me) {
		Container c = theGame.getContainer();
		
		Widget draggingWidget = c.getActiveDraggingObject();
		if (draggingWidget == Container.getNothingBeingDragged()) {
			System.err.println ("FoundationController::mouseReleased() unexpectedly found nothing being dragged.");
			c.releaseDraggingObject();		
			return;
		}

		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("FoundationController::mouseReleased(): somehow no dragSource in container.");
			c.releaseDraggingObject();
			return;
		}

		Pile foundation = (Pile) src.getModelElement();

		if (fromWidget instanceof BuildablePileView) {
			// coming from a buildable pile [user may be trying to move multiple cards]
			BuildablePile fromPile = (BuildablePile) fromWidget.getModelElement();

			/** Must be the ColumnView widget being dragged. */
			ColumnView columnView = (ColumnView) draggingWidget;
			Column col = (Column) columnView.getModelElement();
			if (col == null) {
				System.err.println ("FoundationController::mouseReleased(): somehow ColumnView model element is null.");
				c.releaseDraggingObject();			
				return;
			}

			// must use peek() so we don't modify col prematurely. Here is a HACK! Presumably
			// we only want the Move object to know things about the move, but we have to put
			// in a check to verify that Column is of size one. NO good solution that I can
			// see right now.
			if (col.count() != 1) {
				fromWidget.returnWidget (draggingWidget);  // return home
			} else {
				Move m = new MoveCardFromReservePileToFoundation (fromPile, foundation, col.peek(), theGame.getBaseRank().getValue());

				if (m.doMove (theGame)) {
					// Success
					theGame.pushMove (m);
				} else {
					fromWidget.returnWidget (draggingWidget);
				}
			}
		} else if(fromWidget instanceof ColumnView) {
			
		} else {
			//from wastePile
			Pile wastePile = (Pile) fromWidget.getModelElement();

			CardView cardView = (CardView) draggingWidget;
			Card theCard = (Card) cardView.getModelElement();
			if (theCard == null) {
				System.err.println ("FoundationController::mouseReleased(): somehow CardView model element is null.");
				c.releaseDraggingObject();
				return;
			}

			Move m = new MoveCardFromWastePileToFoundation (wastePile, foundation, theCard, theGame.getBaseRank().getValue());
			if (m.doMove (theGame)) {
				theGame.pushMove (m);
			} else {
				System.out.println(TAG + "failed on doing MoveCardFromWastePileToFoundation");
				fromWidget.returnWidget (draggingWidget);
			}
		}

		c.releaseDraggingObject();
		c.repaint();
	}
	
	public void mouseClicked(MouseEvent me) {
		Pile p = (Pile) src.getModelElement();
		if(p.empty()) {
			System.out.println(TAG + "::empty foundation");
		} else {
			System.out.println(TAG + "::non empty foundation");
		}
	}
}
