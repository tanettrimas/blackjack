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

enum class ScoreResult(private val score: Int) {
    Win(1), Lose(-1), Draw(0);

    companion object {
        fun from(score: Int): ScoreResult {
            return values().find { it.score == score } ?: error("Invalid score $score")
        }
    }
}

enum class Action {
    Hit, Split, Stand
}
