package game

import card.Shoe

class RegularPlayer(hands: List<Hand>) : Player {
    private val hands: MutableList<Hand> = hands.toMutableList()
    private var aceAssignment: AceAssignment = AceAssignment.NOT_ASSIGNED

    private var currentHandIndex = 0

    private var currentHand: Hand
        get() {
            return hands[currentHandIndex]
        }
        set(value) {
            hands[currentHandIndex] = value
        }

    override fun hasBlackjack() = currentHand.size == 2 && !currentHand.splitted && total() == 21
    fun assignAce(aceAssignment: AceAssignment) {
        this.aceAssignment = aceAssignment
    }

    override fun total(): Int {
        return HandWithAssignment(currentHand, aceAssignment).total()
    }

    override fun actions(): List<Action> {
        if (currentHand.isBust() || isFinished()) {
            return emptyList()
        }
        if (currentHand.isSplittable()) {
            return listOf(Action.Hit, Action.Split, Action.Stand)
        }
        return listOf(Action.Hit, Action.Stand)
    }

    override fun play(action: Action, shoe: Shoe) {
        if (isFinished()) {
            return
        }
        when (action) {
            Action.Hit -> currentHand = currentHand.hit(shoe)
            Action.Split -> {
                hands.clear()
                hands.addAll(currentHand.split(shoe))
            }

            Action.Stand -> {
                currentHand = FinishedHand(currentHand)
                if (currentHandIndex == hands.lastIndex) {
                    return
                } else {
                    currentHandIndex++
                }
            }
        }
    }

    override fun isFinished(): Boolean {
        return hands.all { it is FinishedHand || it.isBust() }
    }
}

private class HandWithAssignment(private val hand: Hand, private val aceAssignment: AceAssignment) : Hand by hand {
    override fun total(): Int {
        val sum = hand.total()
        if (hand.hasAce() && aceAssignment == AceAssignment.NOT_ASSIGNED) {
            throw AceNotAssignedError("")
        }
        return if (hand.hasAce()) {
            sum + aceAssignment.value
        } else {
            sum
        }
    }
}

enum class AceAssignment(val value: Int) {
    NOT_ASSIGNED(-1), ONE(0), ELEVEN(10)
}

class AceNotAssignedError(override val message: String?) : RuntimeException(message)