package card

class Shoe(private val shoeSize: Int = 1, cards: List<Card> = emptyList()) {
    private val cards = cards.toMutableList().apply { addAll(fromShoeSize(shoeSize)) }

    companion object {
        private fun createCards() = Suit
            .values()
            .fold(listOf<Card>()) { acc, suit ->
                val cards = createCards(suit)
                acc + cards
            }.shuffled()

        private fun createCards(suit: Suit) = CardNumber.range(suit) + Face.range(suit)

        private fun fromShoeSize(shoeSize: Int): List<Card> {
            require(shoeSize >= 1)
            return (0 until shoeSize).flatMap { createCards() }
        }

        private const val REFRESH_THRESHOLD = 0.5
    }

    private fun needsRefresh() = cards.size < (52 * shoeSize) * REFRESH_THRESHOLD

    private fun refreshIfPossible() {
        if (needsRefresh()) {
            cards.clear()
            cards.addAll(fromShoeSize(shoeSize))
        }
    }

    val size: Int get() = cards.size

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Shoe

        if (cards != other.cards) return false
        return size == other.size
    }

    override fun hashCode(): Int {
        var result = cards.hashCode()
        result = 31 * result + size
        return result
    }

    fun deal(): Card {
        refreshIfPossible()
        return cards.removeFirst()
    }

    operator fun plus(shoe: Shoe): Shoe {
        return Shoe(cards = shoe.cards)
    }
}
