package AmericanToadGameTest;

import AmericanToadGame.AmericanToad;
import AmericanToadGame.MoveCardFromReservePileToFoundation;
import AmericanToadGame.MoveCardFromReservePileToTableau;
import AmericanToadGame.MoveDealCard;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class AmericanToadTest extends KSTestCase {	
	// this is the game under test.
	AmericanToad game;
		
	// window for game.
	GameWindow gw;
		
	protected void setUp() {
		game = new AmericanToad();
		gw = Main.generateWindow(game, Deck.OrderBySuit); 
		
	}
		
	protected void tearDown() {
		gw.setVisible(false);
		gw.dispose();
	}
		
	public void testMoveDealCard() {
		MoveDealCard deal = new MoveDealCard(game.deck, game.pileWaste);
		assertTrue(game.pileWaste.count() == 1);
		assertTrue (deal.valid(game));
		assertTrue (deal.doMove(game));
		assertTrue(deal.undo(game));
		
		game.deck.removeAll();
		assertFalse (deal.valid(game));
		assertFalse (deal.doMove(game));
	}
	
	public void testMoveCardFromeReservePileToFoundation() {
		int suit = game.foundation[0].peek().getSuit();
		int rank = game.foundation[0].peek().getRank();
		MoveCardFromReservePileToFoundation move = 
		new MoveCardFromReservePileToFoundation(game.pileReserve, game.foundation[0], new Card(rank+1, suit), game.getBaseRank().getValue());

		assertTrue(game.foundation[0].count() == 1);
		assertTrue(move.valid(game));
		assertTrue(move.doMove(game));
		assertTrue(move.undo(game));
	}
	
	public void testMoveCardFromReservePileTotTableau() {;
		int suit = game.tableau[1].peek().getSuit();
		int rank = game.tableau[1].peek().getRank();
		MoveCardFromReservePileToTableau move =
		new MoveCardFromReservePileToTableau(game.pileReserve, game.tableau[1], new Card(rank-1, suit));
	
		assertTrue(move.valid(game));
		assertTrue(move.doMove(game));
		assertTrue(move.undo(game));
	}
	
	public void testMoveCardFromTableauToFoundation() {
		int suit;
		int rank;
	}

}
