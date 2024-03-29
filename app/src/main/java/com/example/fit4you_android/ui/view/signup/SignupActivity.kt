package com.example.fit4you_android.ui.view.signup

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.example.fit4you_android.R
import com.example.fit4you_android.data.Resource
import com.example.fit4you_android.data.dto.response.IsEmailDupRes
import com.example.fit4you_android.data.dto.response.IsNicknameDupRes
import com.example.fit4you_android.data.dto.response.SignUpRes
import com.example.fit4you_android.databinding.ActivitySignupBinding
import com.example.fit4you_android.ui.base.BaseActivity
import com.example.fit4you_android.ui.view.login.LoginActivity
import com.example.fit4you_android.util.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupBinding, SignupViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_signup
    override val viewModel: SignupViewModel by viewModels()

    private var nickNameFlag = false
    private var passwordFlag = false
    private var passwordCheckFlag = false
    private var emailFlag = false

//    private val nickNameListener = object : CheckTextWatcher() {
//        override fun checkFlag() {
//            binding.btnSignUpJoin.isEnabled =
//                passwordFlag && passwordCheckFlag && emailFlag
//        }
//
//        override fun checkText(text: String) = checkNickName(text)
//    }
//
//    private val passwordListener = object : CheckTextWatcher() {
//        override fun checkFlag() {
//            binding.btnSignUpJoin.isEnabled =
//                passwordFlag && passwordCheckFlag && emailFlag
//        }
//
//        override fun checkText(text: String) = checkPassword(text)
//    }
//
//    private val passwordAgainListener = object : CheckTextWatcher() {
//        override fun checkFlag() {
//            binding.btnSignUpJoin.isEnabled =
//                passwordFlag && passwordCheckFlag && emailFlag
//        }
//
//        override fun checkText(text: String) = checkPasswordAgain(text)
//    }
//
//    private val emailListener = object : CheckTextWatcher() {
//        override fun checkFlag() {
//            binding.btnSignUpJoin.isEnabled =
//                passwordFlag && passwordCheckFlag && emailFlag
//        }
//
//        override fun checkText(text: String) = checkEmail(text)
//    }

    override fun initBeforeBinding() {

    }

    override fun initAfterBinding() {
        binding.lifecycleOwner = this
        observeViewModel()
    }

    override fun initView() {
//        initEmailBtn()
//        initNicknameBtn()
        initSignUpBtn()
//        initEditTextListener()
    }

    private fun observeViewModel() {
        observe(viewModel.signUpProcess, ::handleSignUpResult)
//        observe(viewModel.emailDupProcess, ::handleEmailDupResult)
//        observe(viewModel.nickDupProcess, ::handleNickDupResult)
        observeToast(viewModel.showToast)
    }

    private fun handleSignUpResult(status: Resource<SignUpRes>) {
        Log.d("회원가입 과정", "$status")
        when (status) {
            is Resource.Loading -> {
                binding.lottieSignup.toVisible()
                binding.lottieSignup.playAnimation()
            }
            is Resource.Success -> status.data.let {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                val nextScreenIntent =
                    Intent(applicationContext, LoginActivity::class.java)
                startActivity(nextScreenIntent)
                finish()
            }
            is Resource.Error -> {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                viewModel.showToastMessage(status.message)
            }
        }
    }

    private fun handleEmailDupResult(status: Resource<IsEmailDupRes>) {
        when (status) {
            is Resource.Loading -> {
                binding.lottieSignup.toVisible()
                binding.lottieSignup.playAnimation()
            }
            is Resource.Success -> status.data.let {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                when (status.data.result) {
                    "SUCCESS" -> viewModel.showToastMessage(status.data.data)
                    "FAIL" -> viewModel.showToastMessage(status.data.message)
                }
            }
            is Resource.Error -> {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                viewModel.showToastMessage(status.message)
            }
        }
    }

    private fun handleNickDupResult(status: Resource<IsNicknameDupRes>) {
        when (status) {
            is Resource.Loading -> {
                binding.lottieSignup.toVisible()
                binding.lottieSignup.playAnimation()
            }
            is Resource.Success -> status.data.let {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                when (status.data.result) {
                    "SUCCESS" -> viewModel.showToastMessage(status.data.data)
                    "FAIL" -> viewModel.showToastMessage(status.data.message)
                }
            }
            is Resource.Error -> {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                viewModel.showToastMessage(status.message)
            }
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

//    private fun initEditTextListener() {
//        binding.etSignUpEmailExample.addTextChangedListener(emailListener)
//        binding.etSignUpPasswordInsert.addTextChangedListener(passwordListener)
//        binding.etSignUpPasswordInsertMore.addTextChangedListener(passwordAgainListener)
//        binding.etSignUpNickNameExample.addTextChangedListener(nickNameListener)
//    }

    private fun initSignUpBtn() {
        binding.btnSignUpJoin.setOnClickListener {
            val email = binding.etSignUpEmailExample.text.toString()
            val pw = binding.etSignUpPasswordInsert.text.toString()
            val repw = binding.etSignUpPasswordInsertMore.text.toString()

            if (email == "" || pw == "" || repw == "")
                viewModel.showToastMessage(resources.getString(R.string.sign_up_req_all))
            else if (pw == repw) {
                viewModel.showToastMessage(resources.getString(R.string.sign_up_req_suc))
                viewModel.signUp(email, pw)
            } else
                viewModel.showToastMessage(resources.getString(R.string.sign_up_req_nopw))
        }
    }

//    private fun initEmailBtn() {
//        binding.btnSignUpEmailDoubleCheck.setOnClickListener {
//            val email = binding.etSignUpEmailExample.text.toString()
//            viewModel.isEmailDup(email)
//        }
//    }

//    private fun initNicknameBtn() {
//        binding.btnSignUpNickNameDoubleCheck.setOnClickListener {
//            val nickname = binding.etSignUpNickNameExample.text.toString()
//            viewModel.isNicknameDup(nickname)
//        }
//    }

//    private fun nicknameRegex(id: String): Boolean =
//        id.matches("^[가-힣a-zA-Z|d]{3,15}$".toRegex())


//    private fun passwordRegex(password: String): Boolean =
//        password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{8,16}\$".toRegex())

//    private fun passwordCheckRegex(passwordCheck: String): Boolean =
//        passwordCheck.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{8,16}\$".toRegex())


    private fun emailRegex(email: String): Boolean {
        val regexEmail =
            """^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})${'$'}""".toRegex()
        return regexEmail.matches(email)
    }

    private fun isInvalidEditTextPassword(): Boolean {
        val etInsert = binding.etSignUpPasswordInsert.text.toString()
        val etInsertMore =
            binding.etSignUpPasswordInsertMore.text.toString()

        if (etInsertMore != "" && etInsertMore != etInsert) return false
        return true
    }

    private fun checkEmail(email: String) {
        when {
            email.isEmpty() -> {
                binding.etSignUpEmailExample.error =
                    resources.getString(R.string.sign_up_check_email)
                emailFlag = false
            }
            !emailRegex(email) -> {
                binding.etSignUpEmailExample.error =
                    resources.getString(R.string.sign_up_check_email_regex)
                emailFlag = false
            }
            else -> {
                binding.etSignUpEmailExample.error = null
                emailFlag = true
            }
        }
    }

    private fun checkNickName(nickName: String) {
        when {
            nickName.isEmpty() -> {
                binding.etSignUpNickNameExample.error =
                    resources.getString(R.string.sign_up_check_nick)
                nickNameFlag = false
            }
//            !nicknameRegex(nickName) -> {
//                binding.etSignUpNickNameExample.error =
//                    resources.getString(R.string.sign_up_check_nick_regex)
//                nickNameFlag = false
//            }
            else -> {
                binding.etSignUpNickNameExample.error = null
                nickNameFlag = true
            }
        }
    }

    private fun checkPassword(password: String) {
        when {
            password.isEmpty() -> {
                binding.etSignUpPasswordInsert.error =
                    resources.getString(R.string.sign_up_check_pw)
                passwordFlag = false
            }
//            !passwordRegex(password) -> {
//                binding.etSignUpPasswordInsert.error =
//                    resources.getString(R.string.sign_up_check_pw_regex)
//                passwordFlag = false
//            }
//            !passwordCheckRegex(password) -> {
//                binding.etSignUpPasswordInsertMore.error =
//                    resources.getString(R.string.sign_up_check_pw_regex)
//                passwordCheckFlag = false
//            }
            password.isNotEmpty() -> {
                binding.etSignUpPasswordInsert.error = null
                passwordFlag = true

                if (!isInvalidEditTextPassword()) {
                    binding.etSignUpPasswordInsertMore.error =
                        resources.getString(R.string.sign_up_check_pw_not)
                    passwordCheckFlag = false
                    passwordFlag = true
                } else {
                    binding.etSignUpPasswordInsertMore.error = null
                    passwordCheckFlag = true
                }
            }
        }
    }

    private fun checkPasswordAgain(password: String) {
        when {
            password.isEmpty() -> {
                binding.etSignUpPasswordInsertMore.error =
                    resources.getString(R.string.sign_up_check_pw)
                passwordFlag = false
            }
//            !passwordCheckRegex(password) -> {
//                binding.etSignUpPasswordInsertMore.error =
//                    resources.getString(R.string.sign_up_check_pw_regex)
//                passwordCheckFlag = false
//            }
            password.isNotEmpty() -> {
                binding.etSignUpPasswordInsertMore.error = null
                passwordFlag = true
                when {
                    binding.etSignUpPasswordInsertMore.text.toString() != ""
                            && binding.etSignUpPasswordInsertMore.text.toString() != binding.etSignUpPasswordInsert.text.toString() -> {
                        binding.etSignUpPasswordInsertMore.error =
                            resources.getString(R.string.sign_up_check_pw_not)
                        passwordCheckFlag = false
                        passwordFlag = true
                    }
                    else -> {
                        binding.etSignUpPasswordInsertMore.error = null
                        passwordCheckFlag = true
                    }
                }
            }
        }
    }
}