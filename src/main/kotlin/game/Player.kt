package game

import card.Shoe

interface Player {
    fun total(): Int
    fun actions(): List<Action>
    fun play(action: Action, shoe: Shoe)

    fun isBust() = total() > 21

    fun scoreAgainst(other: Player): ScoreResult {
        if (isBust() && !other.isBust() || other.hasBlackjack() && !hasBlackjack()) return ScoreResult.Lose
        else if (!isBust() && other.isBust() || hasBlackjack() && !other.hasBlackjack()) return ScoreResult.Win
        return ScoreResult.from(this.total().compareTo(other.total()))
    }

    fun hasBlackjack(): Boolean

    fun isFinished(): Boolean

    fun printCards(): String
}

