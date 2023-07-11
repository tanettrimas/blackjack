package card

sealed interface Rank {
    fun value(): Int
}
enum class Face : Rank {
    Jack, King, Queen, Ace;

    override fun value(): Int {
        return when(this) {
            Jack, King, Queen -> 10
            Ace -> 1
        }
    }

    companion object {
        internal fun range(suit: Suit) = values().map { it of suit }
    }
}

class CardNumber(internal val value: Int) : Rank {
    companion object {
        private val range = 2..10
        internal fun range(suit: Suit) = range.map { it of suit }
    }

    init {
        require(range.contains(value))
    }

    override fun value(): Int {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CardNumber

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "$value"
    }
}