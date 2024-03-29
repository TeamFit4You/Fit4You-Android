package com.example.fit4you_android.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fit4you_android.data.error.ErrorManager
import com.example.fit4you_android.util.SingleEvent
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    // LiveData를 사용하여 메시지를 전달하는 데 사용됩니다.
    private val _showToastMessage = MutableLiveData<SingleEvent<Any>>()
    val showToastMessage: LiveData<SingleEvent<Any>> = _showToastMessage

    // 에러 발생 시 메시지를 전달하는 데 사용됩니다.
    private val _showErrorMessage = MutableLiveData<SingleEvent<String>>()
    val showErrorMessage: LiveData<SingleEvent<String>> = _showErrorMessage

    /**
     * 메시지를 보여주는 데 사용됩니다.
     */
    protected fun showToast(message: Any) {
        _showToastMessage.postValue(SingleEvent(message))
    }

    /**
     * 에러 메시지를 보여주는 데 사용됩니다.
     */
    protected fun showError(message: String) {
        _showErrorMessage.postValue(SingleEvent(message))
    }

    @Inject
    lateinit var errorManager: ErrorManager
}
