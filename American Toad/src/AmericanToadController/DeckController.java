package AmericanToadController;

import AmericanToadGame.AmericanToad;
import AmericanToadGame.MoveDealCard;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Move;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;

public class DeckController extends SolitaireReleasedAdapter {
	
	AmericanToad theGame;
	MultiDeck deck;
	Pile pileWaste;

	public DeckController(AmericanToad theGame, MultiDeck deck, Pile pileWaste) {
		super(theGame);
		this.theGame = theGame;
		this.deck = deck;
		this.pileWaste = pileWaste;
	}
	
	public void mousePressed (java.awt.event.MouseEvent me) {
		// Attempting a DealFourCardMove
		Move m = new MoveDealCard (deck, pileWaste);
		if (m.doMove(theGame)) {
			theGame.pushMove(m);
			theGame.refreshWidgets();
 		}
	}

}
