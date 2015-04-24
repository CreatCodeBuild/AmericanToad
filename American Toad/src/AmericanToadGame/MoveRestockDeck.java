package AmericanToadGame;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.model.Stack;

public class MoveRestockDeck extends Move {
	
	Deck deck;
	Stack stack;
	
	public MoveRestockDeck(Deck d, Stack p) {
		deck = d;
		stack = p;
	}

	@Override
	public boolean doMove(Solitaire theGame) {
		if(!valid(theGame))
			return false;
		
		while(stack.count() > 0) {
			Card c = stack.get();
			deck.add(c);
		}
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		while(deck.count() > 0) {
			Card c = deck.get();
			stack.add(c);
			game.updateNumberCardsLeft(-1);
		}
		return true;
	}

	@Override
	public boolean valid(Solitaire theGame) {
		return deck.empty();
	}

}
