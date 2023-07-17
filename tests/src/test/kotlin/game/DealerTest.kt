package game

import card.Face
import card.Shoe
import card.Suit
import card.of
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DealerTest {
    @Test
    fun testTotal() {
        val hand = BlackjackHand(10 of Suit.Hearts, Face.Ace of Suit.Spades)
        val dealer = Dealer(hand)
        assertEquals(21, dealer.total())
    }

    @Test
    fun testActions() {
        val hand = BlackjackHand(10 of Suit.Hearts, Face.Ace of Suit.Spades)
        val dealer = Dealer(hand)
        val actions = dealer.actions()
        assertEquals(2, actions.size)
        assertTrue(actions.contains(Action.Hit))
        assertTrue(actions.contains(Action.Stand))
    }

    @Test
    fun testPlayHit() {
        val shoe = Shoe(cards = mutableListOf(2 of Suit.Hearts, 5 of Suit.Spades))
        val hand = BlackjackHand(10 of Suit.Hearts, 7 of Suit.Spades)
        val dealer = Dealer(hand)
        dealer.play(Action.Hit, shoe)
        assertEquals(19, dealer.total())
    }

    @Test
    fun testPlayStand() {
        val shoe = Shoe(cards = mutableListOf(2 of Suit.Hearts, 5 of Suit.Spades))
        val hand = BlackjackHand(10 of Suit.Hearts, 7 of Suit.Spades)
        val dealer = Dealer(hand)
        dealer.play(Action.Stand, shoe)
        assertEquals(17, dealer.total())
    }

    @Test
    fun testHasBlackjack() {
        val hand = BlackjackHand(10 of Suit.Hearts, Face.Ace of Suit.Spades)
        val dealer = Dealer(hand)
        assertTrue(dealer.hasBlackjack())
    }

    @Test
    fun testIsFinished() {
        val shoe = Shoe(cards = mutableListOf(2 of Suit.Hearts, 5 of Suit.Spades))
        val hand = BlackjackHand(10 of Suit.Hearts, 7 of Suit.Spades)
        val dealer = Dealer(hand)
        assertFalse(dealer.isFinished())
        dealer.play(Action.Hit, shoe)
        assertTrue(dealer.isFinished())
        assertTrue(dealer.actions().isEmpty())
    }

    @Test
    fun testPrintCards() {
        val shoe = Shoe(cards = mutableListOf(2 of Suit.Hearts, 5 of Suit.Spades))
        val hand = BlackjackHand(10 of Suit.Hearts, 7 of Suit.Spades)
        val dealer = Dealer(hand)
        assertEquals("[10 of Hearts, <HIDDEN>]", dealer.printCards())
        dealer.play(Action.Hit, shoe)
        assertEquals("[10 of Hearts, 7 of Spades, 2 of Hearts]", dealer.printCards())
    }
}