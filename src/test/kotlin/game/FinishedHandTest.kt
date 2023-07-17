package game

import card.Face.Ace
import card.Face.King
import card.Shoe
import card.Suit.*
import card.of
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FinishedHandTest {
    @Test
    fun testIsSplittable() {
        val hand = FinishedHand(BlackjackHand(2 of Hearts, 2 of Spades))
        assertFalse(hand.isSplittable())
    }

    @Test
    fun testHit() {
        val hand = FinishedHand(BlackjackHand(10 of Hearts, King of Diamonds))
        val shoe = Shoe(cards = mutableListOf(2 of Hearts))
        val newHand = hand.hit(shoe)
        assertTrue(newHand is FinishedHand)
        assertEquals(hand.toString(), newHand.toString())
    }

    @Test
    fun testSplit() {
        val hand = FinishedHand(BlackjackHand(2 of Hearts, 2 of Spades))
        val shoe = Shoe(cards = mutableListOf(Ace of Hearts, Ace of Spades))
        assertThrows<Exception> { hand.split(shoe) }
    }

    @Test
    fun testToString() {
        val innerHand = BlackjackHand(10 of Hearts, King of Spades)
        val hand = FinishedHand(innerHand)
        assertEquals(innerHand.toString(), hand.toString())
    }
}