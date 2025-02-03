import kotlinx.coroutines.Job
import kotlin.random.Random
import kotlin.random.nextInt

class Player(private val name: String, private val playerNumber: Int) {
    private var cardsList = mutableListOf<MutableList<MutableList<Int>>>()

    private fun cardRow(number: Int): Int {
        val row = when (number) {
            in 1..9 -> 0
            in 10..19 -> 1
            in 20..29 -> 2
            in 30..39 -> 3
            in 40..49 -> 4
            in 50..59 -> 5
            in 60..69 -> 6
            in 70..79 -> 7
            else -> 8
        }
        return row
    }

    fun getLottoCard() {
        val cardFirstLine = MutableList(size = 9) { 0 }
        val cardSecondLine = MutableList(size = 9) { 0 }
        val cardThirdLine = MutableList(size = 9) { 0 }
        val numbersForCard = BarrelsBag()
        var firstLineCount = 0
        var secondLineCount = 0
        var thirdLineCount = 0
        var totalLineCount = 0

        while (totalLineCount != 15) {
            val number = numbersForCard.getBarrel()
            val line = Random.nextInt(1..3)
            if (firstLineCount != 5 && line == 1 && cardFirstLine[cardRow(number)] == 0) {
                cardFirstLine[cardRow(number)] = number
                firstLineCount++
                totalLineCount++
            } else if (secondLineCount != 5 && line == 2 && cardSecondLine[cardRow(number)] == 0) {
                cardSecondLine[cardRow(number)] = number
                secondLineCount++
                totalLineCount++
            } else if (thirdLineCount != 5 && line == 3 && cardThirdLine[cardRow(number)] == 0) {
                cardThirdLine[cardRow(number)] = number
                thirdLineCount++
                totalLineCount++
            }
        }
        cardsList.add(mutableListOf(cardFirstLine, cardSecondLine, cardThirdLine))
    }

    fun playingLotto(barrel: Int, parentJob: Job) {
        cardsList.forEach {
            if (it[0].contains(barrel)) {
                println("Player $playerNumber - $name get barrel N$barrel on first line!")
                it[0][it[0].indexOf(barrel)] = -1
                if (it[0].sum() == -5) {
                    cardPrinter()
                    println("First line closed! Player $playerNumber: $name wins!")
                    parentJob.cancel()
                }
            } else if (it[1].contains(barrel)) {
                println("Player $playerNumber - $name get barrel N$barrel on second line!")
                it[1][it[1].indexOf(barrel)] = -1
                if (it[1].sum() == -5) {
                    cardPrinter()
                    println("Second line closed! Player $playerNumber: $name wins!")
                    parentJob.cancel()
                }
            } else if (it[2].contains(barrel)) {
                println("Player $playerNumber - $name get barrel N$barrel on third line!")
                it[2][it[2].indexOf(barrel)] = -1
                if (it[2].sum() == -5) {
                    cardPrinter()
                    println("Third line closed! Player $playerNumber: $name wins!")
                    parentJob.cancel()
                }
            }
        }
    }

    fun cardPrinter() {
        println("Player $playerNumber: $name")
        cardsList.forEach {
            println("__________________________________________________")
            it.forEach { intMutableList ->
                var cell = 0
                intMutableList.forEach { it1 ->
                    when (it1) {
                        0 -> {
                            print("|   |")
                        }
                        -1 -> {
                            print("| X |")
                        }
                        else -> {
                            print("| $it1 |")
                        }
                    }
                    cell++
                     if (cell == 9) {
                         println("")
                     }
                }
            }
            println("--------------------------------------------------")
        }
    }
}