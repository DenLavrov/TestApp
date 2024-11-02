import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.test.app.core.presentation.vm.ViewModelAssistedFactory
import com.test.app.core.presentation.vm.ViewModelFactory

@Composable
inline fun <reified T : ViewModel> SavedStateRegistryOwner.getVm(
    factory: ViewModelAssistedFactory<T>
) = viewModel(
    modelClass = T::class.java,
    factory = ViewModelFactory(factory, this)
)