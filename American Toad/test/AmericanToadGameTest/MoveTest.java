package AmericanToadGameTest;

import AmericanToadGame.AmericanToad;
import AmericanToadGame.MoveCardFromReservePileToFoundation;
import AmericanToadGame.MoveCardFromReservePileToTableau;
import AmericanToadGame.MoveCardFromTableauToFoundation;
import AmericanToadGame.MoveCardFromWastePileToFoundation;
import AmericanToadGame.MoveCardFromWastePileToTableau;
import AmericanToadGame.MoveColumnFromTableauToTableau;
import AmericanToadGame.MoveDealCard;
import ks.client.gamefactory.GameWindow;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class MoveTest extends KSTestCase {	
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

		assertTrue(move.valid(game));
		assertTrue(move.doMove(game));
		assertTrue(move.undo(game));
		
		MoveCardFromReservePileToFoundation move2 = 
		new MoveCardFromReservePileToFoundation(game.pileReserve, game.foundation[2], null, game.getBaseRank().getValue());
		assertFalse(move2.valid(game));
		assertFalse(move2.doMove(game));
		
		MoveCardFromReservePileToFoundation move3 = 
		new MoveCardFromReservePileToFoundation(new BuildablePile(), game.foundation[2], null, game.getBaseRank().getValue());
		assertFalse(move3.valid(game));
		assertFalse(move3.doMove(game));
		
		MoveCardFromReservePileToFoundation move4 = 
		new MoveCardFromReservePileToFoundation(game.pileReserve, new Pile(), new Card(rank, suit), game.getBaseRank().getValue());
		assertTrue(move4.valid(game));
		assertTrue(move4.doMove(game));
	}
	
	public void testMoveCardFromReservePileTotTableau() {;
		int suit = game.tableau[1].peek().getSuit();
		int rank = game.tableau[1].peek().getRank();
		MoveCardFromReservePileToTableau move =
		new MoveCardFromReservePileToTableau(game.pileReserve, game.tableau[1], new Card(rank-1, suit));
		assertTrue(move.valid(game));
		assertTrue(move.doMove(game));
		assertTrue(move.undo(game));
		
		
		MoveCardFromReservePileToTableau move2 = new MoveCardFromReservePileToTableau(game.pileReserve, game.tableau[2], null);
		assertFalse(move2.valid(game));
		assertFalse(move2.doMove(game));
		
		MoveCardFromReservePileToTableau move3 =
		new MoveCardFromReservePileToTableau(game.pileReserve, game.tableau[1], new Card(rank + 1, suit));
		assertFalse(move3.valid(game));
		assertFalse(move3.doMove(game));
		
		game.tableau[7].get();
		MoveCardFromReservePileToTableau move4 =
		new MoveCardFromReservePileToTableau(game.pileReserve, game.tableau[7], new Card(rank + 1, suit));
		assertTrue(move4.valid(game));
		assertTrue(move4.doMove(game));
	}
	
	public void testMoveCardFromTableauToFoundation() {
		int suit = game.foundation[0].peek().getSuit();
		int rank = game.foundation[0].peek().getRank();
		
		MoveCardFromTableauToFoundation move =
		new MoveCardFromTableauToFoundation(game.tableau[1], new Pile(), new Card(game.getBaseRank().getValue(), 1), game.getBaseRank().getValue());
		assertTrue(move.valid(game));
		assertTrue(move.doMove(game));
		assertTrue(move.undo(game));
		
		MoveCardFromTableauToFoundation move2 =
		new MoveCardFromTableauToFoundation(new Column(), new Pile(), null, game.getBaseRank().getValue());
		assertFalse(move2.valid(game));
		assertFalse(move2.doMove(game));
		assertFalse(move2.undo(game));
		
		MoveCardFromTableauToFoundation move3 =
		new MoveCardFromTableauToFoundation(game.tableau[1], game.foundation[0], new Card(6, suit), game.getBaseRank().getValue());
		assertTrue(move3.valid(game));
		assertTrue(move3.doMove(game));
		assertTrue(move3.undo(game));
	}

	public void testMoveCardFromWastePileToFoundation() {
		int rank = game.foundation[0].peek().getRank();
		int suit = game.foundation[0].peek().getSuit();
		
		Move move = new MoveCardFromWastePileToFoundation(new Pile(), new Pile(), new Card(rank, suit), rank);
		assertTrue(move.valid(game));
		assertTrue(move.doMove(game));
		assertTrue(move.undo(game));

		Pile p = new Pile();
		p.add(new Card(rank, suit));
		Move move2 = new MoveCardFromWastePileToFoundation(p, new Pile(), null, rank);
		assertTrue(move2.valid(game));
		assertTrue(move2.doMove(game));
		assertTrue(move2.undo(game));	

		Move move3 = new MoveCardFromWastePileToFoundation(p, p, new Card(rank+1, suit), rank);
		assertTrue(move3.valid(game));
		assertTrue(move3.doMove(game));
		assertTrue(move3.undo(game));	
	}

	public void testMoveCardFromWastePileToTableau() {
		int rank = game.tableau[0].peek().getRank();
		int suit = game.tableau[0].peek().getSuit();
		
		Move move = new MoveCardFromWastePileToTableau(new Pile(), new Column(), new Card(rank, suit));
		assertTrue(move.valid(game));
		assertTrue(move.doMove(game));
		assertTrue(move.undo(game));
		
		Pile p = new Pile();
		p.add(new Card(rank, suit));
		Move move2 = new MoveCardFromWastePileToTableau(p, new Column(), null);
		assertTrue(move2.valid(game));
		assertTrue(move2.doMove(game));
		assertTrue(move2.undo(game));
		
		Column c = new Column();
		c.add(new Card(rank+1, suit));
		Move move3 = new MoveCardFromWastePileToTableau(p, c, null);
		assertTrue(move3.valid(game));
		assertTrue(move3.doMove(game));
		assertTrue(move3.undo(game));
	}
	
	public void testMoveColumnFromTableauToTableau() {
		int rank = game.tableau[0].peek().getRank();
		int suit = game.tableau[0].peek().getSuit();
		
		Move move = new MoveColumnFromTableauToTableau(new Column(), new Column(), null, 0);
		assertTrue(move.valid(game));
		assertTrue(move.doMove(game));
		assertTrue(move.undo(game));
		
		Column c1 = new Column();
		c1.add(new Card(rank, suit));
		Column c2 = new Column();
		c2.add(new Card(rank+1, suit));
		Move move2 = new MoveColumnFromTableauToTableau(c1, c2, c1, 1);
		assertTrue(move2.valid(game));
		assertTrue(move2.doMove(game));
		assertTrue(move2.undo(game));
	}
}
