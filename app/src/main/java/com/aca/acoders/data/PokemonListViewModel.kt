import androidx.activity.result.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aca.acoders.domain.Pokemon
import com.aca.acoders.repository.PokemonRepository
import kotlinx.coroutines.launch


class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {    var pokemonList =
    mutableStateListOf<Pokemon>()
    private var currentPage = 0
    var isEndReached = false
    var isLoading = mutableStateOf(false)

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading.value || isEndReached) return

        viewModelScope.launch {
            isLoading.value = true
            val results = repository.getPokemonList(currentPage, 20).pokemons

            if (results.isEmpty()) {
                isEndReached = true
            } else {
                pokemonList.addAll(results)
                currentPage++
            }
            isLoading.value = false
        }
    }
}
