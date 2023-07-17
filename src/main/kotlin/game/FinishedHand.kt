package game

import card.Shoe

internal class FinishedHand(private val hand: Hand): Hand by hand {
    override fun isSplittable(): Boolean {
        return false
    }

    override fun hit(shoe: Shoe): Hand {
        return this
    }

    override fun split(shoe: Shoe): List<Hand> {
        error("Cannot split a finsihed hand")
    }

    override fun toString() = hand.toString()
}