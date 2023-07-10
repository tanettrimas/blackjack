package card

import card.Deck
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class DeckTest {
    @Test
    fun `has correct size`() {
        assertEquals(52, Deck().size)
    }

    @Test
    fun `can be shuffled`() {
        val deck = Deck()
        assertNotEquals(deck, deck.shuffled())
    }
}