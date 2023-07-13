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
        val shoe = Shoe()
        shoe.deal()
        shoe.deal()
        assertEquals(50, shoe.size)
        while (shoe.size <= 50) {
            shoe.deal()
        }
        assertEquals(51, shoe.size) // Shoe is refreshed after some time
    }
}