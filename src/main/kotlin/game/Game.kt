package game

import card.Card
import card.Shoe

class Game {
    private val shoe = Shoe(shoeSize = 4)
    private val players: List<Player> = createPlayers()
    private val dealer: Player
        get() = players.filterIsInstance<Dealer>().first()
    private val regularPlayer: Player
        get() = players.first { it is RegularPlayer }

    private fun createPlayers(): List<Player> {
        val playerCards = mutableListOf<Card>()
        val dealerCards = mutableListOf<Card>()

        playerCards.add(shoe.deal())
        dealerCards.add(shoe.deal())
        playerCards.add(shoe.deal())
        dealerCards.add(shoe.deal())

        val dealer = Dealer(BlackjackHand(*dealerCards.toTypedArray()))
        val player = RegularPlayer(listOf(BlackjackHand(*playerCards.toTypedArray())))
        return listOf(player, dealer)
    }

    private fun printWelcomeMessage() {
        println("Welcome to Blackjack!")
        println("You are dealt two cards. Try to get as close to 21 as possible without going over.")
        println("Face cards are worth 10. Aces are worth 1 or 11, whichever makes a better hand.")
        line()
        println("""
            Dealer cards: ${dealer.printCards()}
            Player cards: ${regularPlayer.printCards()}
        """.trimIndent())
    }

    fun play() {
        printWelcomeMessage()
        line()
        while(!regularPlayer.isFinished()) {
            val total = regularPlayer.totalWithAssignment()
            println("Current total: $total")
            println("Here are your actions: ${regularPlayer.actions()}")
            println("Please write your choice: ")
            val action = Action.valueOf(readln().replaceFirstChar { it.uppercase() })
            regularPlayer.play(action, shoe)
            line()
            println("Your hands is now: ${regularPlayer.printCards()}")
            println("The total of your current hand is: ${regularPlayer.totalWithAssignment()}")
        }
        line()
        println("You are now finished. Now playing dealer.")
        line()
        dealer.play(Action.Hit, shoe)
        println("Dealer finished with hand ${dealer.printCards()}")
        printScore(player = regularPlayer, dealer = dealer)
        line()
        println("Do you want to play again?")
    }

    private fun printScore(player: Player, dealer: Player) {
        return when(player.scoreAgainst(dealer)) {
            ScoreResult.Win -> println("You won over the dealer with score ${player.total()} against ${dealer.total()}")
            ScoreResult.Lose -> println("You lost over the dealer with score ${player.total()} against ${dealer.total()}")
            ScoreResult.Draw -> println("You obtained a draw over the dealer with score ${player.total()} against ${dealer.total()}")
        }
    }

    private fun Player.totalWithAssignment() = try {
        regularPlayer.total()
    } catch (e: AceNotAssignedError) {
        println("Ace detected in your hand but not assigned. Please assign ace to either ${AceAssignment.ONE} or ${AceAssignment.ELEVEN}: ")
        val assignment = AceAssignment.valueOf(readln())
        (regularPlayer as RegularPlayer).assignAce(assignment)
        regularPlayer.total()
    }

    private fun line() = println("----------------------------------------------------------------------")
}

fun main() {
    Game().play()
}