package card

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class RankTest {

    @Test
    fun testFaceValue() {
        val jack = Face.Jack
        val king = Face.King
        val queen = Face.Queen
        val ace = Face.Ace

        val jackValue = jack.value()
        val kingValue = king.value()
        val queenValue = queen.value()
        val aceValue = ace.value()

        assertEquals(10, jackValue)
        assertEquals(10, kingValue)
        assertEquals(10, queenValue)
        assertEquals(1, aceValue)
    }

    @Test
    fun testNumberValue() {
        val cardNumber5 = CardNumber(5)
        val cardNumber9 = CardNumber(9)

        val number5Value = cardNumber5.value()
        val number9Value = cardNumber9.value()

        assertEquals(5, number5Value)
        assertEquals(9, number9Value)
    }

    @Test
    fun `throws on invalid number`() {
        assertThrows<IllegalArgumentException>("Invalid number provided") {
            CardNumber(1)
        }
        assertThrows<IllegalArgumentException>("Invalid number provided") {
            CardNumber(11)
        }
        assertDoesNotThrow {
            CardNumber(10)
        }
        assertDoesNotThrow {
            CardNumber(2)
        }
    }
}