package game

import card.Card
import card.Shoe
import card.Face

class BlackjackHand private constructor(private val cards: List<Card>, private val hasSplit: Boolean = false) : Hand {
    constructor(vararg cards: Card, splitted: Boolean = false) : this(cards.toList(), splitted)
    constructor(shoe: Shoe) : this(shoe.deal(), shoe.deal())

    override operator fun contains(card: Card) = cards.contains(card)

    override fun total(): Int {
        return cards.sumOf { it.rank.value() }
    }

    override fun split(shoe: Shoe): List<BlackjackHand> {
        if(hasSplit || !cards.isSplittable()) {
            error("Cannot split a hand which is not splittable")
        }
        return cards.chunked(1).map { BlackjackHand(it, hasSplit = true).hit(shoe) }
    }

    override fun hit(shoe: Shoe) = BlackjackHand(cards + shoe.deal(), hasSplit = hasSplit)

    override fun isBust() = total() > 21
    override fun isSplittable() = cards.isSplittable()

    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: Hand): Int {
        if (isBust() && !other.isBust()) return -1
        else if (!isBust() && other.isBust()) return 1
        return this.total().compareTo(other.total())
    }

    override fun toString(): String {
        return "$cards"
    }

    override fun hasAce() = cards.count { it.rank == Face.Ace } > 0
    override fun splitted() = hasSplit

    override val size: Int
        get() = cards.size
}
private fun List<Card>.isSplittable(): Boolean {
    return this.size == 2 && this[0].rank == this[1].rank
}