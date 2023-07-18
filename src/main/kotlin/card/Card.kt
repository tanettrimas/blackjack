package card

class Card(private val suit: Suit, internal val rank: Rank) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        if (suit != other.suit) return false
        return rank == other.rank
    }
    override fun hashCode(): Int {
        var result = suit.hashCode()
        result = 31 * result + rank.hashCode()
        return result
    }
    override fun toString(): String {
        mapOf(1 to 2)
        return "$rank of $suit"
    }
}

infix fun Int.of(suit: Suit) = Card(suit, CardNumber(this))
infix fun Face.of(suit: Suit) = Card(suit, this)