package game

import card.Face.Ace
import card.Suit.*
import card.of
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HandTest {

    @Test
    fun testTotalWithNoAce() {
        val card1 = 5 of Spades
        val card2 = 10 of Hearts
        val card3 = 7 of Diamonds

        val hand = Hand(card1, card2, card3)

        val expectedTotal = 22
        val actualTotal = hand.total()

        assertEquals(expectedTotal, actualTotal)
    }

    @Test
    fun testTotalWithAceValueOf11() {
        val card1 = Ace of Clubs
        val card2 = 9 of Hearts

        val hand = Hand(card1, card2)

        val expectedTotal = 20
        val actualTotal = hand.total()

        assertEquals(expectedTotal, actualTotal)
    }

    @Test
    fun testTotalWithAceValueOf1() {
        val card1 = Ace of Diamonds
        val card2 = 7 of Spades
        val card3 = 5 of Hearts

        val hand = Hand(card1, card2, card3)

        val expectedTotal = 13
        val actualTotal = hand.total()

        assertEquals(expectedTotal, actualTotal)
    }

    @Test
    fun testTotalWithMultipleAces() {
        val card1 = Ace of Hearts
        val card2 = 6 of Clubs
        val card3 = Ace of Diamonds

        val hand = Hand(card1, card2, card3)

        val expectedTotal = 18
        val actualTotal = hand.total()

        assertEquals(expectedTotal, actualTotal)
    }

    @Test
    fun testTotalWithBust() {
        val card1 = 10 of Spades
        val card2 = 8 of Hearts
        val card3 = 7 of Clubs

        val hand = Hand(card1, card2, card3)

        val expectedTotal = 25
        val actualTotal = hand.total()

        assertEquals(expectedTotal, actualTotal)
    }
}