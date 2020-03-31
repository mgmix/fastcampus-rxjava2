package com.maryang.fastrxjava.ui.signup

import com.maryang.fastrxjava.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.BehaviorSubject

class SignupViewModel : BaseViewModel() {

    val checkState1 = BehaviorSubject.createDefault(false)
    val checkState2 = BehaviorSubject.createDefault(false)
    val checkState3 = BehaviorSubject.createDefault(false)
    val buttonState = BehaviorSubject.createDefault(false)

    init {
        compositeDisposable += Observable.combineLatest(
            checkState1,
            checkState2,
            checkState3,
            Function3<Boolean, Boolean, Boolean, Boolean> {
                chk1, chk2, chk3 ->
                chk1 && chk2 && chk3
            }
        ).subscribe{
            buttonState.onNext(it)
        }

    }

}