package game

import card.Card
import card.Shoe
import card.Face

class Hand private constructor(private val cards: List<Card>, private val hasSplit: Boolean = false) : Comparable<Hand> {
    constructor(vararg cards: Card, splitted: Boolean = false) : this(cards.toList(), splitted)
    constructor(shoe: Shoe) : this(shoe.deal(), shoe.deal())

    operator fun contains(card: Card) = cards.contains(card)

    fun total(): Int {
        val sum = cards.sumOf { it.rank.value() }
        return if (cards.hasAce() && sum <= 11) {
            sum + 10
        } else {
            sum
        }
    }

    fun split(shoe: Shoe): List<Hand> {
        if(hasSplit || !cards.isSplittable()) {
            error("Cannot split a hand which is not splittable")
        }
        return cards.chunked(1).map { Hand(it, hasSplit = true).hit(shoe) }
    }

    fun hit(shoe: Shoe) = Hand(cards + shoe.deal(), hasSplit = hasSplit)

    private fun isBust() = total() > 21

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

    fun hasBlackjack() = !hasSplit && cards.size == 2 && total() == 21

}

private fun List<Card>.hasAce() = this.find { it.rank == Face.Ace } != null
private fun List<Card>.isSplittable() = this.size == 2 && this[0].rank.value() == this[1].rank.value()