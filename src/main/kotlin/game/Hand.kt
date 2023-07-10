package game

import card.Card
import card.Shoe
import card.Face

class Hand(private val cards: List<Card>, private val splitted: Boolean = false) {
    constructor(vararg cards: Card, splitted: Boolean = false) : this(cards.toList(), splitted)
    constructor(shoe: Shoe) : this(shoe.deal(), shoe.deal())

    fun total(): Int {
        val sum = cards.sumOf { it.rank.value() }
        return if (cards.hasAce() && sum <= 11) {
            sum + 10
        } else {
            sum
        }
    }

    fun split(shoe: Shoe): List<Hand> {
        require(!splitted && cards.isSplittable()) {
            "Cannot split a hand which is not splittable"
        }
        return cards.chunked(1).map { Hand(it, splitted = true).hit(shoe) }
    }

    fun hit(shoe: Shoe) = Hand(cards + shoe.deal())
}

private fun List<Card>.hasAce() = this.find { it.rank == Face.Ace } != null
private fun List<Card>.isSplittable() = this.size == 2 && this[0].rank.value() == this[1].rank.value()