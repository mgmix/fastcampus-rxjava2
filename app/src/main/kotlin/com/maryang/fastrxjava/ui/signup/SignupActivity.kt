package com.maryang.fastrxjava.ui.signup

import android.content.Intent
import android.os.Bundle
import com.maryang.fastrxjava.R
import com.maryang.fastrxjava.base.BaseActivity
import com.maryang.fastrxjava.ui.repos.GithubReposActivity
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity : BaseActivity() {

    private var check1Checked = false
    private var check2Checked = false
    private var check3Checked = false
    private val viewModel by lazy {
        SignupViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        setOnClickListener()
        compositeDisposable += viewModel.checkState1.subscribe { checkbox1.isChecked = it }
        compositeDisposable += viewModel.checkState2.subscribe { checkbox2.isChecked = it}
        compositeDisposable += viewModel.checkState3.subscribe { checkbox3.isChecked = it}
        compositeDisposable += viewModel.buttonState.subscribe {
            signupButton.isEnabled = it
            signupButton.setBackgroundColor(
                if (signupButton.isEnabled) getColor(R.color.colorPrimary)
                else getColor(R.color.grey_500)
            )

        }

    }

    private fun setOnClickListener() {
        checkbox1.setOnClickListener{ viewModel.checkState1.onNext(checkbox1.isChecked)}
        checkbox2.setOnClickListener{ viewModel.checkState2.onNext(checkbox2.isChecked)}
        checkbox3.setOnClickListener{ viewModel.checkState3.onNext(checkbox3.isChecked)}

        signupButton.setOnClickListener {
            startActivity(
                Intent(this, GithubReposActivity::class.java)
            )
            finish()
        }
    }

    private fun enableSignupButton() {
        signupButton.isEnabled = check1Checked && check2Checked && check3Checked
        signupButton.setBackgroundColor(
            if (signupButton.isEnabled) getColor(R.color.colorPrimary)
            else getColor(R.color.grey_500)
        )
    }
}
