package game

import card.Shoe

internal class FinishedHand(hand: Hand): Hand by hand {
    override fun isSplittable(): Boolean {
        return false
    }

    override fun hit(shoe: Shoe): Hand {
        return this
    }

    override fun split(shoe: Shoe): List<Hand> {
        error("Cannot split a finsihed hand")
    }
}