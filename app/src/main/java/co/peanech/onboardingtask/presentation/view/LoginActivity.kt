package co.peanech.onboardingtask.presentation.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import co.peanech.onboardingtask.Constants
import co.peanech.onboardingtask.R
import co.peanech.onboardingtask.databinding.ActivityLoginBinding
import co.peanech.onboardingtask.domain.model.ErrorCode
import co.peanech.onboardingtask.domain.model.Result
import co.peanech.onboardingtask.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initActivityResultLauncher()
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        btnLoginSubmit.setOnClickListener {
            val email = etLoginEmail.text.toString()
            val password = etLoginPassword.text.toString()

            if (!email.isBlank() && !password.isBlank()) loginViewModel.login(email, password)
            else {
                if (email.isBlank()) tilLoginEmail.error = getString(R.string.error_email_empty)
                if (password.isBlank()) tilLoginPassword.error =
                    getString(R.string.error_password_empty)
            }
        }

        btnLoginSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }

    private fun initViewModel() = with(loginViewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginResult.collect {
                    when (it) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            val intent =
                                Intent(this@LoginActivity, UserActivity::class.java).apply {
                                    putExtra(Constants.EXTRA_EMAIL, it.data)
                                }
                            startActivity(intent)
                        }

                        is Result.Error -> handleErrorMessage(it)
                    }
                }
            }
        }
    }

    private fun initActivityResultLauncher() = with(binding) {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    etLoginEmail.setText(it.data?.getStringExtra(Constants.EXTRA_EMAIL) ?: "")
                    etLoginPassword.setText(it.data?.getStringExtra(Constants.EXTRA_PASSWORD) ?: "")
                }
            }
    }

    private fun handleErrorMessage(error: Result.Error) = with(binding) {
        when (error.code) {
            ErrorCode.EMAIL -> tilLoginEmail.error = error.message
            ErrorCode.PASSWORD -> tilLoginPassword.error = error.message
            else -> Toast.makeText(this@LoginActivity, error.message, Toast.LENGTH_SHORT).show()
        }
    }
}
