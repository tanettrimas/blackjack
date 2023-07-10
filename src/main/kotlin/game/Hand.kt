package game

import card.Card
import card.Face

class Hand(private val cards: List<Card>) {
    constructor(vararg card: Card) : this(card.toList())
    fun total(): Int {
        val sum = cards.sumOf { it.rank.value() }
        val hasAceInHand = cards.hasAce()
        return if (hasAceInHand && sum <= 11) {
            sum + 10
        } else {
            sum
        }
    }
}

private fun List<Card>.hasAce() = this.find { it.rank == Face.Ace } != null