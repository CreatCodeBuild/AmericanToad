package ks.tests.model;

import java.util.StringTokenizer;

import ks.common.model.Card;
import ks.common.model.Stack;

/**
 * Test Framework for Element classes.
 * 
 * @author George Heineman
 */
public class ModelFactory {

	/**
	 * Initialize a stack to contain a given sequence of cards.
	 * 
	 * s contains Card abbreviations with separated spaces to determine cards
	 * that are added to the stack.
	 * 
	 * Works for any subclass of Stack. For example, given a Pile p the following
	 * invocation init (p, "3S 4C 5H") removes all of its elements and replaces
	 * it instead with a three-card stack, with the three of Spades on the 
	 * bottom and the five of hearts on the top.  
	 * 
	 * [6:50 PM 3-26-08]
	 * Any existing cards in st are cleared out prior to being initialized.
	 */ 
	public static void init (Stack st, String s) {
		st.removeAll();
		
		// pull each card out of the space-separated string and add to pile
		StringTokenizer toks = new StringTokenizer(s);
		while (toks.hasMoreTokens()) {
			st.add(fromString(toks.nextToken()));
		}
	}
	
	/** 
	 * [12:28 PM 3-26-08]
	 */
	public static Card fromString (String s) {
		String suitS;

		int rank;
		int suit;

		// the only two-digit representation
		if (s.startsWith("10")) {
			rank = 10;
			suitS = s.substring(2);
		} else {
			// Figure out rest of rank. 
			char rankC = s.charAt(0);
			suitS = s.substring(1);
			if (rankC == Card.ACEabbreviation.charAt(0)) {
				rank = 1;
			} else if (rankC == Card.KINGabbreviation.charAt(0)) {
				rank = 13;
			} else if (rankC == Card.QUEENabbreviation.charAt(0)) {
				rank = 12;
			} else if (rankC == Card.JACKabbreviation.charAt(0)) {
				rank = 11;
			} else {
				rank = rankC - '0';
			}
		}

		if (suitS.equals(Card.CLUBSabbreviation)) {
			suit = Card.CLUBS;
		} else if (suitS.equals (Card.DIAMONDSabbreviation)) {
			suit = Card.DIAMONDS;
		} else if (suitS.equals (Card.HEARTSabbreviation)) {
			suit = Card.HEARTS;
		} else if (suitS.equals (Card.SPADESabbreviation)) {
			suit = Card.SPADES;
		} else {
			suit = -1;
		}
		
		return new Card (rank, suit);
	}
}
