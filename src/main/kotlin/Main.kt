import kotlinx.coroutines.*

suspend fun main() {

    var numberOfCards : Int
    println("Input count of cards:")
    numberOfCards = readln().toInt()
    if (numberOfCards < 1) {
        do {
            println("Error, the cards count need to be >= 1. Repeat count input:")
            numberOfCards = readln().toInt()
        } while (numberOfCards < 1)
    }

    println("Input name of Player 1:")
    val player1 = Player(readln(), 1)
    println("Input name of Player 2:")
    val player2 = Player(readln(), 2)
    val parentJob = Job()

    val scope = CoroutineScope(parentJob + Dispatchers.Default)
    scope.launch {
        for (n in 1..numberOfCards) {
            player1.getLottoCard()
            player2.getLottoCard()
        }
        player1.cardPrinter()
        player2.cardPrinter()

        launch {
            var counter = 1
            LottoHost.sharedFlow.collect {
                println("Move $counter. Barrel N$it.")
                counter++
                player1.playingLotto(it, parentJob)
            }
        }

        launch {
            var counter = 1
            LottoHost.sharedFlow.collect {
                println("Move $counter. Barrel N$it.")
                counter++
                player2.playingLotto(it, parentJob)
            }
        }
    }
    parentJob.complete()
    parentJob.join()
}