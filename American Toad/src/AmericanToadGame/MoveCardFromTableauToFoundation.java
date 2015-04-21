package AmericanToadGame;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class MoveCardFromTableauToFoundation extends Move{
	public static final String TAG = "MoveCardFromTableauToFoundation";
	
	Column fromTableau;
	Pile toFoundation;
	Card card;
	int baseRank;
	
	public MoveCardFromTableauToFoundation(Column from, Pile to, Card c, int base) {
		fromTableau = from;
		toFoundation = to;
		card = c;
		baseRank = base;
	}
	
	@Override
	public boolean doMove(Solitaire theGame) {
		if (!valid (theGame))
			return false;
	
		// EXECUTE:
		// Deal with both situations
		if (card == null)
			toFoundation.add (fromTableau.get());
		else
			toFoundation.add (card);
	
		// advance score
		theGame.updateScore (1);
		return true;
	}

	@Override
	public boolean undo(Solitaire theGame) {
		if (toFoundation.empty()) return false;
		fromTableau.add (toFoundation.get());
		return true;
	}

	@Override
	public boolean valid(Solitaire theGame) {
		if(card == null) {
			System.out.println(TAG + "::1");
			return false;
		} else {
			if(toFoundation.empty()) {
				//if the target foundation is empty, then dragging card must be the base rank
				if(card.getRank() == baseRank) {
					System.out.println(TAG + "::2");
					return true;
				} else {
					System.out.println(TAG + "::3");
					return false;
				}
			} else {
				if(card.getSuit() == toFoundation.suit() 
				&&( card.getRank() - 1 == toFoundation.rank()
				|| (card.getRank() == Card.ACE && toFoundation.rank() == Card.KING))) {
					System.out.println(TAG + "::4");
					return true;
				} else {
					System.out.println(TAG + "::5");
					return false;
				}
			}
		}
	}
	
}
