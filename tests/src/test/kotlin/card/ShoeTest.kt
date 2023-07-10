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
}