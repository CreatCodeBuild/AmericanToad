package AmericanToadGame;

import ks.common.games.Solitaire;
import ks.common.model.BuildablePile;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Stack;

public class MoveColumnFromTableuToTableau extends Move {
	
	protected Column fromTableau;
	protected Column toTableau;
	protected Column columnBeingDragged;
	protected int numCards;
	
	public MoveColumnFromTableuToTableau(Column from, Column to, Column c, int num) {
		fromTableau = from;
		toTableau = to;
		columnBeingDragged = c;
		numCards = num;
	}

	@Override
	public boolean doMove(Solitaire theGame) {
		// VALIDATE:
		if (valid (theGame) == false)
			return false;

		// EXECUTE:
		if (columnBeingDragged == null) {
			fromTableau.select (numCards);
			Stack st = fromTableau.getSelected ();
			toTableau.push (st);
		} else {
			// already have the column
			toTableau.push (columnBeingDragged);
		}

		return true;
	}

	@Override
	public boolean undo(Solitaire theGame) {
		// VALIDATE:
		if (toTableau.count() < numCards) return false;
		
		// We know the number of cards moved, so we select them, extract the
		// stack, and move them all back to the fromPile.
		toTableau.select (numCards);
		Stack st = toTableau.getSelected ();
		fromTableau.push (st);

		return true;
	}

	@Override
	public boolean valid(Solitaire theGame) {
		// If move hasn't happened yet, we must extract the desired column to move.
		Column targetColumn;
		if (columnBeingDragged == null) {
			targetColumn = new Column();
			for (int i = numCards; i>=1; i--) {
				targetColumn.add (fromTableau.peek(fromTableau.count() - i));
			}
		} else {
			targetColumn = columnBeingDragged;
		}
		
		//   moveColumnBetweenPiles(Column col, BuildablePile to) : to.empty()
		if (toTableau.empty())
			return true;

		// 	  moveColumnBetweenPiles(Column col,BuildablePile to) : not col.empty() and col.bottom() == to.rank() - 1 and to.peek().faceUp() == true
		if(toTableau.empty()) {
			return true;
		} else {
			if(toTableau.suit() == targetColumn.suit() 
			   && toTableau.rank() == targetColumn.peek(0).getRank() + 1) {
				return true;
			} else {
				return false;
			}
		}
	}

}
