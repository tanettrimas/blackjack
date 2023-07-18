package game

import card.Card
import card.Shoe

interface Hand : Comparable<Hand> {
    operator fun contains(card: Card): Boolean
    fun total(): Int
    fun split(shoe: Shoe): List<Hand>
    fun hit(shoe: Shoe): Hand
    fun isBust(): Boolean
    fun isSplittable(): Boolean
    fun hasAce(): Boolean
    fun splitted(): Boolean

    val size: Int
}