package game

import card.Face.*
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

        val hand = BlackjackHand(card1, card2, card3)

        val expectedTotal = 22
        val actualTotal = hand.total()

        assertEquals(expectedTotal, actualTotal)
    }

    @Test
    fun testTotalWithAceValueOf11() {
        val card1 = Ace of Clubs
        val card2 = 9 of Hearts

        val hand = player {
            BlackjackHand(card1, card2)
        }

        val expectedTotal = 20
        val actualTotal = hand.total()

        assertEquals(expectedTotal, actualTotal)
    }

    @Test
    fun testTotalWithAceValueOf1() {
        val card1 = Ace of Diamonds
        val card2 = 7 of Spades
        val card3 = 5 of Hearts

        val hand = player {  BlackjackHand(card1, card2, card3) }.apply { assignAce(AceAssignment.ONE) }

        val expectedTotal = 13
        val actualTotal = hand.total()

        assertEquals(expectedTotal, actualTotal)
    }

    @Test
    fun testTotalWithMultipleAces() {
        val card1 = Ace of Hearts
        val card2 = 6 of Clubs
        val card3 = Ace of Diamonds

        val hand = player { BlackjackHand(card1, card2, card3) }

        val expectedTotal = 18
        val actualTotal = hand.total()

        assertEquals(expectedTotal, actualTotal)
    }

    @Test
    fun testTotalWithBust() {
        val card1 = 10 of Spades
        val card2 = 8 of Hearts
        val card3 = 7 of Clubs

        val hand = player { BlackjackHand(card1, card2, card3) }

        val expectedTotal = 25
        val actualTotal = hand.total()

        assertEquals(expectedTotal, actualTotal)
        assertTrue(hand.isBust())
        assertTrue(hand.isFinished())
    }

    @Test
    fun `hand from shoe`() {
        val shoe = Shoe()
        val hand = BlackjackHand(shoe = shoe)
        assertTrue(hand.total() in 1..21) // Checks that cards have actually been dealt to the hand
    }

    @Test
    fun `can split a hand`() {
        val shoe = Shoe(cards = listOf(10 of Spades, 10 of Diamonds))
        val hand = BlackjackHand(shoe = shoe)
        assertEquals(20, hand.total())
        val (first, second) = hand.split(shoe)
        assertTrue(10 of Spades in first)
        assertTrue(10 of Diamonds in second)
        assertThrows<IllegalStateException> {
            first.split(shoe)
        }
        assertThrows<IllegalStateException> {
            second.split(shoe)
        }
    }

    @Test
    fun `cannot split a hand that is already split, even if the card value are equal`() {
        val shoe = Shoe(cards = listOf(10 of Spades, 10 of Diamonds, 10 of Spades, 10 of Diamonds))
        val hand = BlackjackHand(shoe = shoe)
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
        val shoe = Shoe(cards = listOf(8 of Hearts, 2 of Spades, Ace of Spades))
        val hand = player { BlackjackHand(shoe) }
        hand.play(Action.Hit, shoe)
        assertEquals(21, hand.total())
        assertFalse(hand.hasBlackjack())
    }

    @Test
    fun `does not have blackjack after split`() {
        val shoe = Shoe(cards = listOf(10 of Spades, 10 of Diamonds, 10 of Spades, 10 of Diamonds))
        val hand = BlackjackHand(shoe = shoe)
        val (first, second) = hand.split(shoe)
        assertEquals(20, first.total())
        assertEquals(20, second.total())
    }

    @Test
    fun `has blackjack`() {
        val shoe = Shoe(cards = listOf(Ace of Spades, 10 of Diamonds))
        val hand = RegularPlayer(listOf(BlackjackHand(shoe = shoe))).apply { assignAce(AceAssignment.ELEVEN) }
        assertTrue(hand.hasBlackjack())
    }

    @Test
    fun `has tie with equal score`() {
        val shoe = Shoe(cards = listOf(Ace of Spades, Queen of Diamonds, Ace of Hearts, 10 of Diamonds))
        val player = BlackjackHand(shoe)
        val dealer = BlackjackHand(shoe)
        assertTrue(player.compareTo(dealer) == 0)
    }

    @Test
    fun `can determine winner`() {
        val shoe = Shoe(cards = listOf(Ace of Spades, Queen of Diamonds, Ace of Hearts, 10 of Diamonds))
        val player = player { BlackjackHand(shoe) }.apply {
            play(Action.Hit, shoe)
        }

        val dealer = Dealer(BlackjackHand(shoe))
        assertEquals(ScoreResult.Lose, player.scoreAgainst(dealer))
        assertTrue(player.isFinished())
        assertTrue(player.isBust())
    }

    @Test
    fun `loses if one player has blackjack and the other gets 21 after hit`() {
        val shoe = Shoe(cards = listOf(6 of Clubs))
        val player = player { BlackjackHand(Ace of Spades, 10 of Hearts) }
        val dealer = Dealer(BlackjackHand(10 of Hearts, 5 of Clubs))
        dealer.play(Action.Hit, shoe)
        assertEquals(ScoreResult.Win, player.scoreAgainst(dealer))
        assertEquals(player.total(), dealer.total())
        assertTrue(player.hasBlackjack())
        assertFalse(dealer.hasBlackjack())
    }

    @Test
    fun `cannot split two unequal face cards`() {
        val handOne = BlackjackHand(Jack of Hearts, Queen of Spades)
        assertFalse(handOne.isSplittable())
    }
}

private fun player(hand: () -> Hand): RegularPlayer {
    return RegularPlayer(listOf(hand())).apply { assignAce(AceAssignment.ELEVEN) }
}