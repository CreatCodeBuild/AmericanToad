package AmericanToadGame;

import ks.common.games.Solitaire;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;
import ks.common.view.ColumnView;
import ks.common.view.DeckView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;

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
	Pile pileReserve;
	
	PileView pileView1;
	PileView pileView2;
	PileView pileView3;
	PileView pileView4;
	PileView pileView5;
	PileView pileView6;
	PileView pileView7;
	PileView pileView8;
	PileView pileViewWaste;
	PileView pileViewReserve;
	
	
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
		
	}
	
	private void initViews() {
		
	}
	
	private void initControllers() {
		
	}

	@Override
	public void initialize() {
		initModel(getSeed());
		initViews();
		initControllers();
		
	}

}
