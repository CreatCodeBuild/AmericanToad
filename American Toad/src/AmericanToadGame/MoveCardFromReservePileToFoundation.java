package AmericanToadGame;

import ks.common.games.Solitaire;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class MoveCardFromReservePileToFoundation extends Move {
	
	public static final String TAG = "MoveCardFromReservePileToFoundation";

	BuildablePile fromReservePile;
	Pile toFoundation;
	Card card;
	int baseRank;
	
	public MoveCardFromReservePileToFoundation(BuildablePile pile, Pile foundation, Card c, int rank) {
		fromReservePile = pile;
		toFoundation = foundation;
		card = c;
		baseRank = rank;
	}

	@Override
	public boolean doMove(Solitaire theGame) {
		if (!valid (theGame))
			return false;
		
		if (card == null)
			toFoundation.add (fromReservePile.get());
		else
			toFoundation.add (card);
	
		// advance score
		theGame.updateScore (1);
		return true;
	}

	@Override
	public boolean undo(Solitaire theGame) {
		if (toFoundation.empty()) return false;
		fromReservePile.add (toFoundation.get());
		return true;
	}

	@Override
	public boolean valid(Solitaire theGame) {
		if(card != null) {
		    if(toFoundation.empty() && card.getRank() == baseRank) {
		    	System.out.println(TAG + "::2");
				return true;
		    } else if(card.getSuit() == toFoundation.suit() && ( card.getRank() - 1 == toFoundation.rank() || (card.getRank() == Card.ACE && toFoundation.rank() == Card.KING))) {
				System.out.println(TAG + "::4");
				return true;
			}
		}
		return false;
	}

}
