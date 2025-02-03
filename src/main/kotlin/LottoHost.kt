import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object LottoHost {

    private val currentBarrelsBag = BarrelsBag()
    private val scope = CoroutineScope(Job() + Dispatchers.Default)
    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        scope.launch {
            while (isActive) {
                _sharedFlow.emit(currentBarrelsBag.getBarrel())
                delay(300)
            }
        }
    }
}