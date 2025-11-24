package co.peanech.onboardingtask.presentation.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import co.peanech.onboardingtask.Constants
import co.peanech.onboardingtask.R
import co.peanech.onboardingtask.databinding.ActivitySignupBinding
import co.peanech.onboardingtask.domain.model.Result
import co.peanech.onboardingtask.presentation.model.UiState
import co.peanech.onboardingtask.presentation.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    private val signupViewModel by viewModels<SignupViewModel>()

    private var isEmailChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        initSignupResultViewModel()
        initIsExistEmailViewModel()
    }

    private fun initView() = with(binding) {
        ibSignupBack.setOnClickListener { finish() }

        etSignupEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                isEmailChecked = false
                binding.tilSignupEmail.helperText = null

                val isValidEmail = p0.toString().matches(Constants.REGEX_EMAIL.toRegex())
                btnSignupEmailCheck.isEnabled = isValidEmail
                tilSignupEmail.error =
                    if (isValidEmail) null else getString(R.string.error_email_regex)
            }
        })

        etSignupPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val isValidPassword = p0.toString().matches(Constants.REGEX_PASSWORD.toRegex())
                tilSignupPassword.error =
                    if (isValidPassword) null else getString(R.string.error_password_regex)
            }
        })

        etSignupPasswordCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val isSamePassword = p0.toString() == etSignupPassword.text.toString()
                tilSignupPasswordCheck.error =
                    if (isSamePassword) null else getString(R.string.error_password_check)
            }
        })

        btnSignupEmailCheck.setOnClickListener {
            val email = binding.etSignupEmail.text.toString()
            val isValidEmail = email.matches(Constants.REGEX_EMAIL.toRegex())
            if (isValidEmail) signupViewModel.checkIsEmailExist(email)
        }

        btnSignupSubmit.setOnClickListener {
            val name = binding.etSignupName.text.toString()
            val email = binding.etSignupEmail.text.toString()
            val password = binding.etSignupPassword.text.toString()
            val passwordCheck = binding.etSignupPasswordCheck.text.toString()

            if (validateUserInput(
                    name, email, password, passwordCheck
                )
            ) signupViewModel.signup(name, email, password)
        }
    }

    private fun initSignupResultViewModel() = with(signupViewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signupResult.collect {
                    when (it) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            val intent =
                                Intent(this@SignupActivity, LoginActivity::class.java).apply {
                                    putExtra(Constants.EXTRA_EMAIL, it.data.email)
                                    putExtra(Constants.EXTRA_PASSWORD, it.data.password)
                                }
                            setResult(RESULT_OK, intent)
                            finish()
                        }

                        is Result.Error -> Toast.makeText(
                            this@SignupActivity, it.message, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun initIsExistEmailViewModel() = with(signupViewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                isExistEmail.collect {
                    when (it) {
                        is UiState.Success -> {
                            if (it.data) {
                                binding.tilSignupEmail.helperText = null
                                binding.tilSignupEmail.error =
                                    getString(R.string.error_email_duplicated)
                            } else {
                                isEmailChecked = true
                                binding.tilSignupEmail.error = null
                                binding.tilSignupEmail.helperText =
                                    getString(R.string.signup_email_not_exist)
                            }
                        }

                        is UiState.Error -> Toast.makeText(
                            this@SignupActivity, it.message, Toast.LENGTH_SHORT
                        ).show()

                        is UiState.Loading -> {}
                        else -> {}
                    }
                }
            }
        }
    }

    private fun validateUserInput(
        name: String, email: String, password: String, passwordCheck: String
    ): Boolean {
        if (name.isBlank() || email.isBlank() || password.isBlank() || passwordCheck.isBlank()) {
            Toast.makeText(this, getString(R.string.error_empty_all), Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isEmailChecked) {
            Toast.makeText(this, getString(R.string.error_email_not_checked), Toast.LENGTH_SHORT)
                .show()
            return false
        }

        val isValidEmail = email.matches(Constants.REGEX_EMAIL.toRegex())
        val isValidPassword = password.matches(Constants.REGEX_PASSWORD.toRegex())
        val isSamePassword = password == passwordCheck

        return isValidEmail && isValidPassword && isSamePassword
    }
}
