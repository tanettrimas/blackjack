package game

enum class ScoreResult(private val score: Int) {
    Win(1), Lose(-1), Draw(0);

    companion object {
        fun from(score: Int): ScoreResult {
            return values().find { it.score == score } ?: error("Invalid score $score")
        }
    }
}