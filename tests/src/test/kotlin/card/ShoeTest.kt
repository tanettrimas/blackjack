package card

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ShoeTest {
    @Test
    fun `has correct size`() {
        assertEquals(52, Shoe().size)
    }

    @Test
    fun `can deal card`() {
        val shoe = Shoe()
        shoe.deal()
        assertEquals(51, shoe.size)
    }

    @Test
    fun `can add cards to shoe`() {
        val shoe = Shoe()
        val combinedShoe = shoe + Shoe()
        assertEquals(52 * 2, combinedShoe.size)
    }

    @Test
    fun `is refreshed`() {
        val smallShoe = Shoe(cards = listOf(Face.Ace of Suit.Spades))
        assertEquals(1, smallShoe.size)
        smallShoe.deal()
        assertEquals(51, smallShoe.size)
    }
}