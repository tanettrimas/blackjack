package game

import card.Card
import card.Shoe
import card.Face

interface Hand : Comparable<Hand> {
    operator fun contains(card: Card): Boolean
    fun total(): Int
    fun split(shoe: Shoe): List<Hand>
    fun hit(shoe: Shoe): Hand
    fun isBust(): Boolean
    fun isSplittable(): Boolean
    fun hasAce(): Boolean

    val size: Int
    val splitted: Boolean
    val totalAces: Int
}

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
        return "Hand(cards=$cards)"
    }

    override fun hasAce() = totalAces > 0
    override val size: Int
        get() = cards.size
    override val splitted: Boolean
        get() = hasSplit
    override val totalAces: Int
        get() = cards.count { it.rank == Face.Ace }

}
private fun List<Card>.isSplittable() = this.size == 2 && this[0].rank.value() == this[1].rank.value()