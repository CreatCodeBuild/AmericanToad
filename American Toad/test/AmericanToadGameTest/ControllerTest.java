package AmericanToadGameTest;

import AmericanToadGame.AmericanToad;
import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;

public class ControllerTest extends TestCase {
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
		
	}
	
	public void testFoundationController() {
		
	}
	
	public void testReservePileController() {
		
	}
	
	public void TableauController() {
		
	}
	
	public void WastePileController() {
		
	}
}
