package AmericanToadGameTest;

import java.awt.event.MouseEvent;

import AmericanToadGame.AmericanToad;
import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class ControllerTest extends KSTestCase {
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
	
	public void testDeckController() {
		// first create a mouse event
		MouseEvent pr = createPressed (game, game.deckView, 0, 0);
		game.deckView.getMouseManager().handleMouseEvent(pr);
		
		assertEquals (new Card(Card.NINE, Card.DIAMONDS), game.pileWaste.peek());
		assertTrue (game.undoMove());
		assertEquals (game.pileWaste.count(), 1);
	}
	
	public void testFoundationController() {
//		MouseEvent pr = createReleased (game, game.foundationView[0], 0, 0);
//		game.foundationView[0].getMouseManager().handleMouseEvent(pr);
		
		//from reverse to foundation 0
		MouseEvent reservePress = createPressed(game, game.pileViewReserve, 0, game.pileViewReserve.getHeight()- 100);
		game.pileViewReserve.getMouseManager().handleMouseEvent(reservePress);
		
		MouseEvent f1Release = createReleased (game, game.foundationView[0], 20, 20);
		game.foundationView[0].getMouseManager().handleMouseEvent(f1Release);
		
		//from waste
		for(int i = 0; i < 5; i++) {
			MouseEvent pr = createPressed (game, game.deckView, 0, 0);
			game.deckView.getMouseManager().handleMouseEvent(pr);
		}
		
		MouseEvent wastePress = createPressed(game, game.pileViewWaste, 0, 0);
		game.pileViewWaste.getMouseManager().handleMouseEvent(wastePress);
		
		MouseEvent f2Release = createReleased (game, game.foundationView[1], 20, 20);
		game.foundationView[1].getMouseManager().handleMouseEvent(f2Release);
		
		for(int i = 0; i < 4; i++) {
			game.pileViewWaste.getMouseManager().handleMouseEvent(wastePress);
			game.foundationView[1].getMouseManager().handleMouseEvent(f2Release);	
		}
		
		//from tableau
		MouseEvent tableauPress = createPressed(game, game.tableauView[7], 0, 0);
		game.tableauView[7].getMouseManager().handleMouseEvent(tableauPress);
		
		game.foundationView[1].getMouseManager().handleMouseEvent(f2Release);

	}
	
	public void TableauController() {
		//from waste
		
		//from reserve
		
		//from tableau
	}
}
