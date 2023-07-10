package card

class Shoe(cards: List<Card> = createCards()) {
    private val cards = cards.toMutableList()

    companion object {
        private fun createCards() = Suit
            .values()
            .fold(listOf<Card>()) { acc, suit ->
                val cards = createCard(suit)
                acc + cards
            }.shuffled()

        private fun createCard(suit: Suit) = CardNumber.range(suit) + Face.range(suit)
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

    fun deal() = cards.removeFirst()
}
