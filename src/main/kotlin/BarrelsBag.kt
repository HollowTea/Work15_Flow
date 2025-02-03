import kotlin.random.Random
import kotlin.random.nextInt

class BarrelsBag { //класс сделан, чтобы не дублировать код в объекте "Ведущий лото" и в классе "Игрок" при создании карточки в моменте получения рандомного числа
    private var barrelsList = mutableListOf<Int>()
    private var rangeUntil = 0

    fun getBarrel(): Int {
        if (barrelsList.isEmpty()) {
            barrelsList = (1..90).toMutableList()
            rangeUntil = 89
        }
        barrelsList.shuffled()
        val barrel = barrelsList[Random.nextInt(0..rangeUntil)]
        barrelsList.remove(barrel)
        rangeUntil -= 1
        return barrel
    }
}