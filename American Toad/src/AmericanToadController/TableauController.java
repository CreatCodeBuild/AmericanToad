package AmericanToadController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.BuildablePileView;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.Widget;
import AmericanToadGame.AmericanToad;
import AmericanToadGame.MoveCardFromReservePileToTableau;
import AmericanToadGame.MoveCardFromWastePileToFoundation;
import AmericanToadGame.MoveCardFromWastePileToTableau;
import AmericanToadGame.MoveColumnFromTableuToTableau;


public class TableauController extends MouseAdapter {
	public static final String TAG = "TableauController";

	protected AmericanToad theGame;
	protected ColumnView src;	

	public TableauController(AmericanToad theGame, ColumnView tableau) {
		super();
		this.theGame = theGame;
		this.src = tableau;
	}
	
	public void mouseReleased(MouseEvent me) {
		//putting a column
		Container c = theGame.getContainer();
		
		Widget draggingWidget = c.getActiveDraggingObject();
		if (draggingWidget == Container.getNothingBeingDragged()) {
			System.err.println ("TableauController::mouseReleased() unexpectedly found nothing being dragged.");
			c.releaseDraggingObject();		
			return;
		}

		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("TableauController::mouseReleased(): somehow no dragSource in container.");
			c.releaseDraggingObject();
			return;
		}

		//BuildablePile toPile = (BuildablePile) src.getModelElement();
		Column toTableau = (Column) src.getModelElement();
		
		if (fromWidget instanceof ColumnView) {
			//move cards from tableau to tableau
			System.out.println(TAG + "::from tableau to tableau");
			//from wastePile
			Column fromTableau = (Column) fromWidget.getModelElement();

			ColumnView colView = (ColumnView) draggingWidget;
			Column col = (Column) colView.getModelElement();
			if (col == null) {
				System.err.println (TAG + "::mouseReleased(): somehow CardView model element is null.");
				c.releaseDraggingObject();
				return;
			}
			if(fromTableau.equals(toTableau)) {
				System.out.println(TAG + "::same tableau");
				fromWidget.returnWidget (draggingWidget);
				c.releaseDraggingObject();
			} else {
				Move m = new MoveColumnFromTableuToTableau(fromTableau, toTableau, col, col.count());
				if (m.doMove (theGame)) {
					theGame.pushMove (m);
				} else {
					System.out.println(TAG + "failed on doing MoveCardFromWastePileToFoundation");
					fromWidget.returnWidget (draggingWidget);
				}
				
				if(fromTableau.empty()) {
					Move auto = new MoveCardFromReservePileToTableau(theGame.pileReserve, fromTableau, theGame.pileReserve.get());
					
					if(auto.doMove(theGame)) {
						theGame.pushMove(auto);
					} else {
						
					}
				}
			}
		} else if(fromWidget instanceof BuildablePileView) {
			System.out.println(TAG + "::from reserve pile to tableau");
			//move card from reserve pile to tableau
			ColumnView columnView = (ColumnView) draggingWidget;
			Column col = (Column) columnView.getModelElement();
			if (col == null) {
				System.err.println ("BuildablePileController::mouseReleased(): somehow ColumnView model element is null.");
				return;
			}

			if (fromWidget == src) {
				toTableau.push (col);   // simply put right back where it came from. No move
			} else {
				BuildablePile fromPile = (BuildablePile) fromWidget.getModelElement();
				Move m = new MoveCardFromReservePileToTableau(fromPile, toTableau, col.peek());

				if (m.doMove (theGame)) {
					System.out.println(TAG + "::from reserve pile to tableau success");
					theGame.pushMove (m);
				} else {
					System.out.println(TAG + "::from reserve pile to tableau invalid");
					fromPile.push (col);
				}
			}	
		} else {
			System.out.println(TAG + "::from waste pile to tableau");
			Pile wastePile = (Pile) fromWidget.getModelElement();

			CardView cardView = (CardView) draggingWidget;
			Card theCard = (Card) cardView.getModelElement();
			if (theCard == null) {
				System.err.println ("FoundationController::mouseReleased(): somehow CardView model element is null.");
				c.releaseDraggingObject();
				return;
			}

			Move m = new MoveCardFromWastePileToTableau(wastePile, toTableau, theCard);
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
	
	public void mousePressed(MouseEvent me) {
		//dragging a column
		Container c = theGame.getContainer();
		
		Column col = (Column) src.getModelElement();
		if(col.count() == 0) {
			return;
		}
		
		ColumnView colView = src.getColumnView (me);
		
		if (colView == null) {
			return;
		}
		
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println (TAG + "::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
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
	
	public void mouseClicked(MouseEvent me) {
		Column c = (Column) src.getModelElement();
		if(c.empty()) {
			System.out.println(TAG + "::empty foundation");
		} else {
			System.out.println(TAG + "::non empty foundation");
		}
	}
}
