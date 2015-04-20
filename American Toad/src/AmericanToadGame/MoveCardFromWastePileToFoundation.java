package AmericanToadGame;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;

public class MoveCardFromWastePileToFoundation extends Move {
	
	public static final String TAG = "MoveCardFromWastePileToFoundation";
	
	protected Pile fromWastePile;
	protected Pile toFoundation;
	protected Card draggingCard;
	protected int baseRank;

	public MoveCardFromWastePileToFoundation(Pile wastePile, Pile foundation, Card card, int rank) {
		super();
		fromWastePile = wastePile;
		draggingCard = card;
		toFoundation = foundation;
		baseRank = rank;
	}
	/**
	 * Each move should knows how to execute itself.
	 * <p>
	 * @param ks.game.Solitaire   the game being played.
	 * @return boolean
	 */
	public boolean doMove (Solitaire theGame) {
		if (!valid (theGame))
			return false;
	
		// EXECUTE:
		// Deal with both situations
		if (draggingCard == null)
			toFoundation.add (fromWastePile.get());
		else
			toFoundation.add (draggingCard);
	
		// advance score
		theGame.updateScore (1);
		return true;
	}
	/**
	 * undo method.
	 */
	public boolean undo(Solitaire game) {
		if (toFoundation.empty()) return false;
		
		fromWastePile.add (toFoundation.get());

		return true;
	}

	public boolean valid(Solitaire game) {
		
		if (draggingCard == null) {
			if (fromWastePile.empty()) return false;   // NOTHING TO EXTRACT!
			draggingCard = fromWastePile.peek();
		}
		
		if(draggingCard == null) {
			System.out.println(TAG + "::1");
			return false;
		} else {
			if(toFoundation.empty()) {
				//if the target foundation is empty, then dragging card must be the base rank
				if(draggingCard.getRank() == baseRank) {
					System.out.println(TAG + "::2");
					return true;
				} else {
					System.out.println(TAG + "::3");
					return false;
				}
			} else {
				if(draggingCard.getSuit() == toFoundation.suit() 
						&& draggingCard.getRank() - 1 == toFoundation.rank()) {
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
