package AmericanToadGame;

import ks.common.games.Solitaire;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;

public class MoveCardFromReservePileToTableau extends Move {
	public static final String TAG = "MoveCardFromReservePileToTableau";
	
	protected BuildablePile fromReservePile;
	protected Column toTableau;
	protected Card draggingCard;
	
	public MoveCardFromReservePileToTableau(BuildablePile from, Column to, Card c) {
		fromReservePile = from;
		toTableau = to;
		draggingCard = c;
	}
	
	public boolean doMove (Solitaire theGame) {
		if (!valid (theGame))
			return false;
	
		if (draggingCard == null)
			toTableau.add (fromReservePile.get());
		else
			toTableau.add (draggingCard);

		return true;
	}

	public boolean undo(Solitaire game) {
		if (toTableau.empty()) return false;
		
		fromReservePile.add (toTableau.get());

		return true;
	}

	public boolean valid(Solitaire game) {
		
		if (draggingCard == null) {
			if (fromReservePile.empty()) {
				return false;   // NOTHING TO EXTRACT!
			} else {
				draggingCard = fromReservePile.peek();
			}
		}
		
		if(toTableau.empty()) {
			System.out.println(TAG + "::toTableau empty");
			return true;
		} else if(draggingCard.getSuit() == toTableau.suit()){
			if(draggingCard.getRank() + 1 
					== toTableau.rank()) {
				System.out.println(TAG + "::1");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
