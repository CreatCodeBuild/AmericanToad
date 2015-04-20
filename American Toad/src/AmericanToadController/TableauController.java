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
import AmericanToadGame.MoveCardFromReservePileToFoundation;
import AmericanToadGame.MoveCardFromReservePileToTableau;
import AmericanToadGame.MoveCardFromWastePileToFoundation;
import AmericanToadGame.MoveCardFromWastePileToTableau;


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
