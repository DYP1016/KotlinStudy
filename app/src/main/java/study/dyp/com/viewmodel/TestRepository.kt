package study.dyp.com.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TestRepository {
    fun getUser(userId: String): LiveData<User> {
        val liveData = MutableLiveData<User>()
        liveData.value = User(userId, userId, 0)
        return liveData
    }
}