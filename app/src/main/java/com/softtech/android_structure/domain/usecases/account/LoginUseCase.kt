package com.softtech.android_structure.domain.usecases.account

import androidx.lifecycle.MutableLiveData
import com.softtech.android_structure.data.repositories.LoginRepository
import com.softtech.android_structure.data.repositories.SignupRepository
import com.softtech.android_structure.domain.entities.account.LoginParameters
import com.softtech.android_structure.domain.validations.RegisterValidator
import com.softtech.android_structure.domain.entities.account.RegisterParams
import com.softtech.android_structure.domain.usecases.UseCase
import com.softtech.android_structure.domain.validations.LoginValidator
import com.softtech.android_structure.features.common.CommonState
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.functions.Function

class LoginUseCase(val loginRepository: LoginRepository,private  val loginValidator: LoginValidator): UseCase<LoginParameters, Single<String>> {

    override fun execute(param: LoginParameters?): Single<String> {
       return loginValidator.validate(param!!).flatMap { loginRepository.runAPI(param) }

   }

    fun login(params: LoginParameters, loginLiveData: MutableLiveData<CommonState<String>>) {
        getDisposabel().add(
                execute(params).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loginLiveData.value= CommonState.LoadingShow }
                .doFinally { loginLiveData.value= CommonState.LoadingFinished }
                .subscribe({ loginLiveData.value= CommonState.Success(it) },
                    { loginLiveData.value= CommonState.Error(it) })
        )

    }

}