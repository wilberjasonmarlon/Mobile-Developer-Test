package cu.wilb3r.reign.ui.home


import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cu.wilb3r.reign.data.db.models.DBArticle
import cu.wilb3r.reign.data.db.repository.DBRepository
import cu.wilb3r.reign.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(private val mRepo: MainRepository, private val dbRepo: DBRepository): ViewModel() {

    fun delectArticle(item: DBArticle) = viewModelScope.launch {
        dbRepo.softDeleteArticle(item)
    }

    //
    fun getArticles(): Flow<PagingData<DBArticle>> {
        return mRepo.getHits().cachedIn(viewModelScope)
    }
}


