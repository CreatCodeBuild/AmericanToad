package AmericanToadGameTest;

import AmericanToadGame.AmericanToad;
import ks.client.gamefactory.GameWindow;
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

}
