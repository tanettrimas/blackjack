package game

import card.Face.Ace
import card.Face.Queen
import card.Shoe
import card.Suit.*
import card.of
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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

    @Test
    fun `hand from shoe`() {
        val shoe = Shoe()
        val hand = Hand(shoe = shoe)
        assertTrue(hand.total() in 1..21) // Checks that cards have actually been dealt to the hand
    }

    @Test
    fun `can split a hand`() {
        val shoe = Shoe() + Shoe(cards = listOf(Ace of Spades, Ace of Diamonds))
        val hand = Hand(shoe = shoe)
        assertEquals(12, hand.total())
        val (first, second) = hand.split(shoe)
        assertTrue(Ace of Spades in first)
        assertTrue(Ace of Diamonds in second)
        assertThrows<IllegalStateException> {
            first.split(shoe)
        }
        assertThrows<IllegalStateException> {
            second.split(shoe)
        }
    }

    @Test
    fun `cannot split a hand that is already split, even if the card value are equal`() {
        val shoe = Shoe() + Shoe(cards = listOf(Ace of Spades, Ace of Diamonds, Ace of Spades, Ace of Diamonds))
        val hand = Hand(shoe = shoe)
        val (first, second) = hand.split(shoe)
        assertThrows<IllegalStateException> {
            first.split(shoe)
        }
        assertThrows<IllegalStateException> {
            second.split(shoe)
        }
    }

    @Test
    fun `can hit a hand`() {
        val shoe = Shoe() + Shoe(cards = listOf(8 of Hearts, 2 of Spades, Ace of Spades))
        var hand = Hand(shoe)
        hand = hand.hit(shoe)
        assertEquals(21, hand.total())
        assertFalse(hand.hasBlackjack())
    }

    @Test
    fun `does not have blackjack after split`() {
        val shoe = Shoe() + Shoe(cards = listOf(Ace of Spades, Ace of Hearts, Queen of Diamonds, 10 of Diamonds))
        val hand = Hand(shoe = shoe)
        val (first, second) = hand.split(shoe)
        assertEquals(21, first.total())
        assertEquals(21, second.total())
        assertFalse(first.hasBlackjack())
        assertFalse(second.hasBlackjack())
    }

    @Test
    fun `has blackjack`() {
        val shoe = Shoe() + Shoe(cards = listOf(Ace of Spades, 10 of Diamonds))
        val hand = Hand(shoe = shoe)
        println(hand.total())
        assertTrue(hand.hasBlackjack())
    }

    @Test
    fun `has tie with equal score`() {
        val shoe = Shoe() + Shoe(cards = listOf(Ace of Spades, Queen of Diamonds, Ace of Hearts, 10 of Diamonds))
        val player = Hand(shoe)
        val dealer = Hand(shoe)
        assertTrue(player.compareTo(dealer) == 0)
    }

    @Test
    fun `can determine winner`() {
        val shoe = Shoe() + Shoe(cards = listOf(Ace of Spades, Queen of Diamonds, Ace of Hearts, 10 of Diamonds))
        val player = Hand(shoe).hit(shoe) // Wil get over 21 and bust
        val dealer = Hand(shoe)
        assertTrue(player < dealer)
    }
}