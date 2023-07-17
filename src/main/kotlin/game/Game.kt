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
    }

    private fun line() = println("----------------------------------------------------------------------")
}

fun main() {
    Game().play()
}