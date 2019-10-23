package com.softtech.android_structure.domain.usecases.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softtech.android_structure.data.repositories.SignupRepository
import com.softtech.android_structure.domain.validations.RegisterValidator
import com.softtech.android_structure.domain.entities.account.RegisterParams
import com.softtech.android_structure.domain.usecases.UseCase
import com.softtech.android_structure.features.common.CommonState
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.functions.Function

class RegisterUseCase(val signupRepository: SignupRepository,private  val  registerValidator: RegisterValidator): UseCase<RegisterParams, Single<String>> {

    override fun execute(param: RegisterParams?): Single<String> {
       return registerValidator.validate(param!!).flatMap { signupRepository.callRegisterAPI(param) }

   }

    fun register(registerParams: RegisterParams, liveData:MutableLiveData<CommonState<String>>) {
        getDisposabel().add((
                execute(registerParams).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { liveData.value= CommonState.LoadingShow }
                .doFinally { liveData.value= CommonState.LoadingFinished }
                .subscribe({ liveData.value= CommonState.Success(it) },
                    { liveData.value= CommonState.Error(it) })))

    }

}