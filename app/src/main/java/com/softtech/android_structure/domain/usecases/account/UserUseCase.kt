package com.softtech.android_structure.domain.usecases.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softtech.android_structure.data.repositories.UserRepository
import com.softtech.android_structure.domain.entities.account.RegisterParams
import com.softtech.android_structure.domain.entities.account.User
import com.softtech.android_structure.domain.usecases.UseCase
import com.softtech.android_structure.features.common.CommonState
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.util.NotificationLite
import io.reactivex.schedulers.Schedulers

class UserUseCase(private val userRepository: UserRepository):UseCase<Any,Single<User>> {
    override fun execute(param: Any?): Single<User> {
        return Single.fromCallable{  userRepository.getLogedInUser() }
    }


    fun getLogedUser(): LiveData<CommonState<User>> {
        val userLiveData=MutableLiveData<CommonState<User>>()

        getDisposabel().
            add(execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { userLiveData.value=CommonState.LoadingShow }
                .doFinally { userLiveData.value=CommonState.LoadingFinished }
                .subscribe({userLiveData.value=CommonState.Success(it)},{userLiveData.value=CommonState.Error(it)})
            )
        return userLiveData
    }

    fun clearUser(){

        userRepository.clear()
    }

}