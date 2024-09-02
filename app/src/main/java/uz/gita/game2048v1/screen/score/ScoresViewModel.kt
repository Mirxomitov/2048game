package uz.gita.game2048v1.screen.score

//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import uz.gita.game2048v1.data.model.RecordData
//import uz.gita.game2048v1.domain.AppRepository
//import uz.gita.game2048v1.domain.AppRepositoryImpl
//import java.util.concurrent.Executors

//class ScoresViewModel : ScoresContract.ViewModel, ViewModel() {
//    private val repository: AppRepository = AppRepositoryImpl()
//    private val executor = Executors.newSingleThreadExecutor()
//    var twentyBestScores = MutableLiveData<List<RecordData>>()
//
//    override fun showScores() {
//        executor.execute {
//            val ls = repository.twentyBestScores()
//            twentyBestScores.postValue(ls)
//        }
//    }
//}