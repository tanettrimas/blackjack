package card

class Deck(private val cards: List<Card> = createCards()) {
    companion object {
        private fun createCards() = Suit
            .values()
            .fold(listOf<Card>()) { acc, suit ->
                val cards = createCard(suit)
                acc + cards
            }

        private fun createCard(suit: Suit) = CardNumber.range(suit) + Face.range(suit)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Deck

        if (cards != other.cards) return false
        return size == other.size
    }
    override fun hashCode(): Int {
        var result = cards.hashCode()
        result = 31 * result + size
        return result
    }

    val size: Int = cards.size
    fun shuffled() = Deck(cards.shuffled())
}


