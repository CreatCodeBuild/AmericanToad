package AmericanToadGame;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class MoveCardFromWastePileToTableau extends Move{
	public static final String TAG = "MoveCardFromWastePileToTableau";
	
	protected Pile fromWastePile;
	protected Column toTableau;
	protected Card draggingCard;
	
	public MoveCardFromWastePileToTableau(Pile pile, Column tableau, Card c) {
		fromWastePile = pile;
		toTableau = tableau;
		draggingCard = c;
	}
	
	public boolean doMove (Solitaire theGame) {
		if (!valid (theGame))
			return false;
	
		if (draggingCard == null)
			toTableau.add (fromWastePile.get());
		else
			toTableau.add (draggingCard);

		return true;
	}

	public boolean undo(Solitaire game) {
		if (toTableau.empty()) return false;
		
		fromWastePile.add (toTableau.get());

		return true;
	}

	public boolean valid(Solitaire game) {
		
		if (draggingCard == null) {
			if (!fromWastePile.empty()) {
				draggingCard = fromWastePile.peek();
			}
		}
		
		if(draggingCard != null) {
			if(toTableau.empty()) {
				return true;
			} else if(draggingCard.getSuit() == toTableau.suit() && draggingCard.getRank() + 1 == toTableau.rank()) {
				return true;
			}
		}
		
		return false;
	}
}
