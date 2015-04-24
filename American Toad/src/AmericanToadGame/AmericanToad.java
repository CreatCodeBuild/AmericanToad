package AmericanToadGame;

import java.awt.Dimension;

import AmericanToadController.DeckController;
import AmericanToadController.FoundationController;
import AmericanToadController.ReservePileController;
import AmericanToadController.TableauController;
import AmericanToadController.WastePileController;
import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.MultiDeck;
import ks.common.model.MutableInteger;
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
	
	public MultiDeck deck;
	public DeckView deckView;
	
	//foundations are piles
	public Pile foundation[] = new Pile[8];
	public Pile pileWaste;
	public BuildablePile pileReserve;
	
	public PileView foundationView[] = new PileView[8];
	public PileView pileViewWaste;
	public BuildablePileView pileViewReserve;
	
	//tableaus are columns
	public Column tableau[] = new Column[8];
	public ColumnView tableauView[] = new ColumnView[8];

	IntegerView baseRankView;
	MutableInteger baseRank;
	
	IntegerView scoreView;
	public int score = 0;

	public MutableInteger getBaseRank() {
		return baseRank;
	}

	public void setBaseRank(MutableInteger baseRank) {
		this.baseRank = baseRank;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean hasWon() {
		if(score == 104) {
			return true;
		}
		return false;
	}
	
	private void initModel(int seed) {
		deck = new MultiDeck("deck", 2);
		deck.create(seed);
		model.addElement (deck);   // add to our model (as defined within our superclass).

		Card cardWaste = deck.get();
		pileWaste = new Pile("waste pile");
		pileWaste.add(cardWaste);
		model.addElement(pileWaste);
		
		pileReserve = new BuildablePile("pileReserve");
		for(int i = 0; i < 20; i++) {
			Card c = deck.get();
			c.setFaceUp(true);
			pileReserve.add (c);
		}
		model.addElement(pileReserve);

		Card cardBase = deck.get();
		baseRank = new MutableInteger(cardBase.getRank());
		
		for(int i = 0; i < 8; i++) {
			foundation[i] = new Pile("foundation" + i);
			if(i == 0) { foundation[i].add(cardBase); }
			model.addElement(foundation[i]);
		}
		for(int i = 0; i < 8; i++) {
			tableau[i] = new Column("tableau" + i);
			Card c = deck.get();
			tableau[i].add(c);
			model.addElement(tableau[i]);
		}
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
		pileViewReserve.setBounds(20,20, wid, hei*6);
		container.addWidget(pileViewReserve);
		
		for(int i = 0; i < 8; i++) {
			foundationView[i] = new PileView(foundation[i]);
			foundationView[i].setBounds(180 + i*80, 120, wid, hei);
			container.addWidget(foundationView[i]);
		}
		for(int i = 0; i < 8; i++) {
			tableauView[i] = new ColumnView(tableau[i]);
			tableauView[i].setBounds(180 + i*80, 220, wid, hei*3);
			container.addWidget(tableauView[i]);
		}
		baseRankView = new IntegerView(baseRank);
		this.baseRankView.setBounds(740,20, wid, hei);
		container.addWidget(baseRankView);
	}
	
	private void initControllers() {
		deckView.setMouseAdapter(new DeckController (this, deck, pileWaste));
		deckView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		deckView.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		pileViewReserve.setMouseAdapter(new ReservePileController(this, pileViewReserve));
		pileViewReserve.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		pileViewReserve.setUndoAdapter(new SolitaireUndoAdapter(this));
		
		pileViewWaste.setMouseAdapter(new WastePileController(this, pileViewWaste));
		pileViewWaste.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		pileViewWaste.setUndoAdapter(new SolitaireUndoAdapter(this));
		
		for(int i = 0; i < 8; i++) {
			foundationView[i].setMouseAdapter (new FoundationController (this, foundationView[i]));
			foundationView[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
			foundationView[i].setUndoAdapter (new SolitaireUndoAdapter(this));
		
			tableauView[i].setMouseAdapter(new TableauController(this, tableauView[i]));
			tableauView[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
			tableauView[i].setUndoAdapter (new SolitaireUndoAdapter(this));
		}
	}

	@Override
	public void initialize() {
		initModel(getSeed());
		initViews();
		initControllers();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension (900, 650);
	}
	
	/** Code to launch solitaire variation. */
	public static void main (String []args) {
		Main.generateWindow(new AmericanToad(), Deck.OrderBySuit);
	}

}
