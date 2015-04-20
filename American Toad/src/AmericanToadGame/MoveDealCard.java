package AmericanToadGame;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Stack;

public class MoveDealCard extends Move{
	Deck deck;
	Stack stack;
	
	public MoveDealCard(Deck deck, Stack stack) {
		this.deck = deck;
		this.stack = stack;
	}

	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)) { 
			return false; 
		}
		
		Card card = deck.get();
		stack.add(card);
		game.updateNumberCardsLeft(-1); //why -1 here?
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		Card c = stack.get();
		deck.add(c);
		game.updateNumberCardsLeft(+1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return !deck.empty();
	}
	
}
