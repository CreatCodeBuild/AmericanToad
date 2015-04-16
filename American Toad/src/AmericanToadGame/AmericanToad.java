package AmericanToadGame;

import java.awt.Dimension;

import ks.common.games.Solitaire;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;
import ks.common.view.BuildablePileView;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.DeckView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.launcher.Main;

public class AmericanToad extends Solitaire{
	
	public static final String TAG = "AmericanToad";
	private static final String NAME = "American Toad";
	
	MultiDeck deck;
	DeckView deckView;
	
	//foundations are piles
	Pile pile1;
	Pile pile2;
	Pile pile3;
	Pile pile4;
	Pile pile5;
	Pile pile6;
	Pile pile7;
	Pile pile8;
	Pile pileWaste;
	BuildablePile pileReserve;
	
	PileView pileView1;
	PileView pileView2;
	PileView pileView3;
	PileView pileView4;
	PileView pileView5;
	PileView pileView6;
	PileView pileView7;
	PileView pileView8;
	PileView pileViewWaste;
	BuildablePileView pileViewReserve;
	
	
	//tableaus are columns
	Column column1;
	Column column2;
	Column column3;
	Column column4;
	Column column5;
	Column column6;
	Column column7;
	Column column8;
	
	ColumnView columnView1;
	ColumnView columnView2;
	ColumnView columnView3;
	ColumnView columnView4;
	ColumnView columnView5;
	ColumnView columnView6;
	ColumnView columnView7;
	ColumnView columnView8;
	
	IntegerView scoreView;
	IntegerView numLeftView;

	@Override
	public String getName() {
		
		return NAME;
	}

	@Override
	public boolean hasWon() {
		
		return false;
	}
	
	private void initModel(int seed) {
		deck = new MultiDeck("deck", 2);
		deck.create(seed);
		model.addElement (deck);   // add to our model (as defined within our superclass).
		
		/*
		 * Piles
		 */
		pileWaste = new Pile("waste");
		model.addElement(pileWaste);
		
		pileReserve = new BuildablePile("pileReserve");
		model.addElement(pileReserve);
		
		pile1 = new Pile("foundation1");
		model.addElement(pile1);	
		pile2 = new Pile("foundation2");
		model.addElement(pile2);	
		pile3 = new Pile("foundation3");
		model.addElement(pile3);
		pile4 = new Pile("foundation4");
		model.addElement(pile4);
		pile5 = new Pile("foundation5");
		model.addElement(pile5);
		pile6 = new Pile("foundation6");
		model.addElement(pile6);
		pile7 = new Pile("foundation7");
		model.addElement(pile7);
		pile8 = new Pile("foundation8");
		model.addElement(pile8);
		
		for(int i = 0; i < 20; i++) {
			Card c = deck.get();

			c.setFaceUp(true);
			pileReserve.add (c);
		}
		
		//end piles
		
		/*
		 * columns
		 */
		
		
		//end columns
		
		updateNumberCardsLeft(52);
		updateScore(0);
	}
	
	private void initViews() {
		CardImages ci = getCardImages();
		int wid = ci.getWidth();
		int hei = ci.getHeight();
		
		deckView = new DeckView (deck);
		deckView.setBounds (100,20, wid, hei);
		container.addWidget (deckView);
		
		pileViewWaste = new PileView(pileWaste);
		pileViewWaste.setBounds(180, 20, wid, hei);
		container.addWidget(pileViewWaste);
		
		pileViewReserve = new BuildablePileView(pileReserve);
		pileViewReserve.setBounds(20,20, wid, hei*20);
		container.addWidget(pileViewReserve);
		
		pileView1 = new PileView(pile1);
		pileView1.setBounds(180,120, wid, hei);
		container.addWidget(pileView1);
		
		pileView2 = new PileView(pile2);
		pileView2.setBounds(260,120, wid, hei);
		container.addWidget(pileView2);
		
		pileView3 = new PileView(pile3);
		pileView3.setBounds(340,120, wid, hei);
		container.addWidget(pileView3);
		
		pileView4 = new PileView(pile4);
		pileView4.setBounds(420,120, wid, hei);
		container.addWidget(pileView4);
		
		pileView5 = new PileView(pile5);
		pileView5.setBounds(500,120, wid, hei);
		container.addWidget(pileView5);
		
		pileView6 = new PileView(pile6);
		pileView6.setBounds(580,120, wid, hei);
		container.addWidget(pileView6);
		
		pileView7 = new PileView(pile7);
		pileView7.setBounds(660,120, wid, hei);
		container.addWidget(pileView7);
		
		pileView8 = new PileView(pile8);
		pileView8.setBounds(740,120, wid, hei);
		container.addWidget(pileView8);
	}
	
	private void initControllers() {
		
	}

	@Override
	public void initialize() {
		initModel(getSeed());
		initViews();
		initControllers();
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension (1200, 600);
	}
	
	/** Code to launch solitaire variation. */
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		Main.generateWindow(new AmericanToad(), Deck.OrderBySuit);
	}

}
