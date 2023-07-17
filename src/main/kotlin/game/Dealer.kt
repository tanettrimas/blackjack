package game

import card.Shoe

class Dealer(private var hand: Hand) : Player {
    private var actionPlayed = false

    override fun total(): Int {
        val sum = hand.total()
        return if (sum + 10 <= 21) {
            sum + 10
        } else {
            sum
        }
    }

    override fun actions(): List<Action> {
        if (hand is FinishedHand) {
            return emptyList()
        }
        return listOf(Action.Hit, Action.Stand)
    }

    override fun play(action: Action, shoe: Shoe) {
        actionPlayed = true
        if (isFinished()) {
            return
        }
        when(action) {
            Action.Hit -> {
                hand = hand.hit(shoe)
                return if (total() < 17) {
                    play(Action.Hit, shoe)
                } else {
                    play(Action.Stand, shoe)
                }
            }
            Action.Split -> return
            Action.Stand -> hand = FinishedHand(hand)
        }
    }

    override fun hasBlackjack() = hand.size == 2 && total() == 21

    override fun isFinished() = isBust() || hand is FinishedHand
    override fun printCards(): String {
        return if (!actionPlayed) {
            val pattern = "\\[(.*?)]" // The pattern to match the content inside square brackets

            val regex = Regex(pattern)
            val matchResult = regex.find(hand.toString())

            if (matchResult != null) {
                val innerContent = matchResult.groupValues[1]
                "[${innerContent.split(",").first()}, <HIDDEN>]"
            } else {
                ""
            }
        } else {
            hand.toString()
        }
    }
}