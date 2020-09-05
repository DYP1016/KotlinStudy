package study.dyp.com.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class TestViewModel(count: Int) : ViewModel() {
    private val userIdLiveData = MutableLiveData<String>()

    val user: LiveData<User> = Transformations.switchMap(userIdLiveData) {
        TestRepository().getUser(it)
    }

    val counter = MutableLiveData(count)

    fun getUser(userId: String) {
        userIdLiveData.value = userId
    }
}