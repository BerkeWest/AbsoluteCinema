package com.example.absolutecinema.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.data.authentication.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    //MutableSharedFlow vs MutableStateFlow
    //
    //MutableStateFlow'un bir current value su olur. Değiştikçe bunu collecterlarına aktarır. Canlı veri değişimini ekranda göstermek için ideal, sadece en son state verilebilir
    //MutabelSharedFlow event yollar (emit) başlangıç değeri olmak zorunda değil. navigation signal gibi eventler için uygun replay düzenlenmedikçe yeni veriyi collecterlar görmez
    //süper benzetme state termostat, güncel sıcaklık gösterir yeni gelenler de görebilir. shared kapı zili, sadece zamanında duyanlar bilir yeni gelenler bilmez
    private val _loginState = MutableStateFlow<Boolean?>(null)

    //login state sonrası get() koyarsak ne değişir
    //
    // = TEKRARDAN asStateFlow u hesaplar


    //asStateFlow kaldırırsak ne değişir
    //
    //_loginState mutable yani sadece view model değiştirebilir login state ise StateFlow olunca red only olur bu sayede login state i dışardan başka bir yer değiştiremez sadece görebilir
    val loginState = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = repository.login(username, password)
            _loginState.value = result
        }
    }
}

//Compose UI state dataClass var vs val and list
//
//Compose var ve mutableList gibi mutable fieldlardaki değişimi tespit edemez ve recompose etmez. manuel değiştirmek gerekir. immutablelardaki değişimler ise compose tarafından algılanır ve değiştirilir
data class BerkeUIState(val name: String, val surname: String)